package com.example.Mini_Bookstore.service.impl;

import com.example.Mini_Bookstore.dto.BookInventoryDTO;
import com.example.Mini_Bookstore.entity.BookInventory;
import com.example.Mini_Bookstore.entity.Category;
import com.example.Mini_Bookstore.repository.BookInventoryRepository;
import com.example.Mini_Bookstore.repository.BookRepository;
import com.example.Mini_Bookstore.service.BookInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookInventoryServiceImpl implements BookInventoryService {

    private final BookInventoryRepository bookInventoryRepository;
    private final BookRepository bookRepository;

    @Override
    public BookInventoryDTO addBookInventory(BookInventoryDTO bookInventoryDTO) {
        return BookInventoryDTO.fromEntity(bookInventoryRepository.save(bookInventoryDTO.toEntity()));
    }

    @Override
    public Optional<BookInventoryDTO> getBookInventoryById(String id) {
        return bookInventoryRepository.findById(id).map(BookInventoryDTO::new);
    }

    @Override
    public List<BookInventoryDTO> getAllBookInventories() {
        return BookInventoryDTO.fromEntities(bookInventoryRepository.findAll());
    }

    @Override
    public List<BookInventoryDTO> getBookInventoriesByBookId(String bookId) {
        return BookInventoryDTO.fromEntities(bookInventoryRepository.findByBookId(bookId));
    }

    @Override
    public List<BookInventoryDTO> getBookInventoriesByStoreId(String bookStoreId) {
        return BookInventoryDTO.fromEntities(bookInventoryRepository.findByBookStoreId(bookStoreId));
    }

    @Override
    public Optional<BookInventoryDTO> updateBookInventory(String id, BookInventoryDTO bookInventoryDTO) {
        return bookInventoryRepository.findById(id).map(existingInventory -> {
            existingInventory.setBookId(bookInventoryDTO.bookId());
            existingInventory.setBookStoreId(bookInventoryDTO.bookStoreId());
            existingInventory.setPrice(bookInventoryDTO.price());
            existingInventory.setTotalCount(bookInventoryDTO.totalCount());
            existingInventory.setSoldCount(bookInventoryDTO.soldCount());
            BookInventory updatedInventory = bookInventoryRepository.save(existingInventory);
            return new BookInventoryDTO(updatedInventory);
        });
    }

    @Override
    public void deleteBookInventory(String id) {
        bookInventoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean sellMultipleBooks(String bookId, String bookStoreId, int quantity) {
        return bookInventoryRepository.findByBookIdAndBookStoreId(bookId, bookStoreId).filter(inventory -> inventory.getTotalCount() >= quantity).map(inventory -> {
            inventory.setTotalCount(inventory.getTotalCount() - quantity);
            inventory.setSoldCount(inventory.getSoldCount() + quantity);
            bookInventoryRepository.save(inventory);
            return true;
        }).orElse(false);
    }

    @Override
    public long getSoldBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author).stream()
             .mapToLong(book -> 
             bookInventoryRepository.findByBookId(book.getId()).stream()
             .mapToLong(BookInventory::getSoldCount)
             .sum())
             .sum();
    }

    @Override
    public long getSoldBooksByCategory(String category) {
        return bookRepository.findByCategory(Category.valueOf(category.toUpperCase())).stream()
             .mapToLong(book ->
             bookInventoryRepository.findByBookId(book.getId()).stream()
             .mapToLong(BookInventory::getSoldCount)
             .sum())
             .sum();
    }
}