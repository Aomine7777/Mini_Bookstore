package com.example.Mini_Bookstore.service.impl;

import com.example.Mini_Bookstore.dto.BookInventoryDTO;
import com.example.Mini_Bookstore.dto.BookStoreDTO;
import com.example.Mini_Bookstore.entity.BookInventory;
import com.example.Mini_Bookstore.entity.BookStore;
import com.example.Mini_Bookstore.exceptions.BookStoreNotFoundException;
import com.example.Mini_Bookstore.exceptions.InvalidBookStoreDataException;
import com.example.Mini_Bookstore.repository.BookStoreRepository;
import com.example.Mini_Bookstore.service.BookStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookStoreServiceImpl implements BookStoreService {

    private final BookStoreRepository bookStoreRepository;

    @Override
    public BookStoreDTO addBookStore(BookStoreDTO bookStoreDTO) {
        if (bookStoreDTO == null || bookStoreDTO.name() == null || bookStoreDTO.location() == null) {
            throw new InvalidBookStoreDataException("Invalid book store data. Name and location are required.");
        }
        BookStore bookStore = bookStoreDTO.toEntity();
        return new BookStoreDTO(bookStoreRepository.save(bookStore));
    }

    @Override
    public BookStoreDTO getBookStoreByID(String id) {
        return bookStoreRepository.findById(id)
                .map(BookStoreDTO::new)
                .orElseThrow(() -> new BookStoreNotFoundException("Book store not found with ID: " + id));
    }

    public List<BookStoreDTO> getAllBookStores() {
        return bookStoreRepository.findAll().stream()
                .map(BookStoreDTO::new) // Преобразование всех сущностей в DTO
                .collect(Collectors.toList());
    }

    @Override
    public BookStoreDTO updateBookStore(String id, BookStoreDTO updatedBookStoreDTO) {
        if (updatedBookStoreDTO == null || updatedBookStoreDTO.name() == null || updatedBookStoreDTO.location() == null) {
            throw new InvalidBookStoreDataException("Invalid book store data. Name and location are required.");
        }
        return bookStoreRepository.findById(id)
                .map(existingBookStore -> {
                    existingBookStore.setName(updatedBookStoreDTO.name());
                    existingBookStore.setLocation(updatedBookStoreDTO.location());

                    List<BookInventory> updateBookInventories = updatedBookStoreDTO.bookInventories().stream()
                                    .map(BookInventoryDTO::toEntity)
                                    .toList();
                    existingBookStore.setBookInventories(updateBookInventories);

                    BookStore updatedBookStore = bookStoreRepository.save(existingBookStore);
                    return new BookStoreDTO(updatedBookStore);
                }).orElseThrow(() -> new BookStoreNotFoundException("BookStore not found with id: " + id));
    }

    @Override
    public BookStoreDTO deleteBookStoreById(String id) {
        BookStore bookStore = bookStoreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BookStore not found with id: " + id));
        bookStoreRepository.deleteById(id);
        return new BookStoreDTO(bookStore);
    }
}