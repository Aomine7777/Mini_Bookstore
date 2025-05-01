package com.example.Mini_Bookstore.dto;

import com.example.Mini_Bookstore.entity.Book;
import com.example.Mini_Bookstore.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record BookDTO(
         String id,

         @NotNull(message = "Title cannot be null")
         @NotEmpty(message = "Title cannot be empty")
         @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
         String title,

         @NotNull(message = "Author cannot be null")
         @NotEmpty(message = "Author cannot be empty")
         @Size(min = 2, max = 30, message = "Author must be between 2 and 30 characters")
         String author,

         @NotNull(message = "Category cannot be null")
         Category category,

         @NotBlank(message = "Added date cannot be blank")
         String addedAt,

         @NotBlank(message = "Publication date cannot be blank")
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

    public Book toEntity() {
        return new Book(id, title, author, category, addedAt, publicationDate);
    }
}