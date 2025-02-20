package com.example.Mini_Bookstore.service;


import com.example.Mini_Bookstore.entity.BookStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookStoreService {
    BookStore addBookStore(BookStore bookStore);

    Optional<BookStore> getBookStoreByID(String id);

    BookStore updateBookStore(String id, BookStore updatedBookStore);

    Optional<BookStore> deleteBookStoreById(String id);

    List<BookStore> getAllBookStores();
}