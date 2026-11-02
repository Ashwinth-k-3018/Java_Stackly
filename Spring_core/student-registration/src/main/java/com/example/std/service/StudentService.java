
package com.example.std.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

import com.example.std.model.Student;
import com.example.std.exception.StudentNotFoundException;
import com.example.std.exception.DuplicateRegistrationException;

@Service
public class StudentService {

    private List<Student> students = new ArrayList<>();

    private Integer studentIdCounter = 1;

    // 1. Register student
    public Student registerStudent(Student student) {

        boolean duplicate = students.stream()
                .anyMatch(s ->
                    s.getEmail().equalsIgnoreCase(student.getEmail())
                    && s.getCourseName().equalsIgnoreCase(student.getCourseName())
                );

        if (duplicate) {
            throw new DuplicateRegistrationException(
                    "Student is already registered for this course");
        }

        student.setStudentId(studentIdCounter++);

        students.add(student);

        return student;
    }

    // 2. View all students
    public List<Student> getAllStudents() {
        return students;
    }

    // 3. Find student by ID
    public Student getStudentById(Integer studentId) {

        return students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst()
                .orElseThrow(() ->
                    new StudentNotFoundException(
                        "Student not found with ID: " + studentId)
                );
    }

    // 4. Update student details
    public Student updateStudent(Integer studentId, Student updatedStudent) {

        Student existingStudent = getStudentById(studentId);

        boolean duplicate = students.stream()
                .anyMatch(s ->
                    !s.getStudentId().equals(studentId)
                    && s.getEmail().equalsIgnoreCase(updatedStudent.getEmail())
                    && s.getCourseName().equalsIgnoreCase(updatedStudent.getCourseName())
                );

        if (duplicate) {
            throw new DuplicateRegistrationException(
                    "Student is already registered for this course");
        }

        existingStudent.setStudentName(updatedStudent.getStudentName());
        existingStudent.setCourseName(updatedStudent.getCourseName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setAge(updatedStudent.getAge());
        existingStudent.setPhone(updatedStudent.getPhone());
        existingStudent.setGender(updatedStudent.getGender());

        return existingStudent;
    }

    // 5. Delete registration
    public String deleteStudent(Integer studentId) {

        Student student = getStudentById(studentId);

        students.remove(student);

        return "Student registration deleted successfully";
    }

    // 6. Search by student name
    public List<Student> searchByName(String studentName) {

        return students.stream()
                .filter(s -> s.getStudentName().toLowerCase()
                .contains(studentName.toLowerCase()))
                .collect(Collectors.toList());
    }

    // 7. Search by course name
    public List<Student> searchByCourse(String courseName) {

        return students.stream()
                .filter(s -> s.getCourseName().toLowerCase()
                .contains(courseName.toLowerCase()))
                .collect(Collectors.toList());
    }

    // 8. Search by email
    public Student searchByEmail(String email) {

        return students.stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() ->
                    new StudentNotFoundException(
                        "Student not found with email: " + email)
                );
    }

    // 9. Filter students by age
    public List<Student> getStudentsByAge(Integer age) {

        return students.stream()
                .filter(s -> s.getAge().equals(age))
                .collect(Collectors.toList());
    }

    // 10. Total registrations
    public Integer getTotalStudents() {
        return students.size();
    }

    // patch update number 
    
    public Student updatePhone(Integer studentId, String phone) {

        Student student = getStudentById(studentId);

        student.setPhone(phone);

        return student;
    }
}
