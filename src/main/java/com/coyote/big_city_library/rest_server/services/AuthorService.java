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
     * Adds a new given author.
     * 
     * @param author to add.
     * @return The added author; will never be null.
     * @see Author
     */
    public Author addAuthor(Author author) {
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
     * Updates a given author.
     * 
     * @param author to update.
     * @return The updated author; will never be null.
     * @see Author
     */
    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
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
