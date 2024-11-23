package com.example.Mini_Bookstore.repository;

import com.example.Mini_Bookstore.entity.BookInventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookInventoryRepository extends MongoRepository<BookInventory, String> {
    List<BookInventory> findByBookId(String bookId);
    List<BookInventory> findByBookStoreId(String storeId);
    List<BookInventory> findByBookIdAndBookStoreId(String bookId, String storeId);
}
