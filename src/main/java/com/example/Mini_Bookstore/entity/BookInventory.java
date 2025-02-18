package com.example.Mini_Bookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "book_inventory")
public class BookInventory {
    @Id
    private String id;
    private String bookId;
    private String bookStoreId;
    private int price;
    private int totalCount;
    private int soldCount;
}