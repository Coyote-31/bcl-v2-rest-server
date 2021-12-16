package com.coyote.big_city_library.rest_server.dao.repositories;

import com.coyote.big_city_library.rest_server.dao.entities.Exemplary;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Repository class handling exemplaries.
 * 
 * @see Exemplary
 */
public interface ExemplaryRepository extends JpaRepository<Exemplary, Integer> {
    
}
