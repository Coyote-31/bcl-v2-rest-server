package com.coyote.big_city_library.rest_server.dao.repositories;

import com.coyote.big_city_library.rest_server.dao.entities.Library;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Repository class handling libraries.
 * 
 * @see Library
 */
public interface LibraryRepository extends JpaRepository<Library, Integer> {
    
}
