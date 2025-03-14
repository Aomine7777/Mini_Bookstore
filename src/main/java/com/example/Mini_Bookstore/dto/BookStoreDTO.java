package com.example.Mini_Bookstore.dto;

import com.example.Mini_Bookstore.entity.BookStore;

import java.util.List;

public record BookStoreDTO(
        String id,
        String name,
        String location,
        List<BookInventoryDTO> bookInventories
) {
    public BookStoreDTO (BookStore bookStore) {
        this(
                bookStore.getId(),
                bookStore.getName(),
                bookStore.getLocation(),
                bookStore.getBookInventories().stream()
                        .map(BookInventoryDTO::new)
                        .toList()
        );
    }

    public BookStore toEntity() {
        return new BookStore(
                this.id,
                this.name,
                this.location,
                this.bookInventories.stream()
                        .map(BookInventoryDTO::toEntity)
                        .toList()
        );
    }
}