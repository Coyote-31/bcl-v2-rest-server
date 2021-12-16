package com.coyote.big_city_library.rest_server.dao.repositories;

import com.coyote.big_city_library.rest_server.dao.entities.Publisher;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Repository class handling publishers.
 * 
 * @see Publisher
 */
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
    
}
