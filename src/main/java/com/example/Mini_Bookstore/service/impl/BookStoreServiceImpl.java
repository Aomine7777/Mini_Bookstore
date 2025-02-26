package com.example.Mini_Bookstore.service.impl;

import com.example.Mini_Bookstore.dto.BookStoreDTO;
import com.example.Mini_Bookstore.entity.BookStore;
import com.example.Mini_Bookstore.repository.BookStoreRepository;
import com.example.Mini_Bookstore.service.BookStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookStoreServiceImpl implements BookStoreService {

    private final BookStoreRepository bookStoreRepository;

    @Override
    public BookStoreDTO addBookStore(BookStoreDTO bookStoreDTO) {
        BookStore bookStore = bookStoreDTO.toEntity();
        return BookStoreDTO.fromEntity(bookStoreRepository.save(bookStore));
    }

    @Override
    public Optional<BookStoreDTO> getBookStoreByID(String id) {
        return bookStoreRepository.findById(id)
                .map(BookStoreDTO::new);
    }

    public List<BookStoreDTO> getAllBookStores() {
        return bookStoreRepository.findAll().stream()
                .map(BookStoreDTO::new) // Преобразование всех сущностей в DTO
                .collect(Collectors.toList());
    }

    @Override
    public BookStoreDTO updateBookStore(String id, BookStoreDTO updatedBookStoreDTO) {
        return bookStoreRepository.findById(id)
                .map(existingBookStore -> {
                    existingBookStore.setName(updatedBookStoreDTO.name());
                    existingBookStore.setLocation(updatedBookStoreDTO.location());
                    existingBookStore.setBookInventories(updatedBookStoreDTO.bookInventories());
                    BookStore updatedBookStore = bookStoreRepository.save(existingBookStore);
                    return new BookStoreDTO(updatedBookStore);
                }).orElseThrow(() -> new RuntimeException("BookStore not found with id: " + id));
    }

    @Override
    public Optional<BookStoreDTO> deleteBookStoreById(String id) {
        Optional<BookStore> bookStoreOptional = bookStoreRepository.findById(id);
        if (bookStoreOptional.isPresent()) {
            BookStore bookStore = bookStoreOptional.get();
            bookStoreRepository.deleteById(id);
            return Optional.of(new BookStoreDTO(bookStore));
        } else {
            throw new RuntimeException("BookStore not found with id: " + id);
        }
    }
}