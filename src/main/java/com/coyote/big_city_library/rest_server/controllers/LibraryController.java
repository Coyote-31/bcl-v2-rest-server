package com.coyote.big_city_library.rest_server.controllers;

import java.util.List;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server.dto.LibraryDto;
import com.coyote.big_city_library.rest_server.services.LibraryService;

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
@RequestMapping("/libraries")
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @PostMapping("/add")
    public LibraryDto addLibrary(@Valid @RequestBody LibraryDto libraryDto) {
        LibraryDto librarySaved = libraryService.addLibrary(libraryDto);
        log.debug("addLibrary() => library with name '{}' added", librarySaved.getName());
        return librarySaved;
    }

    @GetMapping("")
    public List<LibraryDto> findAllLibraries() {
        List<LibraryDto> libraries = libraryService.findAllLibraries();
        log.debug("findAllLibraries() => {} library(s) found", libraries.size());
        return libraries;
    }
    
    @GetMapping("/{id}")
    public LibraryDto findLibraryById(@PathVariable Integer id) {
        LibraryDto libraryDto = libraryService.findLibraryById(id);
        if (libraryDto != null) {
            log.debug("findLibraryById() => library with name '{}' found", libraryDto.getName());
        } else {
            log.debug("findLibraryById() => No library found with id '{}'", id);
        }
        return libraryDto;
    }
    
    @PutMapping("/update")
    public LibraryDto updateLibrary(@Valid @RequestBody LibraryDto libraryDto) {
        LibraryDto libraryUpdated = libraryService.updateLibrary(libraryDto);
        log.debug("updateLibrary() => library with name '{}' updated", libraryUpdated.getName());
        return libraryUpdated;
    }

    @DeleteMapping("/delete")
    public void deleteLibrary(@Valid @RequestBody LibraryDto libraryDto) {
        libraryService.deleteLibrary(libraryDto);
        log.debug("deleteLibrary() => library with name '{}' removed", libraryDto.getName());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLibraryById(@PathVariable Integer id) {
        libraryService.deleteLibraryById(id);
        log.debug("deleteLibraryById() => library with id '{}' removed", id);
    }

}
