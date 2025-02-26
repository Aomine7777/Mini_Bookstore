package com.example.Mini_Bookstore.service.impl;


import com.example.Mini_Bookstore.dto.BookDTO;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookInventoryRepository bookInventoryRepository;

    public BookDTO addBook(BookDTO bookDTO) {
        Book book = bookDTO.toEntity();
        return BookDTO.fromEntity(bookRepository.save(book));
    }

    public Optional<BookDTO> getBookById(String id) {
        return bookRepository.findById(id).map(BookDTO::new);
    }


    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
             .map(BookDTO::fromEntity)
             .collect(Collectors.toList());
    }


    public int getBookCount(String bookId) {
        return bookInventoryRepository.findByBookId(bookId).stream()
             .mapToInt(BookInventory::getTotalCount)
             .sum();
    }


    public BookDTO updateBook(BookDTO bookDTO) {
        Book updatedBook = bookDTO.toEntity();
        return BookDTO.fromEntity(bookRepository.save(updatedBook));
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


    public List<BookDTO> searchBooks(Category category, String keyword) {
        List<Book> books;
        if (category != null && keyword != null && !keyword.isEmpty()) {
            books = bookRepository.findByCategoryAndTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(category, keyword, keyword);
        } else if (category != null) {
            books = bookRepository.findByCategory(category);
        } else if (keyword != null && !keyword.isEmpty()) {
            books = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
        } else {
            books = bookRepository.findAll();
        }
        return books.stream().map(BookDTO::new).toList();
    }
}