package com.onlinefoodordering.programs;

import java.util.Scanner;

public class MainClass {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n==================================");
            System.out.println("   ONLINE FOOD ORDERING SYSTEM");
            System.out.println("==================================");
            System.out.println("1. Create Tables");
            System.out.println("2. Customer Operations");
            System.out.println("3. Menu Operations");
            System.out.println("4. Order Operations");
            System.out.println("5. Generate Bill");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    CreateTables.tableMenu();
                    break;

                case 2:
                    CustomerOperations.customerMenu();
                    break;

                case 3:
                    MenuOperations.menuOperations();
                    break;

                case 4:
                    OrderOperations.orderMenu();
                    break;

                case 5:
                    BillGeneration.generateBill();
                    break;

                case 6:
                    System.out.println(
                            "Thank you for using Online Food Ordering System!"
                    );
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 6);

        sc.close();
    }
}
