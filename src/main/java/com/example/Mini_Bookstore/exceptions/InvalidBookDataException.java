package com.example.Mini_Bookstore.exceptions;

public class InvalidBookDataException extends RuntimeException {
  public InvalidBookDataException(String message) {

    super(message);
  }
}