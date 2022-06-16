package com.coyote.big_city_library.rest_server_repository.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coyote.big_city_library.rest_server_model.dao.entities.Exemplary;

/**
 * Repository class handling exemplaries.
 *
 * @see Exemplary
 */
public interface ExemplaryRepository extends JpaRepository<Exemplary, Integer> {

}
