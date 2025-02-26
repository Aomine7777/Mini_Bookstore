package com.example.Mini_Bookstore.dto;

import com.example.Mini_Bookstore.entity.Book;
import com.example.Mini_Bookstore.entity.Category;


public record BookDTO(
         String id,
         String title,
         String author,
         Category category,
         String addedAt,
         String publicationDate
) {
    public BookDTO(Book book) {
        this(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.getAddedAt(),
                book.getPublicationDate()
        );
    }
    public static BookDTO fromEntity(Book book) {
        return new BookDTO(book);
    }
    public Book toEntity() {
        return new Book(id, title, author, category, addedAt, publicationDate);
    }
}