package com.example.Mini_Bookstore.service;


import com.example.Mini_Bookstore.entity.Book;
import com.example.Mini_Bookstore.entity.Category;

import java.util.List;

public interface BookService {
    Book AddBook(Book book);
    Book getBookById(String id);
    List<Book> getAllBooks();
    int getBooksAvailableCount(String id);
    Book updateBook(String id, Book book);
    Book sellOneBook(String storeId, String bookId);
    Book sellBooks (String storeId, String bookId,int quantity);
    int getSoldBooksByAuthor(String author);
    int getSoldBooksByCategory(Category category);
    List<Book> searchBooks(String keyword, Category category);
}
