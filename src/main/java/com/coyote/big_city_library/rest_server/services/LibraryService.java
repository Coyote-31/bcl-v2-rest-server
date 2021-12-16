package com.coyote.big_city_library.rest_server.services;

import java.util.List;
import java.util.Optional;

import com.coyote.big_city_library.rest_server.dao.entities.Library;
import com.coyote.big_city_library.rest_server.dao.repositories.LibraryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  Service class handling libraries
 * 
 * @see LibraryRepository
 */
@Component
public class LibraryService {
    
    @Autowired
    private LibraryRepository libraryRepository;

    /**
     * Saves the given library. 
     * Update or Create if the library doesn't exist.
     * 
     * @param library
     * @see Library
     */
    public Library saveLibrary(Library library) {
        return libraryRepository.save(library);
    }

    /**
     * Returns a list of all the libraries.
     * 
     * @return All the libraries.
     * @see Library
     */
    public List<Library> findAllLibraries() {
        return libraryRepository.findAll();
    }

    /**
     * Returns a library with a given id.
     * 
     * @param id of a library.
     * @return The library with the given id or Optional#empty() if none found.
     */
    public Optional<Library> findLibraryById(Integer id) {
        return libraryRepository.findById(id);
    }

    /**
     * Deletes a given library.
     * 
     * @param library to delete.
     * @see Library
     */
    public void deleteLibrary(Library library) {
        libraryRepository.delete(library);
    }

    /**
     * Deletes a library with a given id
     * 
     * @param id of a library.
     */
    public void deleteLibraryById(Integer id) {
        libraryRepository.deleteById(id);
    }

}
