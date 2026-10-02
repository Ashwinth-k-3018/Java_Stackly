package service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Appointment;
import model.Doctor;
import model.Patient;

public class HospitalService {

	private Map<Integer, Patient> patients = new HashMap<>();
	private Map<Integer, Doctor> doctors = new HashMap<>();
	private Map<String, Appointment> appointments = new HashMap<>();
	
	// Register Patient 
	
	public void registerPatient(Patient patient) {

		if (patients.containsKey(patient.getPat_ID())) {
			System.out.println("\nPatient ID already exists.");
			return;
		}

		patients.put(patient.getPat_ID(), patient);

		System.out.println("\nPatient registered successfully.");
	}
		
	//Add Doctor
	
	public void addDoctor(Doctor doctor) {

		if (doctors.containsKey(doctor.getDoc_Id())) {
			System.out.println("\nDoctor ID already exists.");
			return;
		}

		doctors.put(doctor.getDoc_Id(), doctor);

		System.out.println("\nDoctor added successfully.");
	}
	
	// Book Appointment
	
	public void bookAppointment(int patientId, int doctorId, LocalDate date, LocalTime time) {

		Patient patient = patients.get(patientId);

		if (patient == null) {
			System.out.println("\nPatient not found.");
			return;
		}

		Doctor doctor = doctors.get(doctorId);

		if (doctor == null) {
			System.out.println("\nDoctor not found.");
			return;
		}

		System.out.println("Patient: " + patient.getPat_Name());
		System.out.println("Doctor: " + doctor.getDoc_Name());
		System.out.println("Date: " + date);
		System.out.println("Time: " + time);
		
		// Check Doc Available
		
		for (Appointment appointment : appointments.values()) {

		    if (appointment.getDoctor().getDoc_Id() == doctorId
		            && appointment.getDate().equals(date)
		            && appointment.getTime().equals(time)) {

		    	 throw new IllegalStateException("\nDoctor is already booked at this date and time.");
		    }
		}
		
		// Check Patient appointment  
		
		for (Appointment appointment : appointments.values()) {

		    if (appointment.getPatient().getPat_ID() == patientId
		            && appointment.getDate().equals(date)
		            && appointment.getTime().equals(time)) {

		        System.out.println("\nPatient already has an appointment at this date and time.");
		        return;
		    }
		}
		
		
		String appointmentId = "AP0" + (appointments.size() + 1);

		Appointment appointment = new Appointment(appointmentId,patient,doctor,date,time);

		appointments.put(appointmentId, appointment);

		System.out.println("\nAppointment booked successfully.");
		System.out.println("Appointment ID: " + appointmentId);
	}
	
	// Cancel Appointment
	
	public void cancelAppointment(String appointmentId) {

	    Appointment appointment = appointments.get(appointmentId);

	    if (appointment == null) {
	        System.out.println("\nAppointment not found.");
	        return;
	    }

	    appointments.remove(appointmentId);

	    System.out.println("\nAppointment cancelled successfully.");
	}
	
	// View Available slots
	
	public void viewAvailableSlots(int doctorId, LocalDate date) {

	    Doctor doctor = doctors.get(doctorId);

	    if (doctor == null) {
	        System.out.println("Doctor not found.");
	        return;
	    }

	    LocalTime[] slots = {
	        LocalTime.of(9, 0),
	        LocalTime.of(9, 30),
	        LocalTime.of(10, 0),
	        LocalTime.of(10, 30),
	        LocalTime.of(11, 0),
	        LocalTime.of(11, 30)
	    };

	    System.out.println("\nAvailable slots for " + doctor.getDoc_Name());
	    System.out.println("Date: " + date);

	    for (LocalTime slot : slots) {

	        boolean booked = false;

	        for (Appointment appointment : appointments.values()) {

	            if (appointment.getDoctor().getDoc_Id() == doctorId
	                    && appointment.getDate().equals(date)
	                    && appointment.getTime().equals(slot)) {

	                booked = true;
	                break;
	            }
	        }

	        if (!booked) {
	            System.out.println(slot);
	        }
	    }
	}
	
	// View Doctor Schedule 
	
	public void viewDoctorSchedule(int doctorId, LocalDate date) {

	    Doctor doctor = doctors.get(doctorId);

	    if (doctor == null) {
	        System.out.println("Doctor not found.");
	        return;
	    }

	    System.out.println("\nDoctor Schedule");
	    System.out.println("Doctor: " + doctor.getDoc_Name());
	    System.out.println("Date: " + date);
	    System.out.println();

	    boolean found = false;

	    for (Appointment appointment : appointments.values()) {

	        if (appointment.getDoctor().getDoc_Id() == doctorId
	                && appointment.getDate().equals(date)) {

	            System.out.println(
	                    appointment.getTime() + " - "
	                    + appointment.getPatient().getPat_Name()
	            );

	            found = true;
	        }
	    }

	    if (!found) {
	        System.out.println("\nNo appointments scheduled.");
	    }
	}
	
	public void viewPatientHistory(int patientId) {

	    Patient patient = patients.get(patientId);

	    if (patient == null) {
	        System.out.println("\nPatient not found.");
	        return;
	    }

	    ArrayList<Appointment> history = new ArrayList<>();

	    for (Appointment appointment : appointments.values()) {

	        if (appointment.getPatient().getPat_ID() == patientId) {
	            history.add(appointment);
	        }
	    }

	    System.out.println("\nPatient History");
	    System.out.println("Patient: " + patient.getPat_Name());

	    if (history.isEmpty()) {
	        System.out.println("\nNo appointments found.");
	        return;
	    }

	    for (Appointment appointment : history) {

	        System.out.println(
	                "\nAppointment ID: " + appointment.getAppointmentId()
	                + " | Doctor: " + appointment.getDoctor().getDoc_Name()
	                + " | Date: " + appointment.getDate()
	                + " | Time: " + appointment.getTime()
	        );
	    }
	}
	
	public void searchDoctorBySpecialization(String specialization) {

	    boolean found = false;

	    for (Doctor doctor : doctors.values()) {

	        if (doctor.getDoc_Specialization().equalsIgnoreCase(specialization)) {

	            System.out.println(
	                    "\nDoctor ID: " + doctor.getDoc_Id()
	                    + " | Name: " + doctor.getDoc_Name()
	                    + " | Specialization: " + doctor.getDoc_Specialization()
	            );

	            found = true;
	        }
	    }

	    if (!found) {
	        System.out.println("\nNo doctor found for this specialization.");
	    }
	}
}