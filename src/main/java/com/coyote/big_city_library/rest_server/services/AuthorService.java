package com.coyote.big_city_library.rest_server.services;

import java.util.List;
import java.util.Optional;

import com.coyote.big_city_library.rest_server.dao.entities.Author;
import com.coyote.big_city_library.rest_server.dao.repositories.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  Service class handling authors
 * 
 * @see AuthorRepository
 */
@Component
public class AuthorService {
    
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Saves the given author. 
     * Update or Create if the author doesn't exist.
     * 
     * @param author
     * @see Author
     */
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    /**
     * Returns a list of all the authors.
     * 
     * @return All the authors.
     * @see Author
     */
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Returns a author with a given id.
     * 
     * @param id of a author.
     * @return The author with the given id or Optional#empty() if none found.
     */
    public Optional<Author> findAuthorById(Integer id) {
        return authorRepository.findById(id);
    }

    /**
     * Deletes a given author.
     * 
     * @param author to delete.
     * @see Author
     */
    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
    }

    /**
     * Deletes a author with a given id
     * 
     * @param id of a author.
     */
    public void deleteAuthorById(Integer id) {
        authorRepository.deleteById(id);
    }

}
