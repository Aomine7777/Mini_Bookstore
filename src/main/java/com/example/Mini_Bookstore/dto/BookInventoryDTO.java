package com.example.Mini_Bookstore.dto;

import com.example.Mini_Bookstore.entity.BookInventory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.stream.Collectors;

public record BookInventoryDTO(
        String id,

        @NotBlank(message = "Book ID cannot be blank")
        String bookId,

        @NotBlank(message = "Book Store ID cannot be blank")
        String bookStoreId,

        @Min(value = 0, message = "Price must be at least 0")
        int price,

        @Min(value = 0, message = "Total count must be at least 0")
        int totalCount,

        @Min(value = 0, message = "Sold count must be at least 0")
        int soldCount
) {
    public BookInventoryDTO(BookInventory bookInventory) {
        this(
                bookInventory.getId(),
                bookInventory.getBookId(),
                bookInventory.getBookStoreId(),
                bookInventory.getPrice(),
                bookInventory.getTotalCount(),
                bookInventory.getSoldCount()
        );
    }

    public static List<BookInventoryDTO> fromEntities(List<BookInventory> bookInventories) {
        return bookInventories.stream()
                .map(BookInventoryDTO::new)
                .collect(Collectors.toList());
    }

    public BookInventory toEntity() {
        return new BookInventory(
                this.id,
                this.bookId,
                this.bookStoreId,
                this.price,
                this.totalCount,
                this.soldCount
        );
    }
}