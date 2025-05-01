package com.example.Mini_Bookstore.exceptions;

public class InvalidBookInventoryDataException extends RuntimeException {
    public InvalidBookInventoryDataException(String message) {
        super(message);
    }
}