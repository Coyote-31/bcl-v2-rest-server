package com.coyote.big_city_library.rest_server_service.services;

import java.util.List;

import com.coyote.big_city_library.rest_server_model.dao.entities.Library;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.LibraryRepository;
import com.coyote.big_city_library.rest_server_service.dto.LibraryDto;
import com.coyote.big_city_library.rest_server_service.dto.LibraryMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class handling libraries
 *
 * @see LibraryRepository
 */
@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    protected LibraryMapper libraryMapper;

    /**
     * Adds a new given library.
     *
     * @param libraryDto to add.
     * @return The added library; will never be null.
     * @see Library
     * @see LibraryDto
     */
    public LibraryDto addLibrary(LibraryDto libraryDto) {
        Library library = libraryMapper.toModel(libraryDto);
        library = libraryRepository.save(library);

        return libraryMapper.toDto(library);
    }

    /**
     * Returns a list of all the libraries.
     *
     * @return All the libraries.
     * @see Library
     * @see LibraryDto
     */
    public List<LibraryDto> findAllLibraries() {
        return libraryMapper.toDto(libraryRepository.findAll());
    }

    /**
     * Returns a library with a given id.
     *
     * @param id of a library.
     * @return The library with the given id or null if none found.
     * @see Library
     * @see LibraryDto
     */
    public LibraryDto findLibraryById(Integer id) {
        return libraryMapper.toDto(libraryRepository.findById(id).orElse(null));
    }

    /**
     * Updates a given library.
     *
     * @param libraryDto to update.
     * @return The updated library; will never be null.
     * @see Library
     * @see LibraryDto
     */
    public LibraryDto updateLibrary(LibraryDto libraryDto) {
        Library library = libraryMapper.toModel(libraryDto);
        return libraryMapper.toDto(libraryRepository.save(library));
    }

    /**
     * Deletes a given library.
     *
     * @param libraryDto to delete.
     * @see Library
     * @see LibraryDto
     */
    public void deleteLibrary(LibraryDto libraryDto) {
        libraryRepository.delete(libraryMapper.toModel(libraryDto));
    }

    /**
     * Deletes a library with a given id
     *
     * @param id of a library.
     * @see Library
     * @see LibraryDto
     */
    public void deleteLibraryById(Integer id) {
        libraryRepository.deleteById(id);
    }

}
