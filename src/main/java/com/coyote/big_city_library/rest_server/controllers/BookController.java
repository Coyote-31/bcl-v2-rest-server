package com.coyote.big_city_library.rest_server.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server.dao.entities.Book;
import com.coyote.big_city_library.rest_server.services.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/add")
    public Book addBook(@Valid @RequestBody Book book) {
        Book bookSaved = bookService.addBook(book);
        log.debug("addBook() => book with title '{}' added", bookSaved.getTitle());
        return bookSaved;
    }

    @GetMapping("/all")
    public List<Book> findAllBooks() {
        List<Book> books = bookService.findAllBooks();
        log.debug("findAllBooks() => {} book(s) found", books.size());
        return books;
    }
    
    @GetMapping("/{id}")
    public Book findBookById(@PathVariable Integer id) {
        Optional<Book> optionalBook = bookService.findBookById(id);
        Book book;
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
            log.debug("findBookById() => book with title '{}' found", book.getTitle());
        } else {
            book = null;
            log.debug("findBookById() => No book found with id '{}'", id);
        }
        return book;
    }

    @PutMapping("/update")
    public Book updateBook(@Valid @RequestBody Book book) {
        Book bookUpdated = bookService.updateBook(book);
        log.debug("updateBook() => book with title '{}' updated", bookUpdated.getTitle());
        return bookUpdated;
    }

    @DeleteMapping("/delete")
    public void deleteBook(@Valid @RequestBody Book book) {
        bookService.deleteBook(book);
        log.debug("deleteBook() => book with title '{}' removed", book.getTitle());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBookById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
        log.debug("deleteBookById() => book with id '{}' removed", id);
    }

}
