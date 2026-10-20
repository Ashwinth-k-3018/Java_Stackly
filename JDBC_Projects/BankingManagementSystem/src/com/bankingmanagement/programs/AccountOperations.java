package com.bankingmanagement.programs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AccountOperations {

    static final Scanner sc = new Scanner(System.in);

    // 1. Create Account
    
    public static void createAccount() {

        String customerQuery =
                "SELECT status FROM customers WHERE customer_id = ?";

        String insertQuery = """
                INSERT INTO accounts(customer_id, account_type, balance)
                VALUES (?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement customerPst = con.prepareStatement(customerQuery)) {

            System.out.print("Enter Customer ID: ");
            int customerId = sc.nextInt();
            sc.nextLine();

            // Check whether the customer exists and is active
            customerPst.setInt(1, customerId);

            try (ResultSet rs = customerPst.executeQuery()) {

                if (!rs.next()) {
                    System.out.println("Customer ID not found!");
                    return;
                }

                if (!"ACTIVE".equalsIgnoreCase(rs.getString("status"))) {
                    System.out.println(
                            "Cannot create an account for an inactive customer!");
                    return;
                }
            }
            
            // Account type
            
            System.out.print("Enter Account Type (SAVINGS/CURRENT): ");
            String accountType = sc.nextLine().trim().toUpperCase();

            if (!accountType.equals("SAVINGS")
                    && !accountType.equals("CURRENT")) {
                System.out.println("Invalid account type!");
                return;
            }
            // Balance 
            
            System.out.print("Enter Opening Balance: ");
            double balance = Double.parseDouble(sc.nextLine().trim());

            if (balance < 0) {
                System.out.println("Balance cannot be negative!");
                return;
            }

            try (PreparedStatement accountPst =
                         con.prepareStatement(insertQuery)) {

                accountPst.setInt(1, customerId);
                accountPst.setString(2, accountType);
                accountPst.setDouble(3, balance);

                int rows = accountPst.executeUpdate();

                if (rows > 0) {
                	System.out.println("Account created successfully!");
                	}
                } catch (SQLException e) {
					e.printStackTrace();
				}
            } catch (SQLException e1) {
				e1.printStackTrace();
			}
    }

    // 2. View All Accounts
    public static void viewAllAccounts() {

        String query = """
                SELECT account_id, customer_id, account_type, balance
                FROM accounts
                ORDER BY account_id
                """;

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            boolean found = false;

            System.out.println(
                    "+------------+-------------+----------------+----------------+");
            System.out.printf(
                    "| %-10s | %-11s | %-14s | %-14s |%n",
                    "Account ID", "Customer ID", "Account Type", "Balance");
            System.out.println(
                    "+------------+-------------+----------------+----------------+");

            while (rs.next()) {
                found = true;

                System.out.printf(
                        "| %-10d | %-11d | %-14s | %14s |%n",
                        rs.getInt("account_id"),
                        rs.getInt("customer_id"),
                        rs.getString("account_type"),
                        rs.getBigDecimal("balance").toPlainString());
            }

            System.out.println(
                    "+------------+-------------+----------------+----------------+");

            if (!found) {
                System.out.println("No accounts found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. View Account By ID
	public static void viewAccountById(int accountId) {

		String query = """
				SELECT account_id, customer_id, account_type, balance
				FROM accounts
				WHERE account_id = ?
				""";

		try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {

			pst.setInt(1, accountId);

			try (ResultSet rs = pst.executeQuery()) {

				boolean found = false;

				System.out.println("+------------+-------------+----------------+----------------+");
				System.out.printf("| %-10s | %-11s | %-14s | %-14s |%n", "Account ID", "Customer ID", "Account Type",
						"Balance");
				System.out.println("+------------+-------------+----------------+----------------+");

				while (rs.next()) {
					found = true;

					System.out.printf("| %-10d | %-11d | %-14s | %14s |%n", rs.getInt("account_id"),
							rs.getInt("customer_id"), rs.getString("account_type"),
							rs.getBigDecimal("balance").toPlainString());
				}

				System.out.println("+------------+-------------+----------------+----------------+");

				if (!found) {
					System.out.println("No accounts found.");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			}catch (SQLException e1) {
				e1.printStackTrace();
			}
	}

    // 4. View Accounts By Customer ID
    public static void viewAccountsByCustomerId(int customerId) {

        String query = """
                SELECT account_id, customer_id, account_type, balance
                FROM accounts
                WHERE customer_id = ?
                ORDER BY account_id
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {

			pst.setInt(1, customerId);

			try (ResultSet rs = pst.executeQuery()) {

				boolean found = false;

				System.out.println("+------------+-------------+----------------+----------------+");
				System.out.printf("| %-10s | %-11s | %-14s | %-14s |%n", "Account ID", "Customer ID", "Account Type",
						"Balance");
				System.out.println("+------------+-------------+----------------+----------------+");

				while (rs.next()) {
					found = true;

					System.out.printf("| %-10d | %-11d | %-14s | %14s |%n", rs.getInt("account_id"),
							rs.getInt("customer_id"), rs.getString("account_type"),
							rs.getBigDecimal("balance").toPlainString());
				}

				System.out.println("+------------+-------------+----------------+----------------+");

				if (!found) {
					System.out.println("No accounts found.");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			}catch (SQLException e1) {
				e1.printStackTrace();
			}
    }

    // 5. Check Account Balance
    public static void checkBalance(int accountId) {

        String query =
                "SELECT balance FROM accounts WHERE account_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, accountId);

            try (ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {
                    System.out.println("Available Balance: "
                            + rs.getBigDecimal("balance"));
                } else {
                    System.out.println("Account ID not found!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Account Menu
    public void accountMenu() {

        int choice ;

        do {
            System.out.println("""
                    
                    ===== ACCOUNT MENU =====
                    1. Create Account
                    2. View All Accounts
                    3. View Account By ID
                    4. View Accounts By Customer ID
                    5. Check Account Balance
                    6. Back to Main Menu
                    """);

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:{
                    createAccount();
                    break;
                }
                case 2:{
                    viewAllAccounts();
                    break;
                }
                case 3:{
	                System.out.print("Enter Account ID: ");
	                int id = sc.nextInt();
	                viewAccountById(id);
                    break;
                }
                case 4:{
                    System.out.print("Enter Customer ID: ");
                    int id = sc.nextInt();
                    viewAccountsByCustomerId(id);
                    break;
                }
                case 5:{
                    System.out.print("Enter Account ID: ");
                    int id = sc.nextInt();
                    checkBalance(id);
                    break;
                   }
                case 6:{
                    System.out.println("Returning to Main Menu...");
                    break;
                }
                default:{
                    System.out.println("Invalid choice! Please try again.");
                }
            }
        } while (choice != 6);
    }
}