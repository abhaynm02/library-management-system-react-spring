package com.abhaynm.library_management.exceptions.coustomexceptions;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(String message) {
        super(message);
    }
}
