
package com.bankingmanagement.programs;

import java.util.Scanner;

public class MainClass {

	public static Scanner sc = new Scanner(System.in);
	
	
	public static void main(String[] args) {

		CreateTables createTables = new CreateTables();
		CustomerOperations customerOperations = new CustomerOperations();
		AccountOperations accountOperations = new AccountOperations();
		TransferOperations transferOperations = new TransferOperations();
		TransactionHistory transactionHistory = new TransactionHistory();

		int choice;

		do {
			System.out.println("\n==================================");
			System.out.println("   BANKING MANAGEMENT SYSTEM");
			System.out.println("==================================");
			System.out.println("1. Table Creations");
			System.out.println("2. Customer Operations");
			System.out.println("3. Account Operations");
			System.out.println("4. Deposit / Withdraw / Transfer");
			System.out.println("5. Transaction History");
			System.out.println("6. Exit");
			System.out.println("==================================");
			System.out.print("Enter your choice: ");

			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {

			case 1: {
				createTables.tableMenu();
				break;
			}
			case 2: {
				customerOperations.customerMenu();
				break;
			}
			case 3: {
				accountOperations.accountMenu();
				break;
			}
			case 4: {
				transferOperations.transferMenu();
				break;
			}
			case 5: {
				transactionHistory.transactionHistoryMenu();
				break;
			}
			case 6: {
				System.out.println("Thank you for using our Banking Management System!");
				break;
			}
			default: {
				System.out.println("Invalid choice! Please select 1 to 5.");
			}
			}

		} while (choice != 6);
		sc.close();
	}
}