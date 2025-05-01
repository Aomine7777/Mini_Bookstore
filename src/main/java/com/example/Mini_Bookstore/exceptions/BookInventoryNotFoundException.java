package com.example.Mini_Bookstore.exceptions;

public class BookInventoryNotFoundException extends RuntimeException {
    public BookInventoryNotFoundException(String message) {
        super(message);
    }
}