package com.example.Mini_Bookstore.controller;

import com.example.Mini_Bookstore.entity.BookStore;
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
    public ResponseEntity<BookStore> addBookStore(@RequestBody BookStore bookStore) {
        return ResponseEntity.ok(bookStoreService.addBookStore(bookStore));
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookStore> getBookStoreById(@PathVariable String id) {
        return  bookStoreService.getBookStoreByID(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<BookStore>> getAllBookStore() {
        return ResponseEntity.ok(bookStoreService.getAllBookStores());
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookStore> updateBookStore(@PathVariable String id, @RequestBody BookStore bookStore) {
        try{
            return ResponseEntity.ok(bookStoreService.updateBookStore(id, bookStore));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BookStore> deleteBookStore(@PathVariable String id) {
        try{
            bookStoreService.deleteBookStoreById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}
