package com.coyote.big_city_library.rest_server.dao.repositories;

import com.coyote.big_city_library.rest_server.dao.entities.Author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer>  {
    
}
