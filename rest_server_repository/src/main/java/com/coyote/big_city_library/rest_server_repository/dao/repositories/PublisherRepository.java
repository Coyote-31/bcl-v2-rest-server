package com.coyote.big_city_library.rest_server_repository.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coyote.big_city_library.rest_server_model.dao.entities.Publisher;

/**
 * Repository class handling publishers.
 *
 * @see Publisher
 */
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

}
