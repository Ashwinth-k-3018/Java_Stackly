package com.example.std.exception;

public class DuplicateRechargeException extends RuntimeException {
	
	 private static final long serialVersionUID = 1L;

    public DuplicateRechargeException(String message) {
        super(message);
    }
}
