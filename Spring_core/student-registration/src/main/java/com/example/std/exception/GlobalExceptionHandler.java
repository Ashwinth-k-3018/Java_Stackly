package com.example.std.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle student not found
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFound(
            StudentNotFoundException ex) {

        return new ResponseEntity<>(
                ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle duplicate registration
    @ExceptionHandler(DuplicateRegistrationException.class)
    public ResponseEntity<String> handleDuplicateRegistration(
            DuplicateRegistrationException ex) {

        return new ResponseEntity<>(
                ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return new ResponseEntity<>(
                message, HttpStatus.BAD_REQUEST);
    }
}
