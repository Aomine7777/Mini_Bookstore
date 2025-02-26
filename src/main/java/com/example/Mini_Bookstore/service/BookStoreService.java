package com.example.Mini_Bookstore.service;


import com.example.Mini_Bookstore.dto.BookStoreDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookStoreService {
    BookStoreDTO addBookStore(BookStoreDTO bookStoreDTO);

    Optional<BookStoreDTO> getBookStoreByID(String id);

    BookStoreDTO updateBookStore(String id, BookStoreDTO updatedBookStoreDTO);

    Optional<BookStoreDTO> deleteBookStoreById(String id);

    List<BookStoreDTO> getAllBookStores();
}