package com.example.Mini_Bookstore.repository;

import com.example.Mini_Bookstore.entity.BookInventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookInventoryRepository extends MongoRepository<BookInventory, String> {
    Optional<BookInventory> findByBookIdAndBookStoreId(String bookId, String bookStoreId);

    List<BookInventory> findByBookStoreId(String bookStoreId);

    List<BookInventory> findByBookId(String bookId);
}