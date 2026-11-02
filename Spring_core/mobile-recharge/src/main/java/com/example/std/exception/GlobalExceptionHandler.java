package com.example.std.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle recharge not found
	
    @ExceptionHandler(RechargeNotFoundException.class)
    public ResponseEntity<String> handleRechargeNotFound(
            RechargeNotFoundException ex) {

        return new ResponseEntity<>(
                ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle duplicate recharge
    
    @ExceptionHandler(DuplicateRechargeException.class)
    public ResponseEntity<String> handleDuplicateRecharge(
            DuplicateRechargeException ex) {

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
