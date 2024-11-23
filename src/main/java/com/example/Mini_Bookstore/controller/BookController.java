package com.example.Mini_Bookstore.controller;

import com.example.Mini_Bookstore.entity.Book;
import com.example.Mini_Bookstore.entity.Category;
import com.example.Mini_Bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.AddBook(book);
    }
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable String id) {
        return bookService.getBookById(id);
    }
    @GetMapping
    public List<Book> getAllBooks() {
     return bookService.getAllBooks();
    }
    @GetMapping("/{id}/available")
    public int getBookAvailableCount( @PathVariable String id) {
        return bookService.getBooksAvailableCount(id);
    }

    @PostMapping("/{storeId}/{bookId}/sell")
    public void sellBooks(@PathVariable String storeId, @PathVariable String bookId, @RequestParam int quantity) {
        bookService.sellBooks(storeId, bookId, quantity);
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(required = false) String keyword,
                                  @RequestParam(required = false) Category category) {
        return bookService.searchBooks(keyword, category);
    }

    @GetMapping("/sold/author")
    public int getSoldBooksByAuthor(@RequestParam String author) {
        return bookService.getSoldBooksByAuthor(author);
    }

    @GetMapping("/sold/category")
    public int getSoldBooksByCategory(@RequestParam Category category) {
        return bookService.getSoldBooksByCategory(category);
    }
}
