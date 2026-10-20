package com.bankingmanagement.programs;

import java.sql.*;
import java.util.Scanner;

public class CustomerOperations {
	
	static final Scanner sc = new Scanner(System.in);

	// Insert customer data

	public static void insertCustomer(int rows) {
		
		String In_query = "Insert into Customers(customer_name,customer_email,customer_phone) Values (?,?,?);";
		
		
		try(Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(In_query);) {
			
			for (int i = 1; i <= rows; i++) {
				
				System.out.print("Enter the Name: ");
				String name = sc.nextLine().trim();
			
				System.out.print("Enter the Email: ");
				String email = sc.nextLine().trim();
				

				System.out.print("Enter the Phone: ");
				String phone = sc.nextLine().trim();

				if (isName(name)) {
					System.out.println("Name cannot be empty!");
					return;
				}

				if (isEmail(email)) {
					System.out.println("Please enter a valid Gmail address!");
					return;
				}

				if (isPhone(phone)) {
					System.out.println("Phone number must contain exactly 10 digits!");
					return;
				}

				pst.setString(1, name);
				pst.setString(2, email);
				pst.setString(3, phone);
				int results  = pst.executeUpdate();
				

                if (results > 0) {
                	
                    System.out.println("Row " + i + " added successfully!\n");
                }

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean isName(String name) {
		return name == null || name.trim().isEmpty();
	}

	public static boolean isEmail(String email) {
		return email == null ||  !email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$");
	}

	public static boolean isPhone(String phone) {
		return phone == null || !phone.matches("[0-9]{10}");
	}
	
	// Update Customers 
	

	public static void updateCustomer() {
	
		System.out.print("Enter the customer id: ");
        int id = sc.nextInt();
	    int choice;
	
	    do {
	        System.out.println("\n===== UPDATE CUSTOMER MENU =====");
	        System.out.println("""
	                1. Update customer name
	                2. Update customer email
	                3. Update customer phone
	                4. Back to Main Menu
	                """);
	
	        System.out.print("Enter your choice: ");
	        choice = sc.nextInt();
	        sc.nextLine();
	
	        switch (choice) {
	
	            case 1: {
	                System.out.print("Enter the updated name: ");
	                String name = sc.nextLine().trim();
	
	                updateName(name, id);
	                break;
	            }
	
	            case 2: {
	                System.out.print("Enter the updated email: ");
	                String email = sc.nextLine().trim();
	
	                updateEmail(email, id);
	                break;
	            }
	
	            case 3: {
	                System.out.print("Enter the updated phone number: ");
	                String phone = sc.nextLine().trim();
	
	                updatePhone(phone, id);
	                break;
	            }
	
	            case 4: {
	                System.out.println("Returning to Main Menu...");
	                break;
	            }
	
	            default: {
	                System.out.println("Invalid update option!");
	                break;
	            }
	        }
	
	    } while (choice != 4);
	}


	public static void updatePhone(String phone, int id) {
		
		if (isPhone(phone)) {
			
			System.out.println("Phone number must contain exactly 10 digits!");
			return;
		}

		String up_query = "update customers set customer_phone=? where customer_id = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(up_query)) {
			
			pst.setString(1, phone);
			pst.setInt(2, id);

			int rows = pst.executeUpdate();

			if (rows > 0) {
				System.out.println("Phone updated successfully!");
			} else {
				System.out.println("Customer ID not found or no changes made.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateEmail(String email, int id) {
		
		if (isEmail(email)) {
			
			System.out.println("Please enter a valid Gmail address!");
			return;
		}
		String up_query = "Update customers set Customer_email=? where customer_id = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(up_query)) {

			pst.setString(1, email);
			pst.setInt(2, id);

			int rows = pst.executeUpdate();

			if (rows > 0) {
				
				System.out.println("Email updated successfully!");
			} else {
				
				System.out.println("Customer ID not found or no changes made.");
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public static void updateName(String name, int id) {
		
		if (isName(name)) {

			System.out.println("Name cannot be empty!");
			return;
		}
		String up_query = "Update customers set customer_name = ? where customer_id=?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(up_query)) {

			pst.setString(1, name);
			pst.setInt(2, id);

			int rows = pst.executeUpdate();
			
			if (rows > 0) {
				
				System.out.println("Name updated successfully!");
				
			} else {
				
				System.out.println("Customer ID not found or no changes made.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// View Customers     
    public static void viewCustomer() {

        int choice;

        do {
        	System.out.println("Customer menu");

        	System.out.println("1. View All Customers");
        	System.out.println("2. View Customer By ID");
        	System.out.println("3. Back to Main Menu");
        	
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:{
                	
                    viewAllCustomers();
                    break;
                }
                case 2:{
                	
                    System.out.print("Enter customer ID: ");
                    int id = sc.nextInt();
                    
                    viewCustomerById(id);
                    break;
                }
                case 3:{
                	
                    System.out.println("Back to Main Menu...");
                    break;
                }
                default:{
                    System.out.println("Invalid choice! Please try again.");
                }
            }

        } while (choice != 3);
    }
    
    public static void viewAllCustomers() {

        String query = "SELECT * FROM customers";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n================ CUSTOMER DETAILS ================\n");

            System.out.println(
                    "+-----+----------------------+--------------------------------+--------------+-----------+");

            System.out.printf("| %-3s | %-20s | %-30s | %-12s | %-10s  |%n",
                    "ID", "Name", "Email", "Phone","Status");

            System.out.println(
                    "+-----+----------------------+--------------------------------+--------------+-----------+");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.printf("| %-3d | %-20s | %-30s | %-12s | %-10s  |%n",
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_email"),
                        rs.getString("customer_phone"),
                		rs.getString("status"));
            }

            System.out.println(
                    "+-----+----------------------+--------------------------------+--------------+-----------+");

            if (!found) {
            	
                System.out.println("No customer records found!");
            }

        } catch (SQLException e) {
        	
            e.printStackTrace();
        }
    }


    // View Customer By ID
    public static void viewCustomerById(int id) {

        String query =
                "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {

                    System.out.println("\n========== CUSTOMER DETAILS ==========\n");

                    System.out.println(
                            "+-----+----------------------+--------------------------------+--------------+--------------+");

                    System.out.printf("| %-3s | %-20s | %-30s | %-12s | %-10s |%n",
                            "ID", "Name", "Email", "Phone","Status");

                    System.out.println(
                            "+-----+----------------------+--------------------------------+--------------+--------------+");

                    System.out.printf("| %-3d | %-20s | %-30s | %-12s | %-10s  |%n",
                            rs.getInt("customer_id"),
                            rs.getString("customer_name"),
                            rs.getString("customer_email"),
                            rs.getString("customer_phone"),
                            rs.getString("status"));

                    System.out.println(
                            "+-----+----------------------+--------------------------------+--------------+--------------+");

                } else {
                    System.out.println("Customer ID not found!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

	public static void deleteCustomerMenu() {
	
	    int choice;
	
	    do {
	        System.out.println("Delete customer menu");
	        System.out.println("1. Delete Customer By ID");
	        System.out.println("2. Deactived Customer By ID");
	        System.out.println("3. Back to Main menu");
	
	        System.out.print("Enter your choice: ");
	        choice = sc.nextInt();
	
	        switch (choice) {
	
	            case 1:{
	            	
	                System.out.print("Enter customer ID to delete: ");
	                int id = sc.nextInt();
	                
	                deleteCustomer(id);
	                break;
	            }
	            case 2:{ 
	            	
	            	System.out.print("Enter customer ID to Deactived: ");
	            	int id = sc.nextInt();
	            	
	            	deactivateCustomer(id);
	            	break;
	            }
	            case 3:{
	            	
	                System.out.println("Returning to Main Menu...");
	                break;
	            }
	            default:{
	            	
	                System.out.println("Invalid choice! Please try again.");
	            }
	        }
	
	    } while (choice != 3);
	}


	// Delete Customer By ID
	public static void deleteCustomer(int id) {
	
	    String checkCustomer =
	            "SELECT customer_id FROM customers WHERE customer_id = ?";
	
	    String checkAccounts =
	            "SELECT account_id FROM accounts WHERE customer_id = ? LIMIT 1";
	
	    String deleteQuery =
	            "DELETE FROM customers WHERE customer_id = ?";
	
	    try (Connection con = DBConnection.getConnection()) {
	
	        // Check whether the customer exists
	    	
	        try (PreparedStatement pst =
	                     con.prepareStatement(checkCustomer)) {
	
	            pst.setInt(1, id);
	
	            try (ResultSet rs = pst.executeQuery()) {
	
	                if (!rs.next()) {
	                    System.out.println("Customer ID not found!");
	                    return;
	                }
	            }
	        }
	
	        // Check whether the customer has any accounts
	        try (PreparedStatement pst =
	                     con.prepareStatement(checkAccounts)) {
	
	            pst.setInt(1, id);
	
	            try (ResultSet rs = pst.executeQuery()) {
	
	                if (rs.next()) {
	                    System.out.println(
	                            "Cannot delete this customer because they have bank accounts.");
	                    System.out.println(
	                            "Preserve the customer's account and transaction history.");
	                    return;
	                }
	            }
	        }
	
	        // Delete the customer
	        
	        try (PreparedStatement pst =
	                     con.prepareStatement(deleteQuery)) {
	
	            pst.setInt(1, id);
	
	            int rows = pst.executeUpdate();
	
	            if (rows > 0) {
	                System.out.println("Customer deleted successfully!");
	            } else {
	                System.out.println("Customer could not be deleted.");
	            }
	        }
	
	    } catch (SQLException e) {
	        System.out.println("Unable to delete customer.");
	        e.printStackTrace();
	    }
	}
	
	// deactivate customer 
	public static void deactivateCustomer(int id) {

	    String query = """
	        UPDATE customers
	        SET status = 'INACTIVE'
	        WHERE customer_id = ?
	          AND status = 'ACTIVE'
	        """;

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement pst = con.prepareStatement(query)) {

	        pst.setInt(1, id);

	        int rows = pst.executeUpdate();

	        if (rows > 0) {
	            System.out.println("Customer deactivated successfully!");
	        } else {
	            System.out.println(
	                "Customer not found or already inactive."
	            );
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void customerMenu()
	{
		int choice;
		
		do {
			System.out.println("""
			                    ===== ACCOUNT MENU =====
			                    1. Insert Customer
			                    2. Update Customer
			                    3. View Customer
			                    4. Delete Customer
			                    5. Back to Main Menu
			                    """);
			System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            
            switch (choice) {
            case 1:{
            	
                System.out.print("Enter the rows: ");
                int row = sc.nextInt();
                sc.nextLine();
                
                insertCustomer(row);
                break;
            }
            case 2:{
            	
                updateCustomer();
                break;
            }
            case 3:{
            	
            	viewCustomer();
                break;
            }
            case 4:{
                
            	deleteCustomerMenu();
                break;
            }
            case 5:{
            	
                System.out.println("Back to Main Menu...");
                break;
           }
            default:{
                System.out.println("Invalid choice! Please try again.");
            }
        }
		}while(choice!=5);
	}

	
}

