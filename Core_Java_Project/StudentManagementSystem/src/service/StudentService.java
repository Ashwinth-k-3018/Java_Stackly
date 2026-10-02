package service;


import java.util.Map;

import model.Student;
import model.StudentStatus;

public class StudentService {

	private Map<Integer, Student> students;

	public StudentService(Map<Integer, Student> students) {
	    this.students = students;
	}
    
    // Add Student 
    public void addStudent(Student student) {

        if (students.containsKey(student.getId())) {
            System.out.println("\nStudent ID already exists.");
            return;
        }

        students.put(student.getId(), student);

        System.out.println("\nStudent added successfully.");
    }
    
    // Display All Student 
    public void displayStudents() {

        if (students.isEmpty()) {
            System.out.println("\nNo students found.");
            return;
        }

        for (Student student : students.values()) {
            System.out.println(student);
        }
    }
    
    // Student Search by ID 
    public void searchStudentById(int id) {

        Student student = students.get(id);

        if (student == null) {
            System.out.println("\nStudent not found.");
            return;
        }

        System.out.println("\nStudent found:");
        System.out.println(student);
    }
    
    // Search by name
    public void searchStudentByName(String name) {

        boolean found = false;

        for (Student student : students.values()) {
            if (student.getName().trim().equalsIgnoreCase(name.trim())) {
            	
                System.out.println("\nStudent found:");
                System.out.println(student);
                found = true;
            }
        }

        if (!found) {
            System.out.println("\nStudent not found.");
        }
    }
    
    // Update Marks 
    public void updateMarks(int id, double marks) {

        Student student = students.get(id);

        if (student == null) {
            System.out.println("\nStudent not found.");
            return;
        }

        student.setMarks(marks);

        System.out.println("\nMarks updated successfully.");
    }
    
    // Update Attendance
    public void updateAttendance(int id, int attendance) {

        Student student = students.get(id);

        if (student == null) {
            System.out.println("\nStudent not found.");
            return;
        }

        student.setAttendance(attendance);

        System.out.println("\nAttendance updated successfully.");
    }
    
    //Update Status
    public void updateStatus(int id, StudentStatus status) {

        Student student = students.get(id);

        if (student == null) {
            System.out.println("\nStudent not found.");
            return;
        }

        student.setStatus(status);

        System.out.println("\nStudent status updated successfully.");
    }
    
    // Calculate Grade
    public void calculateGrade(int id) {

        Student student = students.get(id);

        if (student == null) {
            System.out.println("\nStudent not found.");
            return;
        }
        double marks = student.getMarks();
        String grade;

        if (marks >= 90) {
            grade = "A";
        } else if (marks >= 80) {
            grade = "B";
        } else if (marks >= 70) {
            grade = "C";
        } else if (marks >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }

        System.out.println("\nStudent: " + student.getName());
        System.out.println("Marks: " + marks);
        System.out.println("Grade: " + grade);
    }
    
    // Find Toper
    public void findTopper() {

        if (students.isEmpty()) {
            System.out.println("\nNo students found.");
            return;
        }

        Student topper = null;

        for (Student student : students.values()) {

            if (topper == null || student.getMarks() > topper.getMarks()) {
                topper = student;
            }
        }

        System.out.println("\nTopper Details:");
        System.out.println(topper);
    }
    
    // Remove Student 
    public void removeStudent(int id) {

        Student student = students.get(id);

        if (student == null) {
            System.out.println("\nStudent not found.");
            return;
        }

        students.remove(id);

        System.out.println("\nStudent removed successfully.");
    }
}