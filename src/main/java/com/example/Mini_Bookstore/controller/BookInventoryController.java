package com.example.Mini_Bookstore.controller;

import com.example.Mini_Bookstore.entity.BookInventory;
import com.example.Mini_Bookstore.service.BookInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-inventory")
@RequiredArgsConstructor
public class BookInventoryController {

    private final BookInventoryService bookInventoryService;

    @PostMapping
    public ResponseEntity<BookInventory> addBookInventory(@RequestBody BookInventory bookInventory) {
        return ResponseEntity.ok(bookInventoryService.addBookInventory(bookInventory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookInventory> getBookInventoryById(@PathVariable String id) {
        return bookInventoryService.getBookInventoryById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BookInventory>> getAllBookInventories() {
        return ResponseEntity.ok(bookInventoryService.getAllBookInventories());
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BookInventory>> getInventoriesByBookId(@PathVariable String bookId) {
        return ResponseEntity.ok(bookInventoryService.getBookInventoriesByBookId(bookId));
    }

    @GetMapping("/store/{bookStoreId}")
    public ResponseEntity<List<BookInventory>> getInventoriesByStoreId(@PathVariable String bookStoreId) {
        return ResponseEntity.ok(bookInventoryService.getBookInventoriesByStoreId(bookStoreId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookInventory> updateBookInventory(@PathVariable String id, @RequestBody BookInventory bookInventory) {
        return bookInventoryService.updateBookInventory(id, bookInventory).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookInventory(@PathVariable String id) {
        bookInventoryService.deleteBookInventory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sell-one")
    public ResponseEntity<BookInventory> sellOneBook(@RequestParam String bookId, @RequestParam String bookStoreId) {
        return bookInventoryService.sellOneBook(bookId, bookStoreId).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/sell-multiple")
    public ResponseEntity<String> sellMultipleBooks(@RequestParam String bookId, @RequestParam String bookStoreId, @RequestParam int quantity) {
        boolean success = bookInventoryService.sellMultipleBooks(bookId, bookStoreId, quantity);
        if (success) {
            return ResponseEntity.ok("Successfully sold " + quantity + " books.");
        } else {
            return ResponseEntity.badRequest().body("Not enough stock available.");
        }
    }

    @GetMapping("/sold-by-author/{author}")
    public ResponseEntity<Long> getSoldBooksByAuthor(@PathVariable String author) {
        long soldCount = bookInventoryService.getSoldBooksByAuthor(author);
        return ResponseEntity.ok(soldCount);
    }

    @GetMapping("/sold-by-category/{category}")
    public ResponseEntity<Long> getSoldBooksByCategory(@PathVariable String category) {
        long soldCount = bookInventoryService.getSoldBooksByCategory(category);
        return ResponseEntity.ok(soldCount);
    }
}