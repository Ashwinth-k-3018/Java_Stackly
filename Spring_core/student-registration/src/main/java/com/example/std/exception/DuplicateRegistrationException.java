package com.example.std.exception;

public class DuplicateRegistrationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateRegistrationException(String message) {
        super(message);
    }
}
