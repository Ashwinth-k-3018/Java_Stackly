package com.bankingmanagement.programs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TransactionHistory {

    Scanner sc = new Scanner(System.in);

    // 1. View All Transactions
    
    public void viewAllTransactions() {

        String query = """
                SELECT transaction_id, account_id,
                       transaction_type, amount, transaction_date
                FROM Transactions
                ORDER BY transaction_id
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

        	System.out.println("\n---------------- TRANSACTION HISTORY -----------------");
        	System.out.println(
        	        "-------------------------------------------------------------------------------------------------"
        	);
        	System.out.printf(
        	        "| %-15s | %-12s | %-18s | %-15s | %-22s |%n",
        	        "Transaction ID", "Account ID",
        	        "Type", "Amount", "Date"
        	);

        	System.out.println(
        	        "-------------------------------------------------------------------------------------------------"
        	);

        	boolean found = false;

        	while (rs.next()) {

        	    found = true;

        	    System.out.printf(
        	            "| %-15d | %-12d | %-18s | %-15.2f | %-22s |%n",
        	            rs.getInt("transaction_id"),
        	            rs.getInt("account_id"),
        	            rs.getString("transaction_type"),
        	            rs.getDouble("amount"),
        	            rs.getTimestamp("transaction_date")
        	    );
        	}
        	System.out.println(
        	        "---------------------------------------------------------------------------------------------------"
        	);
            if (!found) {
                System.out.println("No transactions found!");
            }

        } catch (SQLException e) {
            System.out.println("Error viewing transactions: " + e.getMessage());
        }
    }

    // 2. View Transactions By Account ID
    
    public void viewTransactionsByAccountId() {

        System.out.print("Enter Account ID: ");
        int accountId = sc.nextInt();

        String query = """
                SELECT transaction_id, account_id,
                       transaction_type, amount, transaction_date
                FROM Transactions
                WHERE account_id = ?
                ORDER BY transaction_id
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, accountId);

            try (ResultSet rs = pst.executeQuery()) {
            	
            	System.out.println("\n---------------- ACCOUNT TRANSACTION HISTORY ----------------");
            	System.out.println(
            	        "-------------------------------------------------------------------------------------------------"
            	);
            	System.out.printf(
            	        "| %-15s | %-12s | %-18s | %-15s | %-22s |%n",
            	        "Transaction ID", "Account ID", "Type", "Amount", "Date"
            	);

            	System.out.println(
            	        "-------------------------------------------------------------------------------------------------"
            	);

            	boolean found = false;

            	while (rs.next()) {

            	    found = true;

            	    System.out.printf(
            	            "| %-15d | %-12d | %-18s | %-15.2f | %-22s |%n",
            	            rs.getInt("transaction_id"),
            	            rs.getInt("account_id"),
            	            rs.getString("transaction_type"),
            	            rs.getDouble("amount"),
            	            rs.getTimestamp("transaction_date")
            	    );
            	}
            	System.out.println(
            	        "-------------------------------------------------------------------------------------------------"
            	);

                if (!found) {
                    System.out.println(
                            "No transactions found for Account ID: " + accountId);
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Account ID!");
        } catch (SQLException e) {
            System.out.println("Error viewing account transactions: "
                    + e.getMessage());
        }
    }

    // 3. View Transaction By Transaction ID
    
    public void viewTransactionById() {

        System.out.print("Enter Transaction ID: ");
        int transactionId = sc.nextInt();

        String query = """
                SELECT transaction_id, account_id,
                       transaction_type, amount, transaction_date
                FROM Transactions
                WHERE transaction_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, transactionId);

            try (ResultSet rs = pst.executeQuery()) {

                if (!rs.next()) {
                    System.out.println("Transaction ID not found!");
                    return;
                }

                System.out.println("\n------------ TRANSACTION DETAILS ---------------------");

                System.out.printf(
                        "| %-18s | %-30s |%n",
                        "Field", "Value"
                );

                System.out.println("------------------------------------------------------");

                System.out.printf("| %-18s | %-30d |%n",
                        "Transaction ID", rs.getInt("transaction_id"));

                System.out.printf("| %-18s | %-30d |%n",
                        "Account ID", rs.getInt("account_id"));

                System.out.printf("| %-18s | %-30s |%n",
                        "Transaction Type", rs.getString("transaction_type"));

                System.out.printf("| %-18s | %-30.2f |%n",
                        "Amount", rs.getDouble("amount"));

                System.out.printf("| %-18s | %-30s |%n",
                        "Transaction Date", rs.getTimestamp("transaction_date"));

                System.out.println("-------------------------------------------------------");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Transaction ID!");
        } catch (SQLException e) {
            System.out.println("Error viewing transaction: " + e.getMessage());
        }
    }

    // 4. Transaction History Menu
    
    public void transactionHistoryMenu() {

        int choice;

        do {
            System.out.println("\n===== TRANSACTION HISTORY MENU =====");
            System.out.println("1. View All Transactions");
            System.out.println("2. View Transactions By Account ID");
            System.out.println("3. View Transaction By Transaction ID");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        viewAllTransactions();
                        break;

                    case 2:
                        viewTransactionsByAccountId();
                        break;

                    case 3:
                        viewTransactionById();
                        break;

                    case 4:
                        System.out.println("Returning to Main Menu...");
                        break;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid menu number!");
                choice = 0;
            }

        } while (choice != 4);
    }
}