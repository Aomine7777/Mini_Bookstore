package com.example.Mini_Bookstore.service.impl;

import com.example.Mini_Bookstore.entity.BookStore;
import com.example.Mini_Bookstore.repository.BookStoreRepository;
import com.example.Mini_Bookstore.service.BookStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookStoreServiceImpl implements BookStoreService {

    private final BookStoreRepository bookStoreRepository;

    @Override
    public BookStore addBookStore(BookStore bookStore) {
        return bookStoreRepository.save(bookStore);
    }

    @Override
    public Optional<BookStore> getBookStoreByID(String id) {
        return bookStoreRepository.findById(id);
    }
    public List<BookStore> getAllBookStores() {
        return bookStoreRepository.findAll();
    }
    @Override
    public BookStore updateBookStore(String id, BookStore updatedBookStore) {
        return bookStoreRepository.findById(id)
                .map(existingBookStore -> {
                    existingBookStore.setName(updatedBookStore.getName());
                    existingBookStore.setLocation(updatedBookStore.getLocation());
                    existingBookStore.setBookInventories(updatedBookStore.getBookInventories());
                    return bookStoreRepository.save(existingBookStore);
                })
                .orElseThrow(() -> new RuntimeException("BookStore not found with id: " + id));
    }

    @Override
    public Optional<BookStore> deleteBookStoreById(String id) {
        if (bookStoreRepository.existsById(id)) {
            bookStoreRepository.deleteById(id);
        } else {
            throw new RuntimeException("BookStore not found with id: " + id);
        }
        return Optional.empty();
    }
}
