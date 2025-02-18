package com.example.Mini_Bookstore.service.impl;

import com.example.Mini_Bookstore.entity.BookInventory;
import com.example.Mini_Bookstore.entity.Category;
import com.example.Mini_Bookstore.repository.BookInventoryRepository;
import com.example.Mini_Bookstore.repository.BookRepository;
import com.example.Mini_Bookstore.service.BookInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookInventoryServiceImpl implements BookInventoryService {

    private final BookInventoryRepository bookInventoryRepository;
    private final BookRepository bookRepository;

    @Override
    public BookInventory addBookInventory(BookInventory bookInventory) {
        return bookInventoryRepository.save(bookInventory);
    }

    @Override
    public Optional<BookInventory> getBookInventoryById(String id) {
        return bookInventoryRepository.findById(id);
    }

    @Override
    public List<BookInventory> getAllBookInventories() {
        return bookInventoryRepository.findAll();
    }

    @Override
    public List<BookInventory> getBookInventoriesByBookId(String bookId) {
        return bookInventoryRepository.findByBookId(bookId);
    }

    @Override
    public List<BookInventory> getBookInventoriesByStoreId(String bookStoreId) {
        return bookInventoryRepository.findByBookStoreId(bookStoreId);
    }

    @Override
    public Optional<BookInventory> updateBookInventory(String id, BookInventory bookInventory) {
        return bookInventoryRepository.findById(id).map(existingInventory -> {
            existingInventory.setBookId(bookInventory.getBookId());
            existingInventory.setBookStoreId(bookInventory.getBookStoreId());
            existingInventory.setPrice(bookInventory.getPrice());
            existingInventory.setTotalCount(bookInventory.getTotalCount());
            existingInventory.setSoldCount(bookInventory.getSoldCount());
            return bookInventoryRepository.save(existingInventory);
        });
    }

    @Override
    public void deleteBookInventory(String id) {
        bookInventoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<BookInventory> sellOneBook(String bookId, String bookStoreId) {
        return bookInventoryRepository.findByBookIdAndBookStoreId(bookId, bookStoreId).filter(inventory -> inventory.getTotalCount() > 0).map(inventory -> {
            inventory.setTotalCount(inventory.getTotalCount() - 1);
            inventory.setSoldCount(inventory.getSoldCount() + 1);
            return bookInventoryRepository.save(inventory);
        });
    }

    @Override
    @Transactional
    public boolean sellMultipleBooks(String bookId, String bookStoreId, int quantity) {
        return bookInventoryRepository.findByBookIdAndBookStoreId(bookId, bookStoreId).filter(inventory -> inventory.getTotalCount() >= quantity).map(inventory -> {
            inventory.setTotalCount(inventory.getTotalCount() - quantity);
            inventory.setSoldCount(inventory.getSoldCount() + quantity);
            bookInventoryRepository.save(inventory);
            return true;
        }).orElse(false);
    }

    @Override
    public long getSoldBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author).stream().mapToLong(book -> bookInventoryRepository.findByBookId(book.getId()).stream().mapToLong(BookInventory::getSoldCount).sum()).sum();
    }

    @Override
    public long getSoldBooksByCategory(String category) {
        return bookRepository.findByCategory(Category.valueOf(category.toUpperCase())).stream().mapToLong(book -> bookInventoryRepository.findByBookId(book.getId()).stream().mapToLong(BookInventory::getSoldCount).sum()).sum();
    }
}