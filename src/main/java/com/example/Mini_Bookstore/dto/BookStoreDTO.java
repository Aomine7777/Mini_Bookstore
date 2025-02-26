package com.example.Mini_Bookstore.dto;

import com.example.Mini_Bookstore.entity.BookInventory;
import com.example.Mini_Bookstore.entity.BookStore;

import java.util.List;

public record BookStoreDTO(
        String id,
        String name,
        String location,
        List<BookInventory>bookInventories
) {
    public BookStoreDTO (BookStore bookStore) {
        this(
                bookStore.getId(),
                bookStore.getName(),
                bookStore.getLocation(),
                bookStore.getBookInventories()
        );
    }

    public static BookStoreDTO fromEntity(BookStore bookStore) {
        return new BookStoreDTO(bookStore);
    }

    public BookStore toEntity() {
        return new BookStore(
                this.id,
                this.name,
                this.location,
                this.bookInventories
        );
    }
}