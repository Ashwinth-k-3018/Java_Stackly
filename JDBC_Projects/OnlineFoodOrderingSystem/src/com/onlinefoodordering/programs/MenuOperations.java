package com.onlinefoodordering.programs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuOperations {

    static final Connection con = DBConnection.getConnection();
    static final Scanner sc = new Scanner(System.in);

    // 1. Add food item
    
    public static void addMenuItem() {

        System.out.print("Enter item name: ");
        String itemName = sc.nextLine().trim();

        System.out.print("Enter category: ");
        String category = sc.nextLine().trim();

        System.out.print("Enter price: ");
        double price;

        try {
        	
            price = sc.nextDouble();
            sc.nextLine();

            if (price <= 0) {
                System.out.println("Price must be greater than zero.");
                return;
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid price.");
            return;
        }

        String query = """
                INSERT INTO Menu (item_name, category, price)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, itemName);
            ps.setString(2, category);
            ps.setDouble(3, price);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Food item added successfully.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 2. View available food items
    
    public static void viewMenu() {

        String query = """
                SELECT menu_id, item_name, category, price
                FROM Menu
                WHERE availability = TRUE
                """;

        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n----- FOOD MENU -----");

            while (rs.next()) {
                System.out.println("Item ID: " + rs.getInt("menu_id"));
                System.out.println("Name: " + rs.getString("item_name"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Price: Rs. " + rs.getBigDecimal("price"));
                System.out.println("---------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 3. Search food item by ID
    
    public static void searchMenuItem() {

        System.out.print("Enter food item ID: ");
        int menuId;

        try {
            menuId = sc.nextInt();
            sc.nextLine();

            if (menuId <= 0) {
                System.out.println("Enter a valid item ID.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid item ID.");
            return;
        }

        String query = """
                SELECT menu_id, item_name, category, price
                FROM Menu
                WHERE menu_id = ?
                """;

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, menuId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("Item ID: " + rs.getInt("menu_id"));
                    System.out.println("Name: " + rs.getString("item_name"));
                    System.out.println("Category: " + rs.getString("category"));
                    System.out.println("Price: Rs. " + rs.getBigDecimal("price"));
                } else {
                    System.out.println("Food item not found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 4. Update food item
    
    public static void updateMenuItem() {

        System.out.print("Enter food item ID to update: ");
        int menuId;

        try {
        	
            menuId = sc.nextInt();
            sc.nextLine();

            if (menuId <= 0) {
                System.out.println("Enter a valid item ID.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid item ID.");
            return;
        }

        System.out.print("Enter new item name: ");
        String itemName = sc.nextLine().trim();

        System.out.print("Enter new category: ");
        String category = sc.nextLine().trim();

        System.out.print("Enter new price: ");
        double price;

        try {
            price = sc.nextDouble();
            sc.nextLine();

            if (price <= 0) {
                System.out.println("Price must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid price.");
            return;
        }

        String query = """
                UPDATE Menu
                SET item_name = ?, category = ?, price = ?
                WHERE menu_id = ?
                """;

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, itemName);
            ps.setString(2, category);
            ps.setDouble(3, price);
            ps.setInt(4, menuId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Food item updated successfully.");
            } else {
                System.out.println("Food item not found.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 5. Delete food item
    
    public static void deleteMenuItem() {

        System.out.print("Enter food item ID to delete: ");
        int menuId;

        try {
            menuId = sc.nextInt();
            sc.nextLine();

            if (menuId <= 0) {
                System.out.println("Enter a valid item ID.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid item ID.");
            return;
        }

        String query = "DELETE FROM Menu WHERE menu_id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, menuId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Food item deleted successfully.");
            } else {
                System.out.println("Food item not found.");
            }

        } catch (SQLException e) {
            System.out.println("Cannot delete this item. It may be used in an existing order.");
        }
    }

    // 6. Menu options
    
    public static void menuOperations() {

        int choice;

        do {
            System.out.println("\n===== MENU OPERATIONS =====");
            System.out.println("1. Add Food Item");
            System.out.println("2. View Food Menu");
            System.out.println("3. Search Food Item");
            System.out.println("4. Update Food Item");
            System.out.println("5. Delete Food Item");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1 -> addMenuItem();
                case 2 -> viewMenu();
                case 3 -> searchMenuItem();
                case 4 -> updateMenuItem();
                case 5 -> deleteMenuItem();
                case 6 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 6);
    }
}
