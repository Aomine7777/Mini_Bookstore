package com.example.Mini_Bookstore.service;

import com.example.Mini_Bookstore.dto.BookInventoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookInventoryService {
    BookInventoryDTO addBookInventory(BookInventoryDTO bookInventoryDTO);

    BookInventoryDTO getBookInventoryById(String id);

    List<BookInventoryDTO> getAllBookInventories();

    List<BookInventoryDTO> getBookInventoriesByBookId(String bookId);

    List<BookInventoryDTO> getBookInventoriesByStoreId(String bookStoreId);

    BookInventoryDTO updateBookInventory(String id, BookInventoryDTO bookInventoryDTO);

    void deleteBookInventory(String id);

    boolean sellBooks(String bookId, String bookStoreId, int quantity);

    long getSoldBooksByAuthor(String author);

    long getSoldBooksByCategory(String category);
}