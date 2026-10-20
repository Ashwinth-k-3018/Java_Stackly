package com.onlinefoodordering.programs;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateTables {

    static final Connection con = DBConnection.getConnection();

    // Create Customers Table
    
    public static void createCustomer() {

        String query = """
                CREATE TABLE IF NOT EXISTS Customers (
                    customer_id INT PRIMARY KEY AUTO_INCREMENT,
                    customer_name VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE,
                    phone VARCHAR(10) NOT NULL
                )AUTO_INCREMENT = 1;
                """;

        try (Statement stmt = con.createStatement()) {

            stmt.executeUpdate(query);
            System.out.println("Customers table created successfully.");

        } catch (SQLException e) {
            System.out.println("Error creating Customers table.");
            e.printStackTrace();
        }
    }

    // Create Menu Table
    public static void createMenu() {

        String query = """
                CREATE TABLE IF NOT EXISTS Menu (
                    menu_id INT PRIMARY KEY AUTO_INCREMENT,
                    item_name VARCHAR(100) NOT NULL,
                    category VARCHAR(50) NOT NULL,
                    price DECIMAL(10,2) NOT NULL,
                    availability BOOLEAN DEFAULT TRUE,
                    CHECK (price > 0)
                )AUTO_INCREMENT = 101;
                """;

        try (Statement stmt = con.createStatement()) {

            stmt.executeUpdate(query);
            System.out.println("Menu table created successfully.");

        } catch (SQLException e) {
            System.out.println("Error creating Menu table.");
            e.printStackTrace();
        }
    }

    // Create Orders Table
    public static void createOrders() {

        String query = """
                CREATE TABLE IF NOT EXISTS Orders (
                    order_id INT PRIMARY KEY AUTO_INCREMENT,
                    customer_id INT NOT NULL,
                    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    total_amount DECIMAL(10,2) DEFAULT 0.00,
                    order_status VARCHAR(20) DEFAULT 'PLACED',

                    FOREIGN KEY (customer_id)
                    REFERENCES Customers(customer_id)
                )AUTO_INCREMENT = 1001;
                """;

        try (Statement stmt = con.createStatement()) {

            stmt.executeUpdate(query);
            System.out.println("Orders table created successfully.");

        } catch (SQLException e) {
            System.out.println("Error creating Orders table.");
            e.printStackTrace();
        }
    }

    // Create Order_Items Table
    public static void createOrderItems() {

        String query = """
                CREATE TABLE IF NOT EXISTS Order_Items (
                    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
                    order_id INT NOT NULL,
                    menu_id INT NOT NULL,
                    quantity INT NOT NULL,
                    price DECIMAL(10,2) NOT NULL,

                    FOREIGN KEY (order_id)
                    REFERENCES Orders(order_id)
                    ON DELETE CASCADE,

                    FOREIGN KEY (menu_id)
                    REFERENCES Menu(menu_id),

                    CHECK (quantity > 0),
                    CHECK (price > 0)
                )AUTO_INCREMENT = 5001;
                """;

        try (Statement stmt = con.createStatement()) {

            stmt.executeUpdate(query);
            System.out.println("Order_Items table created successfully.");

        } catch (SQLException e) {
            System.out.println("Error creating Order_Items table.");
            e.printStackTrace();
        }
    }

    // Create All Tables
    public static void createAllTables() {

        createCustomer();
        createMenu();
        createOrders();
        createOrderItems();

        System.out.println("All table creation methods executed.");
    }

    // Table Creation Menu
    public static  void tableMenu() {

        int choice;

        do {
            System.out.println("\n===== TABLE CREATION MENU =====");
            System.out.println("1. Create Customers Table");
            System.out.println("2. Create Menu Table");
            System.out.println("3. Create Orders Table");
            System.out.println("4. Create Order_Items Table");
            System.out.println("5. Create All Tables");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = MainClass.sc.nextInt();

            switch (choice) {
                case 1:{
                    createCustomer();
                    break;
                    }
                case 2:{
                    createMenu();
                    break;
                    }
                case 3:{
                    createOrders();
                    break;
                    }
                case 4:{
                    createOrderItems();
                    break;
                    }
                case 5:{
                    createAllTables();
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
