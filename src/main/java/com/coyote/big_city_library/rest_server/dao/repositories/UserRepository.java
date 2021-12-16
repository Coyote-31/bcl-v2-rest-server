package com.coyote.big_city_library.rest_server.dao.repositories;

import com.coyote.big_city_library.rest_server.dao.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Repository class handling users
 * 
 * @see User
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    
}
