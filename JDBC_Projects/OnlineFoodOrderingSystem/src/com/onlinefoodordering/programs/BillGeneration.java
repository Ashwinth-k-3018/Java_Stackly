package com.onlinefoodordering.programs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BillGeneration {

    static final Connection con = DBConnection.getConnection();
    static final Scanner sc = new Scanner(System.in);

    public static void generateBill() {

        System.out.print("Enter customer ID: ");
        int customerId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter order ID: ");
        int orderId = sc.nextInt();
        sc.nextLine();

        String orderQuery =
                "SELECT o.order_id, o.customer_id, o.order_date, "
                + "o.total_amount, o.order_status, c.customer_name, "
                + "c.email, c.phone "
                + "FROM Orders o "
                + "JOIN Customers c ON o.customer_id = c.customer_id "
                + "WHERE o.order_id = ? AND o.customer_id = ?";

        try {
            PreparedStatement orderPs = con.prepareStatement(orderQuery);

            orderPs.setInt(1, orderId);
            orderPs.setInt(2, customerId);

            ResultSet orderRs = orderPs.executeQuery();

            if (!orderRs.next()) {
                System.out.println("Order not found for this customer!");
                return;
            }

            String customerName = orderRs.getString("customer_name");
            String email = orderRs.getString("email");
            String phone = orderRs.getString("phone");
            String orderDate = orderRs.getString("order_date");
            String orderStatus = orderRs.getString("order_status");
            double totalAmount = orderRs.getDouble("total_amount");

            String itemQuery =
                    "SELECT m.item_name, oi.quantity, oi.price "
                    + "FROM Order_Items oi "
                    + "JOIN Menu m ON oi.menu_id = m.menu_id "
                    + "WHERE oi.order_id = ?";

            PreparedStatement itemPs = con.prepareStatement(itemQuery);
            itemPs.setInt(1, orderId);

            ResultSet itemRs = itemPs.executeQuery();

            System.out.println("\n==============================================");
            System.out.println("             ONLINE FOOD ORDERING");
            System.out.println("                    BILL");
            System.out.println("==============================================");

            System.out.println("Order ID      : " + orderId);
            System.out.println("Customer ID   : " + customerId);
            System.out.println("Customer Name : " + customerName);
            System.out.println("Email         : " + email);
            System.out.println("Phone         : " + phone);
            System.out.println("Order Date    : " + orderDate);
            System.out.println("Order Status  : " + orderStatus);

            System.out.println("----------------------------------------------");
            System.out.printf("%-18s %-8s %-10s %-10s%n",
                    "Food Item", "Qty", "Price", "Amount");
            System.out.println("----------------------------------------------");


            while (itemRs.next()) {

                String itemName = itemRs.getString("item_name");
                int quantity = itemRs.getInt("quantity");
                double price = itemRs.getDouble("price");

                double itemTotal = price * quantity;
     
                System.out.printf("%-18s %-8d %-10.2f %-10.2f%n",
                        itemName, quantity, price, itemTotal);
            }

            System.out.println("----------------------------------------------");
            System.out.printf("Total Amount: Rs. %.2f%n", totalAmount);
            System.out.println("==============================================");
            System.out.println("          Thank you for your order!");
            System.out.println("==============================================");

        } catch (SQLException e) {
            System.out.println("Error generating bill: " + e.getMessage());
        }
    }
}
