package com.example.Mini_Bookstore.service.impl;

import com.example.Mini_Bookstore.entity.BookInventory;
import com.example.Mini_Bookstore.repository.BookInventoryRepository;
import com.example.Mini_Bookstore.service.BookInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookInventoryServiceImpl implements BookInventoryService {

    @Autowired
    private BookInventoryRepository inventoryRepository;

    public BookInventory addInventory(BookInventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public List<BookInventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public List<BookInventory> getInventoriesByBookId(String bookId) {
        return inventoryRepository.findByBookId(bookId);
    }

    public List<BookInventory> getInventoriesByStoreId(String storeId) {
        return inventoryRepository.findByBookStoreId(storeId);
    }

    public BookInventory sellBook(String storeId, String bookId, int quantity) {
        List<BookInventory> inventories = inventoryRepository.findByBookIdAndBookStoreId(storeId, bookId);
        if (inventories.isEmpty()) {
            throw new RuntimeException("No inventory record found for the given store and book");
        }
        BookInventory inventory = inventories.get(0);
        if (inventory.getTotalCount() - inventory.getSoldCount() < quantity) {
            throw new RuntimeException("Not enough stock to sell");
        }
        inventory.setSoldCount(inventory.getSoldCount() + quantity);
        return inventoryRepository.save(inventory);
    }

    @Override
    public BookInventory buyBook(String storeId, String bookId, int quantity) {
        return null;
    }


}
