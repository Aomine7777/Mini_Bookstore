package com.example.Mini_Bookstore.controller;

import com.example.Mini_Bookstore.dto.BookStoreDTO;
import com.example.Mini_Bookstore.service.BookStoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/bookstore")
@RequiredArgsConstructor
public class BookStoreController {

    private final BookStoreService bookStoreService;

    @PostMapping
    public ResponseEntity<BookStoreDTO> addBookStore(@Valid @RequestBody BookStoreDTO bookStoreDTO) {
        return ResponseEntity.ok(bookStoreService.addBookStore(bookStoreDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookStoreDTO> getBookStoreById(@PathVariable String id) {
        return ResponseEntity.ok(bookStoreService.getBookStoreByID(id));
    }

    @GetMapping
    public ResponseEntity<List<BookStoreDTO>> getAllBookStores() {
        List<BookStoreDTO> bookStores = bookStoreService.getAllBookStores();
        return ResponseEntity.ok(bookStores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookStoreDTO> updateBookStore(@PathVariable String id, @Valid @RequestBody BookStoreDTO bookStoreDTO) {
        return ResponseEntity.ok(bookStoreService.updateBookStore(id, bookStoreDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookStoreDTO> deleteBookStore(@PathVariable String id) {
        return ResponseEntity.ok(bookStoreService.deleteBookStoreById(id));
    }
}