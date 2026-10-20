package com.onlinefoodordering.programs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class CustomerOperations {

    static final Connection con = DBConnection.getConnection();
    static final Scanner sc = new Scanner(System.in);
    
    // Register Customer
    
    public static void registerCustomer() {

        String query = """
                INSERT INTO Customers (customer_name, email, phone)
                VALUES (?, ?, ?)
                """;
        try (PreparedStatement ps = con.prepareStatement(query)) {

            System.out.print("Enter customer name: ");
            String name = sc.nextLine().trim();

            System.out.print("Enter customer email: ");
            String email = sc.nextLine().trim();

            System.out.print("Enter customer phone: ");
            String phone = sc.nextLine().trim();

            // Validate customer name
            if (name.isEmpty()) {
                System.out.println("Customer name cannot be empty.");
                return;
            }

            // Validate email
            if (!email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
                System.out.println("Invalid email address.");
                return;
            }

            // Validate phone
            if (!phone.matches("[0-9]{10}")) {
                System.out.println("Phone number must contain exactly 10 digits.");
                return;
            }

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Customer registered successfully.");
            }

        } catch (SQLException e) {
        	System.out.println("Error: " + e.getMessage());
        }
    }

    // View All Customers
    
    public static void viewCustomers() {

        String query = "SELECT * FROM Customers";

        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== CUSTOMER DETAILS ==========");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Customer ID   : " + rs.getInt("customer_id"));
                System.out.println("Customer Name : " + rs.getString("customer_name"));
                System.out.println("Email         : " + rs.getString("email"));
                System.out.println("Phone         : " + rs.getString("phone"));
                System.out.println("--------------------------------------");
            }

            if (!found) {
                System.out.println("No customers found.");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving customers.");
            e.printStackTrace();
        }
    }

    // Search Customer by ID
    
    public static void searchCustomerById() {

        String query = """
                SELECT * FROM Customers
                WHERE customer_id = ?
                """;

        System.out.print("Enter customer ID: ");
        int customerId;

        try {
            customerId = sc.nextInt();
            sc.nextLine();

            if (customerId <= 0) {
                System.out.println("Customer ID must be greater than zero.");
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid numeric customer ID.");
            return;
        }

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("\n========== CUSTOMER DETAILS ==========");
                    System.out.println("Customer ID   : " + rs.getInt("customer_id"));
                    System.out.println("Customer Name : " + rs.getString("customer_name"));
                    System.out.println("Email         : " + rs.getString("email"));
                    System.out.println("Phone         : " + rs.getString("phone"));
                } else {
                    System.out.println("Customer not found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching for customer.");
            e.printStackTrace();
        }
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
 		
 		 // Validate phone
        if (!phone.matches("[0-9]{10}")) {
            System.out.println("Phone number must contain exactly 10 digits.");
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
 		
 		// Validate email
        if (!email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            System.out.println("Invalid email address.");
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
 		
 		 // Validate customer name
        if (name.isEmpty()) {
            System.out.println("Customer name cannot be empty.");
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

    // Delete Customer
    
    public static void deleteCustomer() {

        System.out.print("Enter customer ID to delete: ");
        int customerId;

        try {
            customerId = sc.nextInt();
            sc.nextLine();

            if (customerId <= 0) {
                System.out.println("Customer ID must be greater than zero.");
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid numeric customer ID.");
            return;
        }

        String query = """
                DELETE FROM Customers
                WHERE customer_id = ?
                """;

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, customerId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer ID not found.");
            }

        } catch (SQLException e) {
        	
        	System.out.println("Error: " + e.getMessage());
        }
    }

    // Customer Operations Menu
    
    public static  void customerMenu() {

        int choice;

        do {
        	
            System.out.println("\n===== CUSTOMER OPERATIONS =====");
            System.out.println("1. Register Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Search Customer by ID");
            System.out.println("4. Update Customer");
            System.out.println("5. Delete Customer");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:{
                    registerCustomer();
                    break;
                    }
                case 2:{
                    viewCustomers();
                    break;
                    }
                case 3:{
                    searchCustomerById();
                    break;
                    }
                case 4:{
                    updateCustomer();
                    break;
                    }
                case 5:{
                    deleteCustomer();
                    break;
                    }
                case 6:{
                    System.out.println("Returning to Main Menu...");
                    break;
                    }
                default:{
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        } while (choice != 6);
    }
}
