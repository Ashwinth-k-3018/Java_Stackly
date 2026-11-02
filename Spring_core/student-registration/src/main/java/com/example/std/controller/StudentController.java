package com.example.std.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.std.model.Student;
import com.example.std.service.StudentService;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // 1. Register student
    @PostMapping
    public ResponseEntity<Student> registerStudent(
            @Valid @RequestBody Student student) {

        Student savedStudent = studentService.registerStudent(student);

        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    // 2. View all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {

        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // 3. Find student by ID
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable Integer studentId) {

        return ResponseEntity.ok(
                studentService.getStudentById(studentId));
    }

    // 4. Update student
    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Integer studentId,
            @Valid @RequestBody Student student) {

        return ResponseEntity.ok(
                studentService.updateStudent(studentId, student));
    }
    
    // update only number
    
    @PatchMapping("/{studentId}/phone")
    public ResponseEntity<Student> updatePhone(
            @PathVariable Integer studentId,
            @RequestBody Map<String, String> request) {

        return ResponseEntity.ok(
                studentService.updatePhone(
                        studentId, request.get("phone")));
    }

    // 5. Delete registration
    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable Integer studentId) {

        return ResponseEntity.ok(
                studentService.deleteStudent(studentId));
    }

    // 6. Search by student name
    @GetMapping("/search/name")
    public ResponseEntity<List<Student>> searchByName(
            @RequestParam String name) {

        return ResponseEntity.ok(
                studentService.searchByName(name));
    }

    // 7. Search by course
    @GetMapping("/search/course")
    public ResponseEntity<List<Student>> searchByCourse(
            @RequestParam String courseName) {

        return ResponseEntity.ok(
                studentService.searchByCourse(courseName));
    }

    // 8. Search by email
    @GetMapping("/search/email")
    public ResponseEntity<Student> searchByEmail(
            @RequestParam String email) {

        return ResponseEntity.ok(
                studentService.searchByEmail(email));
    }

    // 9. Filter by age
    @GetMapping("/age")
    public ResponseEntity<List<Student>> getStudentsByAge(
            @RequestParam Integer age) {

        return ResponseEntity.ok(
                studentService.getStudentsByAge(age));
    }

    // 10. Total registrations
    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalStudents() {

        return ResponseEntity.ok(
                studentService.getTotalStudents());
    }
}
