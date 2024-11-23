package com.example.Mini_Bookstore.service;

import com.example.Mini_Bookstore.entity.BookInventory;

import java.util.List;

public interface BookInventoryService {
    BookInventory addInventory(BookInventory inventory);
    List<BookInventory> getAllInventories();
    List<BookInventory> getInventoriesByBookId(String bookId);
    List<BookInventory> getInventoriesByStoreId(String storeId);
    BookInventory sellBook(String storeId, String bookId, int quantity);
    BookInventory buyBook(String storeId, String bookId, int quantity);
}
