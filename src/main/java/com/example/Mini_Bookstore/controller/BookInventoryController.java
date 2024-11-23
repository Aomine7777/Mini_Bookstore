package com.example.Mini_Bookstore.controller;

import com.example.Mini_Bookstore.entity.BookInventory;
import com.example.Mini_Bookstore.service.BookInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class BookInventoryController {

    private final BookInventoryService bookInventoryService;

    @PostMapping
    public BookInventory addInventory(@RequestBody BookInventory inventory) {
        return bookInventoryService.addInventory(inventory);
    }

    @GetMapping
    public List<BookInventory> getAllInventory() {
        return bookInventoryService.getAllInventories();
    }
    @GetMapping("/book/{bookId}")
    public List<BookInventory> getInventoriesByBookId(@PathVariable String bookId) {
        return bookInventoryService.getInventoriesByBookId(bookId);
    }
    @PutMapping("/store/{storeId}")
    public List<BookInventory> getInventoriesByStoreId(@PathVariable String storeId) {
        return bookInventoryService.getInventoriesByStoreId(storeId);
    }
    @PostMapping("/sell/{storeId}/{bookId}/{quantity}")
    public BookInventory sellBook(@PathVariable String storeId,
                                  @PathVariable String bookId,
                                  @PathVariable int quantity) {
        return bookInventoryService.sellBook(storeId, bookId, quantity);
    }
}
