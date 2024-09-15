package com.abhaynm.library_management.exceptions.coustomexceptions;

public class BookAddingFailedException extends RuntimeException{
    public BookAddingFailedException(String message) {
        super(message);
    }
}
