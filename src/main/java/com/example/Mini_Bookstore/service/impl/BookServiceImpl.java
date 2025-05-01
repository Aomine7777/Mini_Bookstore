package com.example.Mini_Bookstore.service.impl;


import com.example.Mini_Bookstore.dto.BookDTO;
import com.example.Mini_Bookstore.entity.Book;
import com.example.Mini_Bookstore.entity.BookInventory;
import com.example.Mini_Bookstore.entity.Category;
import com.example.Mini_Bookstore.exceptions.BookNotFoundException;
import com.example.Mini_Bookstore.exceptions.InvalidBookDataException;
import com.example.Mini_Bookstore.repository.BookInventoryRepository;
import com.example.Mini_Bookstore.repository.BookRepository;
import com.example.Mini_Bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookInventoryRepository bookInventoryRepository;

    public BookDTO addBook(BookDTO bookDTO) {
        Book book = bookDTO.toEntity();
        return new BookDTO(bookRepository.save(book));
    }

    public BookDTO getBookById(String id) {
        return bookRepository.findById(id).map(BookDTO::new)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
    }


    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
             .map(BookDTO::new)
             .collect(Collectors.toList());
    }


    public int getBookCount(String bookId) {
        return bookInventoryRepository.findByBookId(bookId).stream()
             .mapToInt(BookInventory::getTotalCount)
             .sum();
    }

    public BookDTO updateBook(BookDTO bookDTO) {
        Book updatedBook = bookDTO.toEntity();
        return new BookDTO(bookRepository.save(updatedBook));
    }

    public List<BookDTO> searchBooks(Category category, String keyword) {
        List<Book> books = new ArrayList<>();
        if (category != null && keyword != null && !keyword.isEmpty()) {
            books.addAll(bookRepository.findByCategoryAndTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(category, keyword, keyword));
        } else if (category != null) {
            books.addAll(bookRepository.findByCategory(category));
        } else if (keyword != null && !keyword.isEmpty()) {
            books.addAll(bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword));
        } else {
            books.addAll(bookRepository.findAll());
        }

        return books.stream()
            .map(BookDTO::new)
            .toList();
    }
}