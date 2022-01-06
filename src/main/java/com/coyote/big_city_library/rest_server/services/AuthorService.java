package com.coyote.big_city_library.rest_server.services;

import java.util.List;

import com.coyote.big_city_library.rest_server.dao.entities.Author;
import com.coyote.big_city_library.rest_server.dao.repositories.AuthorRepository;
import com.coyote.big_city_library.rest_server.dto.AuthorDto;
import com.coyote.big_city_library.rest_server.dto.AuthorMapper;

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

    @Autowired
    protected AuthorMapper authorMapper;

    /**
     * Adds a new given author.
     * 
     * @param author to add.
     * @return The added author; will never be null.
     * @see Author
     * @see AuthorDto
     */
    public AuthorDto addAuthor(AuthorDto authorDto) {
        Author author = authorMapper.toModel(authorDto);
        author = authorRepository.save(author);

        return authorMapper.toDto(author);
    }

    /**
     * Returns a list of all the authors.
     * 
     * @return All the authors.
     * @see Author
     * @see AuthorDto
     */
    public List<AuthorDto> findAllAuthors() {
        return authorMapper.toDto(authorRepository.findAll());
    }

    /**
     * Returns a author with a given id.
     * 
     * @param id of a author.
     * @return The author with the given id or null if none found.
     * @see Author
     * @see AuthorDto
     */
    public AuthorDto findAuthorById(Integer id) {
        return authorMapper.toDto(authorRepository.findById(id).orElse(null));
    }

    /**
     * Updates a given author.
     * 
     * @param author to update.
     * @return The updated author; will never be null.
     * @see Author
     * @see AuthorDto
     */
    public AuthorDto updateAuthor(AuthorDto authorDto) {
        Author author = authorMapper.toModel(authorDto);
        return authorMapper.toDto(authorRepository.save(author));
    }

    /**
     * Deletes a given author.
     * 
     * @param author to delete.
     * @see Author
     * @see AuthorDto
     */
    public void deleteAuthor(AuthorDto authorDto) {
        authorRepository.delete(authorMapper.toModel(authorDto));
    }

    /**
     * Deletes a author with a given id
     * 
     * @param id of a author.
     * @see Author
     * @see AuthorDto
     */
    public void deleteAuthorById(Integer id) {
        authorRepository.deleteById(id);
    }

}
