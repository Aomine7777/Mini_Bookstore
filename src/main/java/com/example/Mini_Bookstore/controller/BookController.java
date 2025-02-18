package com.example.Mini_Bookstore.controller;

import com.example.Mini_Bookstore.entity.Book;
import com.example.Mini_Bookstore.entity.Category;
import com.example.Mini_Bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}/count")
    public ResponseEntity<Integer> getBookCount(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getBookCount(id));
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(book));
    }

    @PostMapping("/{bookId}/sell/{bookStoreId}")
    public ResponseEntity<String> sellOneBook(@PathVariable String bookId, @PathVariable String bookStoreId) {
        boolean success = bookService.sellOneBook(bookId, bookStoreId);
        return success ? ResponseEntity.ok("Book sold") : ResponseEntity.badRequest().body("Not enough stock");
    }

    @PostMapping("/{bookId}/sell/{bookStoreId}/{quantity}")
    public ResponseEntity<String> sellMultipleBooks(@PathVariable String bookId, @PathVariable String bookStoreId, @PathVariable int quantity) {
        boolean success = bookService.sellMultipleBooks(bookId, bookStoreId, quantity);
        return success ? ResponseEntity.ok("Books sold") : ResponseEntity.badRequest().body("Not enough stock");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) Category category, @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(bookService.searchBooks(category, keyword));
    }
}