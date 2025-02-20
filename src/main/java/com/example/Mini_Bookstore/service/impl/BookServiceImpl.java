package com.example.Mini_Bookstore.service.impl;


import com.example.Mini_Bookstore.entity.Book;
import com.example.Mini_Bookstore.entity.BookInventory;
import com.example.Mini_Bookstore.entity.Category;
import com.example.Mini_Bookstore.repository.BookInventoryRepository;
import com.example.Mini_Bookstore.repository.BookRepository;
import com.example.Mini_Bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookInventoryRepository bookInventoryRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    public int getBookCount(String bookId) {
        return bookInventoryRepository.findByBookId(bookId).stream().mapToInt(BookInventory::getTotalCount).sum();
    }


    public Book updateBook(Book updatedBook) {
        return bookRepository.save(updatedBook);
    }


    @Transactional
    public boolean sellOneBook(String bookId, String bookStoreId) {
        Optional<BookInventory> inventoryOpt = bookInventoryRepository.findByBookIdAndBookStoreId(bookId, bookStoreId);
        if (inventoryOpt.isPresent()) {
            BookInventory inventory = inventoryOpt.get();
            if (inventory.getTotalCount() > 0) {
                inventory.setTotalCount(inventory.getTotalCount() - 1);
                inventory.setSoldCount(inventory.getSoldCount() + 1);
                bookInventoryRepository.save(inventory);
                return true;
            }
        }
        return false;
    }


    @Transactional
    public boolean sellMultipleBooks(String bookId, String bookStoreId, int quantity) {
        Optional<BookInventory> inventoryOpt = bookInventoryRepository.findByBookIdAndBookStoreId(bookId, bookStoreId);
        if (inventoryOpt.isPresent()) {
            BookInventory inventory = inventoryOpt.get();
            if (inventory.getTotalCount() >= quantity) {
                inventory.setTotalCount(inventory.getTotalCount() - quantity);
                inventory.setSoldCount(inventory.getSoldCount() + quantity);
                bookInventoryRepository.save(inventory);
                return true;
            }
        }
        return false;
    }


    public List<Book> searchBooks(Category category, String keyword) {
        if (category != null) {
            return bookRepository.findByCategory(category);
        }
        if (keyword != null && !keyword.isEmpty()) {
            return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
        }
        return bookRepository.findAll();
    }
}