package com.example.std.exception;

public class RechargeNotFoundException extends RuntimeException {
	
	 private static final long serialVersionUID = 1L;

    public RechargeNotFoundException(String message) {
        super(message);
    }
}

