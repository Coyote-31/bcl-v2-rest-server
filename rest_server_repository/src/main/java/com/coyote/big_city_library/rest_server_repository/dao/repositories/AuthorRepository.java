package com.coyote.big_city_library.rest_server_repository.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coyote.big_city_library.rest_server_model.dao.entities.Author;

/**
 * Repository class handling authors.
 *
 * @see Author
 */
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
