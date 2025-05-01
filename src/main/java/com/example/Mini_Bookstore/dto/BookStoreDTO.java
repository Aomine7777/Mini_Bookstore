package com.example.Mini_Bookstore.dto;

import com.example.Mini_Bookstore.entity.BookStore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record BookStoreDTO(
        String id,

        @NotNull(message = "Name cannot be null")
        @NotEmpty(message = "Name cannot be empty")
        @Size(min = 2, max = 18)
        String name,

        @NotNull(message = "Location cannot be null")
        @NotEmpty(message = "Location cannot be empty")
        String location,

        @NotNull(message = "BookInventories cannot be null")
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
                this.bookInventories != null
                        ? this.bookInventories.stream().map(BookInventoryDTO::toEntity).toList()
                        : List.of()
        );
    }
}