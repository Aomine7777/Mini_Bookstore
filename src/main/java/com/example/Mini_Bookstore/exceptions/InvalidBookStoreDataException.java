package com.example.Mini_Bookstore.exceptions;

public class InvalidBookStoreDataException extends RuntimeException {
    public InvalidBookStoreDataException(String message) {
        super(message);
    }
}