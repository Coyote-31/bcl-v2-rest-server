package com.coyote.big_city_library.rest_server.services;

import java.util.List;
import java.util.Optional;

import com.coyote.big_city_library.rest_server.dao.entities.Book;
import com.coyote.big_city_library.rest_server.dao.repositories.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  Service class handling books
 * 
 * @see BookRepository
 */
@Component
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;

    /**
     * Saves the given book. 
     * Update or Create if the book doesn't exist.
     * 
     * @param book
     * @see Book
     */
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Returns a list of all the books.
     * 
     * @return All the books.
     * @see Book
     */
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Returns a book with a given id.
     * 
     * @param id of a book.
     * @return The book with the given id or Optional#empty() if none found.
     */
    public Optional<Book> findBookById(Integer id) {
        return bookRepository.findById(id);
    }

    /**
     * Deletes a given book.
     * 
     * @param book to delete.
     * @see Book
     */
    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    /**
     * Deletes a book with a given id
     * 
     * @param id of a book.
     */
    public void deleteBookById(Integer id) {
        bookRepository.deleteById(id);
    }

}
