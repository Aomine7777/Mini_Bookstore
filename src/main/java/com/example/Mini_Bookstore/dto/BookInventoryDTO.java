package com.example.Mini_Bookstore.dto;

import com.example.Mini_Bookstore.entity.BookInventory;

import java.util.List;
import java.util.stream.Collectors;

public record BookInventoryDTO(
        String id,
        String bookId,
        String bookStoreId,
        int price,
        int totalCount,
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