package com.example.Mini_Bookstore.controller;

import com.example.Mini_Bookstore.dto.BookInventoryDTO;
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
    public ResponseEntity<BookInventoryDTO> addBookInventory(@RequestBody BookInventoryDTO bookInventoryDTO) {
        return ResponseEntity.ok(bookInventoryService.addBookInventory(bookInventoryDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookInventoryDTO> getBookInventoryById(@PathVariable String id) {
        return bookInventoryService.getBookInventoryById(id)
             .map(ResponseEntity::ok)
             .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BookInventoryDTO>> getAllBookInventories() {
        return ResponseEntity.ok(bookInventoryService.getAllBookInventories());
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BookInventoryDTO>> getInventoriesByBookId(@PathVariable String bookId) {
        return ResponseEntity.ok(bookInventoryService.getBookInventoriesByBookId(bookId));
    }

    @GetMapping("/store/{bookStoreId}")
    public ResponseEntity<List<BookInventoryDTO>> getInventoriesByStoreId(@PathVariable String bookStoreId) {
        return ResponseEntity.ok(bookInventoryService.getBookInventoriesByStoreId(bookStoreId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookInventoryDTO> updateBookInventory(@PathVariable String id, @RequestBody BookInventoryDTO bookInventoryDTO) {
        return bookInventoryService.updateBookInventory(id, bookInventoryDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookInventory(@PathVariable String id) {
        bookInventoryService.deleteBookInventory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sell-one")
    public ResponseEntity<String> sellOneBook(@RequestParam String bookId, @RequestParam String bookStoreId) {
        boolean success = bookInventoryService.sellMultipleBooks(bookId, bookStoreId, 1);
        if (success) {
            return ResponseEntity.ok("Successfully sold " + 1 + " books.");
        } else {
            return ResponseEntity.badRequest().body("Not enough stock available.");
        }
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