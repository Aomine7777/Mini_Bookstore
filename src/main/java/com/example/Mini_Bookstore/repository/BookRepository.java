package com.example.Mini_Bookstore.repository;

import com.example.Mini_Bookstore.entity.Book;
import com.example.Mini_Bookstore.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByCategory(Category category);

    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);

    List<Book> findByCategoryAndTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(Category category, String title, String author);

    List<Book> findByAuthor(String author);
}