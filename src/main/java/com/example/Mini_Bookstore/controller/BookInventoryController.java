package com.example.Mini_Bookstore.controller;

import com.example.Mini_Bookstore.dto.BookInventoryDTO;
import com.example.Mini_Bookstore.dto.request.SellBookRequest;
import com.example.Mini_Bookstore.service.BookInventoryService;
import jakarta.validation.Valid;
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
    public ResponseEntity<BookInventoryDTO> addBookInventory(@Valid @RequestBody BookInventoryDTO bookInventoryDTO) {
        return ResponseEntity.ok(bookInventoryService.addBookInventory(bookInventoryDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookInventoryDTO> getBookInventoryById(@PathVariable String id) {
        return ResponseEntity.ok(bookInventoryService.getBookInventoryById(id));
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
    public ResponseEntity<BookInventoryDTO> updateBookInventory(@PathVariable String id,@Valid @RequestBody BookInventoryDTO bookInventoryDTO) {
        return ResponseEntity.ok(bookInventoryService.updateBookInventory(id, bookInventoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookInventory(@PathVariable String id) {
        bookInventoryService.deleteBookInventory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellBooks(@Valid @RequestBody SellBookRequest request) {
        boolean success = bookInventoryService.sellBooks(request.bookId(), request.bookStoreId(), request.quantity());
        if (success) {
            return ResponseEntity.ok("Successfully sold " + request.quantity() + " books.");
        } else {
            return ResponseEntity.badRequest().body("Not enough stock available.");
        }
    }

    @GetMapping("/sold-by-author/{author}")
    public ResponseEntity<Long> getSoldBooksByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(bookInventoryService.getSoldBooksByAuthor(author));
    }

    @GetMapping("/sold-by-category/{category}")
    public ResponseEntity<Long> getSoldBooksByCategory(@PathVariable String category) {
        return ResponseEntity.ok(bookInventoryService.getSoldBooksByCategory(category));
    }
}