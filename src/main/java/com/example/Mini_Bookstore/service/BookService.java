package com.example.Mini_Bookstore.service;


import com.example.Mini_Bookstore.entity.Book;
import com.example.Mini_Bookstore.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    Book addBook(Book book);

    Optional<Book> getBookById(String id);

    List<Book> getAllBooks();

    int getBookCount(String bookId);

    Book updateBook(Book updatedBook);

    boolean sellOneBook(String bookId, String bookStoreId);

    boolean sellMultipleBooks(String bookId, String bookStoreId, int quantity);

    int getSoldBooksByAuthor(String author);

    int getSoldBooksByCategory(Category category);

    List<Book> searchBooks(Category category, String keyword);
}