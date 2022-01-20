package com.coyote.big_city_library.rest_server.dao.repositories;

import java.util.List;

import com.coyote.big_city_library.rest_server.dao.entities.Book;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Repository class handling books.
 * 
 * @see Book
 */
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> searchByTitleContainingIgnoreCaseAndAuthorsNameContainingIgnoreCaseAndPublisherNameContainingIgnoreCase(
        String bookTitle, 
        String authorName, 
        String publisherName);
    
}
