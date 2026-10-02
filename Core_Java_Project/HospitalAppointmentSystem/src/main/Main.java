package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import model.Doctor;
import model.Patient;
import service.HospitalService;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        HospitalService service = new HospitalService();

        while (true) {

            System.out.println("\n===== Hospital Management System =====");
            System.out.println("1. Register Patient");
            System.out.println("2. Add Doctor");
            System.out.println("3. Book Appointment");
            System.out.println("4. Cancel Appointment");
            System.out.println("5. View Available Slots");
            System.out.println("6. View Doctor Schedule");
            System.out.println("7. View Patient History");
            System.out.println("8. Search Doctor by Specialization");
            System.out.println("9. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

	            case 1: {
	            	try {
		                System.out.print("How many patients do you want to register? ");
		                int count = scanner.nextInt();
		                scanner.nextLine();
		
		                for (int i = 1; i <= count; i++) {
		
		                    System.out.println("\nEnter details for Patient " + i);
		
		                    System.out.print("Enter Patient ID: ");
		                    int patId = scanner.nextInt();
		                    scanner.nextLine();
		
		                    System.out.print("Enter Patient Name: ");
		                    String patName = scanner.nextLine();
		
		                    System.out.print("Enter Patient Age: ");
		                    int age = scanner.nextInt();
		                    scanner.nextLine();
		
		                    System.out.print("Enter Patient Gender: ");
		                    String gender = scanner.nextLine();
		
		                    System.out.print("Enter Patient Phone: ");
		                    String phone = scanner.nextLine();
		
		                    Patient patient = new Patient(patId, patName, age, gender, phone);
		
		                    service.registerPatient(patient);
		                }
	            	} catch (InputMismatchException e) {
	                    System.out.println("Invalid input. Check Again!!");
	                    scanner.nextLine();
	                }
	
	                break;
	            }
	            case 2: {

	                try {
	                    System.out.print("How many doctors do you want to add? ");
	                    int count = scanner.nextInt();
	                    scanner.nextLine();

	                    for (int i = 1; i <= count; i++) {

	                        System.out.println("\nEnter details for Doctor " + i);

	                        System.out.print("Enter Doctor ID: ");
	                        int docId = scanner.nextInt();
	                        scanner.nextLine();

	                        System.out.print("Enter Doctor Name: ");
	                        String docName = scanner.nextLine();

	                        System.out.print("Enter Doctor Specialization: ");
	                        String specialization = scanner.nextLine();

	                        Doctor doctor = new Doctor(docId, docName, specialization);

	                        service.addDoctor(doctor);
	                    }

	                } catch (InputMismatchException e) {
	                    System.out.println("Invalid input. Check Again.");
	                    scanner.nextLine();
	                }

	                break;
	            }
	            case 3: {

	                try {
	                    System.out.print("Enter Patient ID: ");
	                    int patientId = scanner.nextInt();

	                    System.out.print("Enter Doctor ID: ");
	                    int doctorId = scanner.nextInt();
	                    scanner.nextLine();

	                    System.out.print("Enter Appointment Date (yyyy-MM-dd): ");
	                    LocalDate date = LocalDate.parse(scanner.nextLine());

	                    System.out.print("Enter Appointment Time (HH:mm): ");
	                    LocalTime time = LocalTime.parse(scanner.nextLine());

	                    service.bookAppointment(patientId, doctorId, date, time);

	                } catch (InputMismatchException e) {

	                    System.out.println("Please enter a valid number.");
	                    scanner.nextLine();

	                } catch (DateTimeParseException e) {

	                    System.out.println("Invalid date or time format.");
	                    System.out.println("Use date: yyyy-MM-dd");
	                    System.out.println("Use time: HH:mm");
	                }

	                break;
	            }
                case 4:{

                    System.out.print("Enter Appointment ID: ");
                    String appointmentId = scanner.nextLine().toUpperCase();

                    service.cancelAppointment(appointmentId);

                    break;
                }
                case 5:{
                	
                    System.out.print("Enter Doctor ID: ");
                    int doctorId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Date (yyyy-MM-dd): ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());

                    service.viewAvailableSlots(doctorId, date);

                    break;
                }
                case 6: {

                    System.out.print("Enter Doctor ID: ");
                    int doctorId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Date (yyyy-MM-dd): ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());

                    service.viewDoctorSchedule(doctorId, date);

                    break;
                }
                case 7: {

                    System.out.print("Enter Patient ID: ");
                    int patientId = scanner.nextInt();
                    scanner.nextLine();

                    service.viewPatientHistory(patientId);

                    break;
                }
                case 8: {

                    System.out.print("Enter Specialization: ");
                    String specialization = scanner.nextLine();

                    service.searchDoctorBySpecialization(specialization);

                    break;
                }
                case 9:
                    System.out.println("Thank you!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}