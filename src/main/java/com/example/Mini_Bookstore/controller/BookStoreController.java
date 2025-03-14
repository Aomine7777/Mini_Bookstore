package com.example.Mini_Bookstore.controller;

import com.example.Mini_Bookstore.dto.BookStoreDTO;
import com.example.Mini_Bookstore.service.BookStoreService;
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
    public ResponseEntity<BookStoreDTO> addBookStore(@RequestBody BookStoreDTO bookStoreDTO) {
        BookStoreDTO createdBookStore = bookStoreService.addBookStore(bookStoreDTO);
        return ResponseEntity.ok(createdBookStore);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookStoreDTO> getBookStoreById(@PathVariable String id) {
        BookStoreDTO bookStore = bookStoreService.getBookStoreByID(id);
        return ResponseEntity.ok(bookStore);
    }

    @GetMapping
    public ResponseEntity<List<BookStoreDTO>> getAllBookStores() {
        List<BookStoreDTO> bookStores = bookStoreService.getAllBookStores();
        return ResponseEntity.ok(bookStores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookStoreDTO> updateBookStore(@PathVariable String id, @RequestBody BookStoreDTO bookStoreDTO) {
        BookStoreDTO updatedBookStore = bookStoreService.updateBookStore(id, bookStoreDTO);
        return ResponseEntity.ok(updatedBookStore);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookStoreDTO> deleteBookStore(@PathVariable String id) {
        BookStoreDTO deletedBookStore = bookStoreService.deleteBookStoreById(id);
        return ResponseEntity.ok(deletedBookStore);
    }
}