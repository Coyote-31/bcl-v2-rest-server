package com.coyote.big_city_library.rest_server.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server.dao.entities.Author;
import com.coyote.big_city_library.rest_server.services.AuthorService;

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
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/add")
    public Author addAuthor(@Valid @RequestBody Author author) {
        Author authorSaved = authorService.addAuthor(author);
        log.debug("addAuthor() => author with name '{}' added", authorSaved.getName());
        return authorSaved;
    }

    @GetMapping("/all")
    public List<Author> findAllAuthors() {
        List<Author> authors = authorService.findAllAuthors();
        log.debug("findAllAuthors() => {} author(s) found", authors.size());
        return authors;
    }
    
    @GetMapping("/{id}")
    public Author findAuthorById(@PathVariable Integer id) {
        Optional<Author> optionalAuthor = authorService.findAuthorById(id);
        Author author;
        if (optionalAuthor.isPresent()) {
            author = optionalAuthor.get();
            log.debug("findAuthorById() => author with name '{}' found", author.getName());
        } else {
            author = null;
            log.debug("findAuthorById() => No author found with id '{}'", id);
        }
        return author;
    }

    @PutMapping("/update")
    public Author updateAuthor(@Valid @RequestBody Author author) {
        Author authorUpdated = authorService.updateAuthor(author);
        log.debug("updateAuthor() => author with name '{}' updated", authorUpdated.getName());
        return authorUpdated;
    }

    @DeleteMapping("/delete")
    public void deleteAuthor(@Valid @RequestBody Author author) {
        authorService.deleteAuthor(author);
        log.debug("deleteAuthor() => author with name '{}' removed", author.getName());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthorById(@PathVariable Integer id) {
        authorService.deleteAuthorById(id);
        log.debug("deleteAuthorById() => author with id '{}' removed", id);
    }

}
