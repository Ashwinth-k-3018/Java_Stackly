package com.bankingmanagement.programs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TransferOperations {

    private static final Scanner sc = new Scanner(System.in);
    
    // Deposit 
    
    public static void deposit() {
    	
        System.out.print("Enter Account ID: ");
        int accountId = sc.nextInt();

        System.out.print("Enter Deposit Amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero!");
            return;
        }

        String check_acc = "SELECT account_id FROM accounts WHERE account_id = ?";
        
        String update_acc = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        
        String transactionSql = """
                INSERT INTO transactions (account_id, transaction_type, amount)
                VALUES (?, 'Deposit', ?)
                """;

        try (Connection con = DBConnection.getConnection()) {
        	
            con.setAutoCommit(false);

            try (PreparedStatement checkPst = con.prepareStatement(check_acc)) {
            	
                checkPst.setInt(1, accountId);

                try (ResultSet rs = checkPst.executeQuery()) {
                	
                    if (!rs.next()) {
                        System.out.println("Account ID not found!");
                        con.rollback();
                        return;
                    }
                }
            }

            try (PreparedStatement updatePst = con.prepareStatement(update_acc);
                 PreparedStatement transactionPst = con.prepareStatement(transactionSql)) {

                updatePst.setDouble(1, amount);
                updatePst.setInt(2, accountId);
                updatePst.executeUpdate();

                transactionPst.setInt(1, accountId);
                transactionPst.setDouble(2, amount);
                transactionPst.executeUpdate();

                con.commit();
                System.out.println("Deposit successful!");
                
            } catch (SQLException e) {
            	
                con.rollback();
                System.out.println("Deposit failed. Changes were rolled back.");
                e.printStackTrace();
            }
        } catch (SQLException e) { 
        	
            System.out.println("Database error during deposit.");
            e.printStackTrace();
        }
    }
    
    // Withdraw 
    
    public static void withdraw() {
    	
        System.out.print("Enter Account ID: ");
        int accountId = sc.nextInt();

        System.out.print("Enter Withdrawal Amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero!");
            return;
        }

        String check_acc = "SELECT balance FROM accounts WHERE account_id = ? FOR UPDATE";
        
        String update_acc = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
        
        String transactionSql = """
                INSERT INTO transactions (account_id, transaction_type, amount)
                VALUES (?, 'Withdrawal', ?)
                """;

        try (Connection con = DBConnection.getConnection()) {
        	
            con.setAutoCommit(false);

            try (PreparedStatement checkPst = con.prepareStatement(check_acc)) {
            	
                checkPst.setInt(1, accountId);

                try (ResultSet rs = checkPst.executeQuery()) {
                	
                    if (!rs.next()) {
                    	
                        System.out.println("Account ID not found!");
                        con.rollback();
                        return;
                    }

                    double balance = rs.getDouble("balance");

                    if (amount > balance) {
                    	
                        System.out.println("Insufficient balance!");
                        con.rollback();
                        return;
                    }
                }
            }

            try (PreparedStatement updatePst = con.prepareStatement(update_acc);
                 PreparedStatement transactionPst = con.prepareStatement(transactionSql)) {

                updatePst.setDouble(1, amount);
                updatePst.setInt(2, accountId);
                updatePst.executeUpdate();

                transactionPst.setInt(1, accountId);
                transactionPst.setDouble(2, amount);
                transactionPst.executeUpdate();

                con.commit();
                System.out.println("Withdrawal successful!");
                
            } catch (SQLException e) {
            	
                con.rollback();
                System.out.println("Withdrawal failed. Changes were rolled back.");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Database error during withdrawal.");
            e.printStackTrace();
        }
    }
    
    // Transfer 
    
    public static void transfer() {
    	
        System.out.print("Enter Source Account ID: ");
        int sourceAccountId = sc.nextInt();

        System.out.print("Enter Destination Account ID: ");
        int destinationAccountId = sc.nextInt();

        if (sourceAccountId == destinationAccountId) {
            System.out.println("Source and destination accounts must be different!");
            return;
        }

        System.out.print("Enter Transfer Amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
        	
            System.out.println("Amount must be greater than zero!");
            return;
        }

        String check_acc = "SELECT account_id, balance FROM accounts WHERE account_id IN (?, ?) ORDER BY account_id FOR UPDATE";
        
        String debitSql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
        
        String creditSql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        
        String transactionSql = """
                INSERT INTO transactions (account_id, transaction_type, amount)
                VALUES (?, 'Transfer', ?)
                """;

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            double sourceBalance = -1;
            boolean sourceFound = false;
            boolean destinationFound = false;

            try (PreparedStatement checkPst = con.prepareStatement(check_acc)) {
            	
                checkPst.setInt(1, sourceAccountId);
                checkPst.setInt(2, destinationAccountId);

                try (ResultSet rs = checkPst.executeQuery()) {
                	
                    while (rs.next()) {
                    	
                        int foundAccountId = rs.getInt("account_id");

                        if (foundAccountId == sourceAccountId) {
                            sourceBalance = rs.getDouble("balance");
                            sourceFound = true;
                        }

                        if (foundAccountId == destinationAccountId) {
                            destinationFound = true;
                        }
                    }
                }
            }

            if (!sourceFound || !destinationFound) {
                System.out.println("Source or destination account ID not found!");
                con.rollback();
                return;
            }

            if (amount > sourceBalance) {
                System.out.println("Insufficient balance in source account!");
                con.rollback();
                return;
            }

            try (PreparedStatement debitPst = con.prepareStatement(debitSql);
                 PreparedStatement creditPst = con.prepareStatement(creditSql);
                 PreparedStatement transactionPst = con.prepareStatement(transactionSql)) {

                debitPst.setDouble(1, amount);
                debitPst.setInt(2, sourceAccountId);
                debitPst.executeUpdate();

                creditPst.setDouble(1, amount);
                creditPst.setInt(2, destinationAccountId);
                creditPst.executeUpdate();

                transactionPst.setInt(1, sourceAccountId);
                transactionPst.setDouble(2, amount);
                transactionPst.executeUpdate();

                transactionPst.setInt(1, destinationAccountId);
                transactionPst.setDouble(2, amount);
                transactionPst.executeUpdate();

                con.commit();
                System.out.println("Transfer successful!");
                
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Transfer failed. Changes were rolled back.");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Database error during transfer.");
            e.printStackTrace();
        }
    }

    public void transferMenu() {
    	
        while (true) {
            System.out.println("\n===== MONEY OPERATIONS =====");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:{
                    deposit();
                    break;
                }
                case 2:{
                    withdraw();
                    break;
                }
                case 3:{
                    transfer();
                    break;
                    }
                case 4:{
                	System.out.println("Back to Main menu..");
                    return;
                }
                default:{
                    System.out.println("Invalid choice! Please enter 1 to 4.");
                }
            }
        }
    }
}
