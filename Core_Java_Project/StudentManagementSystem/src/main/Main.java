package main;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import model.Student;
import model.StudentStatus;
import service.StudentFileService;
import service.StudentService;

public class Main {

    public static void main(String[] args) {

    	Map<Integer, Student> students = new HashMap<>();

    	StudentService studentService = new StudentService(students);
        
        StudentFileService fileService = new StudentFileService();

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {

            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search by Student ID");
            System.out.println("4. Search by Name");
            System.out.println("5. Update Student Marks");
            System.out.println("6. Update Attendance");
            System.out.println("7. Update Status");
            System.out.println("8. Calculate Grade");
            System.out.println("9. Remove Student");
            System.out.println("10. Display Topper");
            System.out.println("11. Save Students");
            System.out.println("12. Load Students");
            System.out.println("13. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

	            case 1: {
	
	                try {
	                	
	                	System.out.print("How many Student do you want to register? ");
		                int count = scanner.nextInt();
		                scanner.nextLine();
		
		                for (int i = 1; i <= count; i++) {
		                	
		                	System.out.println("\nEnter details for Student " + i);
		                	
		                	System.out.print("Enter Student ID: ");
		                    int id = scanner.nextInt();
		                    scanner.nextLine();
		
		                    System.out.print("Enter Student Name: ");
		                    String name = scanner.nextLine();
		
		                    System.out.print("Enter Student Email: ");
		                    String email = scanner.nextLine();
		
		                    System.out.print("Enter Marks: ");
		                    double marks = scanner.nextDouble();
		
		                    System.out.print("Enter Attendance: ");
		                    int attendance = scanner.nextInt();
		                    scanner.nextLine();
		
		                    System.out.print("Enter Status (ACTIVE/INACTIVE/COMPLETED): ");
							StudentStatus status = StudentStatus.valueOf(scanner.nextLine().toUpperCase());
		
							Student student = new Student(id, name, email, marks, attendance, status);
		
		                    studentService.addStudent(student);
		                }
	                } 
	                catch (InputMismatchException e) {
	
	                    System.out.println("Invalid input. Please enter the correct data type.");
	                    scanner.nextLine();
	
	                } catch (IllegalArgumentException e) {
	
	                    System.out.println("Invalid status. Use ACTIVE, INACTIVE, or COMPLETED.");
	                }
	
	                break;
	            }
	            case 2: {

	                studentService.displayStudents();

	                break;
	            }
	            case 3: {

	                try {
	                    System.out.print("Enter Student ID: ");
	                    int id = scanner.nextInt();
	                    scanner.nextLine();

	                    studentService.searchStudentById(id);

	                } catch (InputMismatchException e) {
	                    System.out.println("Invalid Student ID. Please enter a number.");
	                    scanner.nextLine();
	                }

	                break;
	            }
	            case 4: {

	                System.out.print("Enter Student Name: ");
	                String name = scanner.nextLine();

	                studentService.searchStudentByName(name);

	                break;
	            }
	            case 5: {

	                try {
	                    System.out.print("Enter Student ID: ");
	                    int id = scanner.nextInt();

	                    System.out.print("Enter New Marks: ");
	                    double marks = scanner.nextDouble();
	                    scanner.nextLine();

	                    studentService.updateMarks(id, marks);

	                } catch (InputMismatchException e) {

	                    System.out.println("Invalid input. Please enter a number.");
	                    scanner.nextLine();
	                }

	                break;
	            }
	            case 6: {

	                try {
	                    System.out.print("Enter Student ID: ");
	                    int id = scanner.nextInt();

	                    System.out.print("Enter New Attendance: ");
	                    int attendance = scanner.nextInt();
	                    scanner.nextLine();

	                    studentService.updateAttendance(id, attendance);

	                } catch (InputMismatchException e) {

	                    System.out.println("Invalid input. Please enter a number.");
	                    scanner.nextLine();
	                }

	                break;
	            }
	            case 7: {

	                try {
	                    System.out.print("Enter Student ID: ");
	                    int id = scanner.nextInt();
	                    scanner.nextLine();

	                    System.out.print("Enter New Status (ACTIVE/INACTIVE/COMPLETED): ");
						StudentStatus status = StudentStatus.valueOf(scanner.nextLine().toUpperCase());

	                    studentService.updateStatus(id, status);

	                } catch (InputMismatchException e) {

	                    System.out.println("Invalid Student ID. Please enter a number.");
	                    scanner.nextLine();

	                } catch (IllegalArgumentException e) {

	                    System.out.println("Invalid status. Use ACTIVE, INACTIVE, or COMPLETED.");
	                }

	                break;
	            }
	            case 8: {

	                try {
	                    System.out.print("Enter Student ID: ");
	                    int id = scanner.nextInt();
	                    scanner.nextLine();

	                    studentService.calculateGrade(id);

	                } catch (InputMismatchException e) {

	                    System.out.println("Invalid Student ID. Please enter a number.");
	                    scanner.nextLine();
	                }
	                break;
	            }
	            case 9: {
	                try {
	                    System.out.print("Enter Student ID: ");
	                    int id = scanner.nextInt();
	                    scanner.nextLine();

	                    studentService.removeStudent(id);

	                } catch (InputMismatchException e) {

	                    System.out.println("Invalid Student ID. Please enter a number.");
	                    scanner.nextLine();
	                }

	                break;
	            }
	            case 10: {

	                studentService.findTopper();
	                break;
	            }	
	            case 11: {

	                fileService.saveStudents(students);
	                break;
	            }
	            case 12: {
	            	
	                try {
	                    fileService.loadStudents(students);
	                } catch (Exception e) {
	                    System.out.println("Error while loading students.");
	                }
	                
	                break;
	            }
                case 13:{
                    running = false;
                    System.out.println("Thank you!");
                    break;
                }
                default:{
                    System.out.println("Invalid choice.");
                }
            }
        }
        scanner.close();
    }
}