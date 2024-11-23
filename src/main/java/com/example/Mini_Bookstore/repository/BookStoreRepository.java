package com.example.Mini_Bookstore.repository;

import com.example.Mini_Bookstore.entity.BookStore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStoreRepository extends MongoRepository<BookStore, String> {
}
