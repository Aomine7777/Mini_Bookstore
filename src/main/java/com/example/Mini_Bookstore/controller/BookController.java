package com.example.Mini_Bookstore.controller;

import com.example.Mini_Bookstore.dto.BookDTO;
import com.example.Mini_Bookstore.entity.Book;
import com.example.Mini_Bookstore.entity.Category;
import com.example.Mini_Bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        Book savedBook = bookService.addBook(bookDTO).toEntity();
        return ResponseEntity.ok(new BookDTO(savedBook));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable String id) {
        BookDTO book = bookService.getBookById(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}/count")
    public ResponseEntity<Integer> getBookCount(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getBookCount(id));
    }

    @PutMapping
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(bookDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam(required = false) Category category, @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(bookService.searchBooks(category, keyword));
    }
}