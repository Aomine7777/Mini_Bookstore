package com.example.Mini_Bookstore.service;


import com.example.Mini_Bookstore.dto.BookDTO;
import com.example.Mini_Bookstore.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    BookDTO addBook(BookDTO bookDTO);

    BookDTO getBookById(String id);

    List<BookDTO> getAllBooks();

    int getBookCount(String bookId);

    BookDTO  updateBook(BookDTO updatedBook);

    List<BookDTO> searchBooks(Category category, String keyword);
}