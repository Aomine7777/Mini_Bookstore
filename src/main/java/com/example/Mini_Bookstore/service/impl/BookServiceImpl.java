package com.example.Mini_Bookstore.service.impl;


import com.example.Mini_Bookstore.entity.Book;
import com.example.Mini_Bookstore.entity.BookInventory;
import com.example.Mini_Bookstore.entity.Category;
import com.example.Mini_Bookstore.repository.BookInventoryRepository;
import com.example.Mini_Bookstore.repository.BookRepository;
import com.example.Mini_Bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookInventoryRepository bookInventoryRepository;

    @Override
    public Book AddBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public int getBooksAvailableCount(String id) {
        List<BookInventory> inventories = bookInventoryRepository.findByBookId(id);
        return inventories.stream()
                .mapToInt(inventory -> inventory.getTotalCount() - inventory.getSoldCount())
                .sum();
    }

    @Override
    public Book updateBook(String id, Book book) {
        Book existingBook = getBookById(id);
        book.setId(existingBook.getId());
        return bookRepository.save(book);
    }

    @Override
    public Book sellOneBook(String storeId, String bookId) {
        List<BookInventory> inventories = bookInventoryRepository.findByBookIdAndBookStoreId(storeId, bookId);
        if (inventories.isEmpty()) {
            throw new RuntimeException("No inventory record found for the given store and book");
        }
        BookInventory inventory = inventories.get(0);
        if (inventory.getTotalCount() - inventory.getSoldCount() <= 0) {
            throw new RuntimeException("Book is out of stock");
        }
        inventory.setSoldCount(inventory.getSoldCount() + 1);
        bookInventoryRepository.save(inventory);
        return getBookById(bookId);
    }
    @Override
    public Book sellBooks(String storeId, String bookId, int quantity) {
        List<BookInventory> inventories = bookInventoryRepository.findByBookIdAndBookStoreId(storeId, bookId);
        if (inventories.isEmpty()) {
            throw new RuntimeException("No inventory record found for the given store and book");
        }
        BookInventory inventory = inventories.get(0);
        if (inventory.getTotalCount() - inventory.getSoldCount() < quantity) {
            throw new RuntimeException("Not enough stock to sell");
        }
        inventory.setSoldCount(inventory.getSoldCount() + quantity);
        bookInventoryRepository.save(inventory);
        return getBookById(bookId);
    }

    @Override
    public int getSoldBooksByAuthor(String author) {
        List<Book> booksByAuthor = bookRepository.findByAuthorContainingIgnoreCase(author);
        return booksByAuthor.stream()
                .mapToInt(book -> bookInventoryRepository.findByBookId(book.getId())
                        .stream()
                        .mapToInt(BookInventory::getSoldCount)
                        .sum())
                .sum();
    }

    @Override
    public int getSoldBooksByCategory(Category category) {
        List<Book> booksByCategory = bookRepository.findByCategory(category);
        return booksByCategory.stream()
                .mapToInt(book -> bookInventoryRepository.findByBookId(book.getId())
                        .stream()
                        .mapToInt(BookInventory::getSoldCount)
                        .sum())
                .sum();
    }
    @Override
    public List<Book> searchBooks(String keyword, Category category) {
        if (keyword != null && !keyword.isBlank()) {
            // Поиск по названию или автору (игнорируя регистр)
            return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
        }
        if (category != null) {
            // Поиск по категории
            return bookRepository.findByCategory(category);
        }
        throw new IllegalArgumentException("Either keyword or category must be provided");
    }
}