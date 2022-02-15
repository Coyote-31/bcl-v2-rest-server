package com.coyote.big_city_library.rest_server.controllers;

import java.util.List;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server.dto.BookDto;
import com.coyote.big_city_library.rest_server.dto.search_books.SearchBookDto;
import com.coyote.big_city_library.rest_server.services.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/add")
    public BookDto addBook(@Valid @RequestBody BookDto bookDto) {
        BookDto bookSaved = bookService.addBook(bookDto);
        log.debug("addBook() => book with title '{}' added", bookSaved.getTitle());
        return bookSaved;
    }

    @GetMapping("")
    public List<BookDto> findAllBooks() {
        List<BookDto> books = bookService.findAllBooks();
        log.debug("findAllBooks() => {} book(s) found", books.size());
        return books;
    }
    
    @GetMapping("/{id}")
    public BookDto findBookById(@PathVariable Integer id) {
        BookDto bookDto = bookService.findBookById(id);
        if (bookDto != null) {
            log.debug("findBookById() => book with title '{}' found", bookDto.getTitle());
        } else {
            log.debug("findBookById() => No book found with id '{}'", id);
        }
        return bookDto;
    }

    @PostMapping("/search")
    public List<SearchBookDto> searchBooks(
        @RequestParam("bookTitle") String bookTitle,
        @RequestParam("authorName") String authorName,
        @RequestParam("publisherName") String publisherName) {

        List<SearchBookDto> books = bookService.searchBooks(bookTitle, authorName, publisherName);
        log.debug("searchBooks('{}' '{}' '{}') => {} book(s) found", bookTitle, authorName, publisherName, books.size());
        return books;
    }

    @PutMapping("/update")
    public BookDto updateBook(@Valid @RequestBody BookDto bookDto) {
        BookDto bookUpdated = bookService.updateBook(bookDto);
        log.debug("updateBook() => book with title '{}' updated", bookUpdated.getTitle());
        return bookUpdated;
    }

    @DeleteMapping("/delete")
    public void deleteBook(@Valid @RequestBody BookDto bookDto) {
        bookService.deleteBook(bookDto);
        log.debug("deleteBook() => book with title '{}' removed", bookDto.getTitle());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBookById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
        log.debug("deleteBookById() => book with id '{}' removed", id);
    }

}
