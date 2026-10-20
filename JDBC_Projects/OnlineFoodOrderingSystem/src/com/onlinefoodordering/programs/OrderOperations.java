
package com.onlinefoodordering.programs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderOperations {

    static final Connection con = DBConnection.getConnection();
    static final Scanner sc = new Scanner(System.in);

    // Place a new order
    
    public static void placeOrder() {

        System.out.print("Enter customer ID: ");
        int customerId = sc.nextInt();
        sc.nextLine();

        // Check whether customer exists
        
        try {
            String customerQuery =
                    "SELECT customer_id FROM Customers WHERE customer_id = ?";

            PreparedStatement ps = con.prepareStatement(customerQuery);
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Customer not found!");
                return;
            }

        } catch (SQLException e) {
            System.out.println("Error checking customer: " + e.getMessage());
            return;
        }

        List<Integer> menuIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        // Select food items
        
        while (true) {

            System.out.print("Enter menu ID (0 to finish): ");
            int menuId = sc.nextInt();
            sc.nextLine();

            if (menuId == 0) {
                break;
            }

            if (menuId < 0) {
                System.out.println("Invalid menu ID!");
                continue;
            }

            System.out.print("Enter quantity: ");
            int quantity = sc.nextInt();
            sc.nextLine();

            if (quantity <= 0) {
                System.out.println("Quantity must be greater than zero!");
                continue;
            }

            menuIds.add(menuId);
            quantities.add(quantity);
        }

        if (menuIds.isEmpty()) {
            System.out.println("No items selected. Order cancelled.");
            return;
        }

        List<Double> prices = new ArrayList<>();
        double totalAmount = 0.0;

        // Get prices and calculate total
        
        try {
            String priceQuery =
                    "SELECT price FROM Menu WHERE menu_id = ? AND availability = TRUE";

            PreparedStatement ps = con.prepareStatement(priceQuery);

            for (int i = 0; i < menuIds.size(); i++) {

                ps.setInt(1, menuIds.get(i));

                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    System.out.println("Menu item ID " + menuIds.get(i)+ " not found or unavailable.");
                    return;
                }

                double price = rs.getDouble("price");

                prices.add(price);

                double itemTotal = price * quantities.get(i);
                totalAmount += itemTotal;
            }

        } catch (SQLException e) {
            System.out.println("Error getting food prices: " + e.getMessage());
            return;
        }

        // Insert order and order items in one transaction
        
        try {
            con.setAutoCommit(false);

            String orderQuery =
                    "INSERT INTO Orders (customer_id, total_amount, order_status) "
                            + "VALUES (?, ?, 'PLACED')";

            PreparedStatement orderPs = con.prepareStatement(orderQuery,PreparedStatement.RETURN_GENERATED_KEYS);

            orderPs.setInt(1, customerId);
            orderPs.setDouble(2, totalAmount);

            orderPs.executeUpdate();

            ResultSet generatedKeys = orderPs.getGeneratedKeys();

            int orderId;

            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Order ID was not generated.");
            }

            String itemQuery =
                    "INSERT INTO Order_Items (order_id, menu_id, quantity, price) "
                            + "VALUES (?, ?, ?, ?)";

            PreparedStatement itemPs = con.prepareStatement(itemQuery);

            for (int i = 0; i < menuIds.size(); i++) {

                itemPs.setInt(1, orderId);
                itemPs.setInt(2, menuIds.get(i));
                itemPs.setInt(3, quantities.get(i));
                itemPs.setDouble(4, prices.get(i));

                itemPs.executeUpdate();
            }

            con.commit();

            System.out.println("\nOrder placed successfully!");
            System.out.println("Order ID: " + orderId);
            System.out.println("Total Amount: Rs. " + totalAmount);

        } catch (SQLException e) {

            try {
                con.rollback();
                System.out.println("Order failed. Changes rolled back.");
            } catch (SQLException rollbackError) {
                System.out.println(
                        "Rollback error: " + rollbackError.getMessage()
                );
            }

            System.out.println("Error placing order: " + e.getMessage());

        } finally {

            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(
                        "Error resetting transaction: " + e.getMessage()
                );
            }
        }
    }

    // View all orders placed by a customer
    public static void viewOrderHistory() {

        System.out.print("Enter customer ID: ");
        int customerId = sc.nextInt();
        sc.nextLine();

        String query =
                "SELECT order_id, order_date, total_amount, order_status "
                        + "FROM Orders WHERE customer_id = ? ORDER BY order_id DESC";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            System.out.println("\n--- Order History ---");

            while (rs.next()) {
                found = true;

                System.out.println("Order ID: " + rs.getInt("order_id"));
                System.out.println("Order Date: " + rs.getTimestamp("order_date"));
                System.out.println(
                        "Total Amount: Rs. " + rs.getDouble("total_amount")
                );
                System.out.println("Status: " + rs.getString("order_status"));
                System.out.println("----------------------------");
            }

            if (!found) {
                System.out.println("No orders found for this customer.");
            }

        } catch (SQLException e) {
            System.out.println("Error viewing order history: " + e.getMessage());
        }
    }

    // View one order and its food items
    
    public static void viewOrderDetails() {

        System.out.print("Enter customer ID: ");
        int customerId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter order ID: ");
        int orderId = sc.nextInt();
        sc.nextLine();

        String orderQuery =
                "SELECT order_id, order_date, total_amount, order_status "
                        + "FROM Orders WHERE order_id = ? AND customer_id = ?";

        try {
            PreparedStatement orderPs = con.prepareStatement(orderQuery);

            orderPs.setInt(1, orderId);
            orderPs.setInt(2, customerId);

            ResultSet orderRs = orderPs.executeQuery();

            if (!orderRs.next()) {
                System.out.println("Order not found for this customer.");
                return;
            }

            System.out.println("\n--- Order Details ---");
            System.out.println("Order ID: " + orderRs.getInt("order_id"));
            System.out.println("Order Date: " + orderRs.getTimestamp("order_date"));
            System.out.println(
                    "Total Amount: Rs. " + orderRs.getDouble("total_amount")
            );
            System.out.println("Status: " + orderRs.getString("order_status"));

            String itemsQuery =
                    "SELECT m.item_name, oi.quantity, oi.price "
                            + "FROM Order_Items oi "
                            + "JOIN Menu m ON oi.menu_id = m.menu_id "
                            + "WHERE oi.order_id = ?";

            PreparedStatement itemPs = con.prepareStatement(itemsQuery);
            itemPs.setInt(1, orderId);

            ResultSet itemRs = itemPs.executeQuery();

            System.out.println("\nFood Items:");

            while (itemRs.next()) {

                String itemName = itemRs.getString("item_name");
                int quantity = itemRs.getInt("quantity");
                double price = itemRs.getDouble("price");

                System.out.println("Item: " + itemName);
                System.out.println("Quantity: " + quantity);
                System.out.println("Price per item: Rs. " + price);
                System.out.println("Item total: Rs. " + (price * quantity));
                System.out.println("----------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error viewing order details: " + e.getMessage());
        }
    }

    // Order menu
    public static void orderMenu() {

        int choice;

        do {
            System.out.println("\n===== ORDER MENU =====");
            System.out.println("1. Place Order");
            System.out.println("2. View Order History");
            System.out.println("3. View Order Details");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    placeOrder();
                    break;

                case 2:
                    viewOrderHistory();
                    break;

                case 3:
                    viewOrderDetails();
                    break;

                case 4:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 4);
    }
}
