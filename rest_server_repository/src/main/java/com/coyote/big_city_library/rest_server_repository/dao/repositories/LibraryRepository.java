package com.coyote.big_city_library.rest_server_repository.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coyote.big_city_library.rest_server_model.dao.entities.Library;

/**
 * Repository class handling libraries.
 *
 * @see Library
 */
public interface LibraryRepository extends JpaRepository<Library, Integer> {

}
