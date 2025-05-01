package com.example.Mini_Bookstore.exceptions;

public class BookStoreNotFoundException extends RuntimeException {
    public BookStoreNotFoundException(String message) {
        super(message);
    }
}