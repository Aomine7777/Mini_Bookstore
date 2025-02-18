package com.example.Mini_Bookstore.service;

import com.example.Mini_Bookstore.entity.BookInventory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookInventoryService {
    BookInventory addBookInventory(BookInventory bookInventory);

    Optional<BookInventory> getBookInventoryById(String id);

    List<BookInventory> getAllBookInventories();

    List<BookInventory> getBookInventoriesByBookId(String bookId);

    List<BookInventory> getBookInventoriesByStoreId(String bookStoreId);

    Optional<BookInventory> updateBookInventory(String id, BookInventory bookInventory);

    void deleteBookInventory(String id);

    Optional<BookInventory> sellOneBook(String bookId, String bookStoreId);

    boolean sellMultipleBooks(String bookId, String bookStoreId, int quantity);

    long getSoldBooksByAuthor(String author);

    long getSoldBooksByCategory(String category);
}