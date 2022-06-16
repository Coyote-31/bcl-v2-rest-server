package com.coyote.big_city_library.rest_server_repository.dao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coyote.big_city_library.rest_server_model.dao.entities.User;

/**
 * Repository class handling users
 *
 * @see User
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByPseudo(String pseudo);
}
