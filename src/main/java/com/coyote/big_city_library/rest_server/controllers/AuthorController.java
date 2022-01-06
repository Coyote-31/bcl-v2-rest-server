package com.coyote.big_city_library.rest_server.controllers;

import java.util.List;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server.dto.AuthorDto;
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
    public AuthorDto addAuthor(@Valid @RequestBody AuthorDto authorDto) {
        AuthorDto authorSaved = authorService.addAuthor(authorDto);
        log.debug("addAuthor() => author with name '{}' added", authorSaved.getName());
        return authorSaved;
    }

    @GetMapping("/all")
    public List<AuthorDto> findAllAuthors() {
        List<AuthorDto> authors = authorService.findAllAuthors();
        log.debug("findAllAuthors() => {} author(s) found", authors.size());
        return authors;
    }
    
    @GetMapping("/{id}")
    public AuthorDto findAuthorById(@PathVariable Integer id) {
        AuthorDto authorDto = authorService.findAuthorById(id);
        if (authorDto != null) {
            log.debug("findAuthorById() => author with name '{}' found", authorDto.getName());
        } else {
            log.debug("findAuthorById() => No Author found with id '{}'", id);
        }
        
        return authorDto;
    }

    @PutMapping("/update")
    public AuthorDto updateAuthor(@Valid @RequestBody AuthorDto authorDto) {
        AuthorDto authorUpdated = authorService.updateAuthor(authorDto);
        log.debug("updateAuthor() => author with name '{}' updated", authorUpdated.getName());
        return authorUpdated;
    }

    @DeleteMapping("/delete")
    public void deleteAuthor(@Valid @RequestBody AuthorDto authorDto) {
        authorService.deleteAuthor(authorDto);
        log.debug("deleteAuthor() => author with name '{}' removed", authorDto.getName());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthorById(@PathVariable Integer id) {
        authorService.deleteAuthorById(id);
        log.debug("deleteAuthorById() => author with id '{}' removed", id);
    }

}
