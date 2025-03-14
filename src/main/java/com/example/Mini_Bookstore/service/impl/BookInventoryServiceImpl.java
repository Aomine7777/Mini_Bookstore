package com.example.Mini_Bookstore.service.impl;

import com.example.Mini_Bookstore.dto.BookInventoryDTO;
import com.example.Mini_Bookstore.entity.BookInventory;
import com.example.Mini_Bookstore.entity.Category;
import com.example.Mini_Bookstore.exceptions.BookInventoryNotFoundException;
import com.example.Mini_Bookstore.exceptions.InsufficientStockException;
import com.example.Mini_Bookstore.exceptions.InvalidBookInventoryDataException;
import com.example.Mini_Bookstore.repository.BookInventoryRepository;
import com.example.Mini_Bookstore.repository.BookRepository;
import com.example.Mini_Bookstore.service.BookInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookInventoryServiceImpl implements BookInventoryService {

    private final BookInventoryRepository bookInventoryRepository;
    private final BookRepository bookRepository;

    @Override
    public BookInventoryDTO addBookInventory(BookInventoryDTO bookInventoryDTO) {
        if (bookInventoryDTO == null || bookInventoryDTO.bookId() == null || bookInventoryDTO.bookStoreId() == null) {
            throw new InvalidBookInventoryDataException("Invalid book inventory data. Book ID and Store ID are required.");
        }
        return new BookInventoryDTO(bookInventoryRepository.save(bookInventoryDTO.toEntity()));
    }

    @Override
    public BookInventoryDTO getBookInventoryById(String id) {
        return bookInventoryRepository.findById(id).map(BookInventoryDTO::new).orElseThrow(()-> new BookInventoryNotFoundException(id));
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
    public BookInventoryDTO updateBookInventory(String id, BookInventoryDTO bookInventoryDTO) {
        return bookInventoryRepository.findById(id)
                .map(existingInventory -> {
                    if (bookInventoryDTO.bookId() == null || bookInventoryDTO.bookStoreId() == null) {
                        throw new InvalidBookInventoryDataException("Book ID and Store ID must not be null.");
                        }
            existingInventory.setBookId(bookInventoryDTO.bookId());
            existingInventory.setBookStoreId(bookInventoryDTO.bookStoreId());
            existingInventory.setPrice(bookInventoryDTO.price());
            existingInventory.setTotalCount(bookInventoryDTO.totalCount());
            existingInventory.setSoldCount(bookInventoryDTO.soldCount());
            BookInventory updatedInventory = bookInventoryRepository.save(existingInventory);
            return new BookInventoryDTO(updatedInventory);
        }).orElseThrow(()-> new BookInventoryNotFoundException("Book inventory with ID " + id + " not found."));
    }

    @Override
    public void deleteBookInventory(String id) {
        bookInventoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean sellBooks(String bookId, String bookStoreId, int quantity) {
        return bookInventoryRepository.findByBookIdAndBookStoreId(bookId, bookStoreId)
           .map(inventory -> {
                if(inventory.getTotalCount() < quantity) {
                        throw new InsufficientStockException("Not enough stock for book ID " + bookId + " in store " + bookStoreId);
                }
                inventory.setTotalCount(inventory.getTotalCount() - quantity);
                inventory.setSoldCount(inventory.getSoldCount() + quantity);
                bookInventoryRepository.save(inventory);
                return true;
        }).orElseThrow(() -> new BookInventoryNotFoundException("Book inventory not found for book ID " + bookId + " in store " + bookStoreId));
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