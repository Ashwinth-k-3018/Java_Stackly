package com.example.book.exception;

public class BookAlreadyAvailableException extends RuntimeException {
	

    private static final long serialVersionUID = 1L;

    public BookAlreadyAvailableException(String message) {
        super(message);
    }
}
