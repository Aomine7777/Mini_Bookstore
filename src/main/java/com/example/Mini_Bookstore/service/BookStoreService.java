package com.example.Mini_Bookstore.service;


import com.example.Mini_Bookstore.entity.Book;
import com.example.Mini_Bookstore.entity.BookStore;

import java.util.List;
import java.util.Optional;

public interface BookStoreService  {
    BookStore addBookStore(BookStore bookStore);
    Optional<BookStore> getBookStoreByID(String id) ;
    public BookStore updateBookStore(String id, BookStore updatedBookStore);
    public Optional<BookStore> deleteBookStoreById(String id);
    public List<BookStore> getAllBookStores();
}
