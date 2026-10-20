package com.bankingmanagement.programs;

import java.sql.*;
import java.util.Scanner;

public class CreateTables {
	static final Connection con = DBConnection.getConnection();

	public static void createCustomer() {
		String query = """
				CREATE TABLE IF NOT EXISTS Customers (
				    customer_id INT PRIMARY KEY AUTO_INCREMENT,
				    customer_name VARCHAR(60) NOT NULL,
				    customer_email VARCHAR(70) NOT NULL,
				    customer_phone VARCHAR(10) UNIQUE,
				    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
				) AUTO_INCREMENT = 1;
				""";

		try (Statement st = con.createStatement()) {
			st.execute(query);
			System.out.println("Customer Table Created...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void createAccounts() {
		String query = """
				CREATE TABLE IF NOT EXISTS Accounts(
					account_id INT PRIMARY KEY AUTO_INCREMENT,
					customer_id INT,
					account_type VARCHAR(20) NOT NULL,
					balance DECIMAL(10,2) NOT NULL,
					
					FOREIGN KEY(customer_id) REFERENCES Customers(customer_id)
				) AUTO_INCREMENT = 101;
				""";

		try (Statement st = con.createStatement()) {
			st.execute(query);
			System.out.println("Accounts table created...");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void createTransactions() {
		String query = """

				CREATE TABLE IF NOT EXISTS Transactions(
					transaction_id INT PRIMARY KEY AUTO_INCREMENT,
					account_id INT ,
					transaction_type VARCHAR(20) NOT NULL,
					amount DECIMAL(12,2) NOT NULL,
					transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,

					FOREIGN KEY (account_id)REFERENCES accounts(account_id)
				) AUTO_INCREMENT = 1001;
				""";

		try (Statement st = con.createStatement()) {
			st.execute(query);
			System.out.println("Transaction table is created..query..");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	public void tableMenu() {
		
		Scanner sc = MainClass.sc;

	    System.out.println("\n===== CREATE TABLE MENU =====");
	    System.out.println("1. Create Customers Table");
	    System.out.println("2. Create Accounts Table");
	    System.out.println("3. Create Transactions Table");
	    System.out.println("4. Create All Tables");
	    System.out.println("5. Back");
	    
	    System.out.print("Enter your choice: ");

	    try  {

	        int choice = sc.nextInt();

	        switch (choice) {

	            case 1:
	                createCustomer();
	                break;

	            case 2:
	                createAccounts();
	                break;

	            case 3:
	                createTransactions();
	                break;

	            case 4:
	                createCustomer();
	                createAccounts();
	                createTransactions();
	                break;

	            case 5:
	                System.out.println("Returning...");
	                break;

	            default:
	                System.out.println("Invalid choice!");
	        }

	    } catch (NumberFormatException e) {
	        System.out.println("Please enter a valid number!");
	    }
	
	}
}
