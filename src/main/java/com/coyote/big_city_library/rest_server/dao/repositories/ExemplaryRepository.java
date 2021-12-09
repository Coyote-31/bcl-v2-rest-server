package com.coyote.big_city_library.rest_server.dao.repositories;

import com.coyote.big_city_library.rest_server.dao.entities.Exemplary;

import org.springframework.data.repository.CrudRepository;

public interface ExemplaryRepository extends CrudRepository<Exemplary, Integer> {
    
}
