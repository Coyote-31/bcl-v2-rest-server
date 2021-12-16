package com.coyote.big_city_library.rest_server.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server.dao.entities.Library;
import com.coyote.big_city_library.rest_server.services.LibraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @PutMapping("/save")
    public Library saveLibrary(@Valid @RequestBody Library library) {
        Library librarySaved = libraryService.saveLibrary(library);
        log.debug("saveLibrary() => library with name '{}' saved", librarySaved.getName());
        return librarySaved;
    }

    @GetMapping("/all")
    public List<Library> findAllLibraries() {
        List<Library> libraries = libraryService.findAllLibraries();
        log.debug("findAllLibraries() => {} library(s) found", libraries.size());
        return libraries;
    }
    
    @GetMapping("/{id}")
    public Library findLibraryById(@PathVariable Integer id) {
        Optional<Library> optionalLibrary = libraryService.findLibraryById(id);
        Library library;
        if (optionalLibrary.isPresent()) {
            library = optionalLibrary.get();
            log.debug("findLibraryById() => library with name '{}' found", library.getName());
        } else {
            library = null;
            log.debug("findLibraryById() => No library found with id '{}'", id);
        }
        return library;
    }

    @DeleteMapping("/delete")
    public void deleteLibrary(@Valid @RequestBody Library library) {
        libraryService.deleteLibrary(library);
        log.debug("deleteLibrary() => library with name '{}' removed", library.getName());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLibraryById(@PathVariable Integer id) {
        libraryService.deleteLibraryById(id);
        log.debug("deleteLibraryById() => library with id '{}' removed", id);
    }

}
