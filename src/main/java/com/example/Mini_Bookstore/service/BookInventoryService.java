package com.example.Mini_Bookstore.service;

import com.example.Mini_Bookstore.dto.BookInventoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookInventoryService {
    BookInventoryDTO addBookInventory(BookInventoryDTO bookInventoryDTO);

    Optional<BookInventoryDTO> getBookInventoryById(String id);

    List<BookInventoryDTO> getAllBookInventories();

    List<BookInventoryDTO> getBookInventoriesByBookId(String bookId);

    List<BookInventoryDTO> getBookInventoriesByStoreId(String bookStoreId);

    Optional<BookInventoryDTO> updateBookInventory(String id, BookInventoryDTO bookInventoryDTO);

    void deleteBookInventory(String id);

    boolean sellMultipleBooks(String bookId, String bookStoreId, int quantity);

    long getSoldBooksByAuthor(String author);

    long getSoldBooksByCategory(String category);
}