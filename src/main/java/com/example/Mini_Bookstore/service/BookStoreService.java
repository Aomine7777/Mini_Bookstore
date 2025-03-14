package com.example.Mini_Bookstore.service;


import com.example.Mini_Bookstore.dto.BookStoreDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookStoreService {
    BookStoreDTO addBookStore(BookStoreDTO bookStoreDTO);

    BookStoreDTO getBookStoreByID(String id);

    BookStoreDTO updateBookStore(String id, BookStoreDTO updatedBookStoreDTO);

    BookStoreDTO deleteBookStoreById(String id);

    List<BookStoreDTO> getAllBookStores();
}