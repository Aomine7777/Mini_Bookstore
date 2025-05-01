package com.example.Mini_Bookstore.dto.request;

public record SellBookRequest(String bookId, String bookStoreId, int quantity) {
}