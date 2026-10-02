package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import model.Student;
import model.StudentStatus;

public class StudentFileService {
	
	// write student into file 
	public void saveStudents(Map<Integer, Student> students) {

	    try {
	        FileWriter fileWriter = new FileWriter("data/students.txt");
	        PrintWriter writer = new PrintWriter(fileWriter);

	        for (Student student : students.values()) {
	            writer.println(student);
	        }

	        writer.close();

	        System.out.println("Student details saved successfully.");

	    } catch (IOException e) {
	        System.out.println("Error while saving student details.");
	    }
	}
	
	// read student from the file 
	public void loadStudents(Map<Integer, Student> students) {

	    try {
	        FileReader fileReader = new FileReader("data/students.txt");
	        BufferedReader reader = new BufferedReader(fileReader);

	        String line;

	        while ((line = reader.readLine()) != null) {

	            String[] data = line.split("\\|");

	            int id = Integer.parseInt(data[0].split(": ")[1].trim());
	            String name = data[1].split(": ")[1];
	            String email = data[2].split(": ")[1];
	            double marks = Double.parseDouble(data[3].split(": ")[1].trim());
	            int attendance = Integer.parseInt(data[4].split(": ")[1].trim());
	            StudentStatus status = StudentStatus.valueOf(data[5].split(": ")[1].trim());

	            Student student = new Student(id,name,email,marks,attendance,status);

	            students.put(id, student);
	        }

	        reader.close();

	        System.out.println("Student details loaded successfully.");

	    } catch (IOException e) {
	        System.out.println("Error while loading student details.");
	    }
	}

}