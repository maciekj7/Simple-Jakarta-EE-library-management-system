package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class BookService {
    @Inject
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Long id) throws Exception {
        try {
            bookRepository.delete(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findAvailable();
    }
}