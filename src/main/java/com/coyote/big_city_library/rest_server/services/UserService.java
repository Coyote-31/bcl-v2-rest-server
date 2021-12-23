package com.coyote.big_city_library.rest_server.services;

import java.util.List;
import java.util.Optional;

import com.coyote.big_city_library.rest_server.dao.entities.User;
import com.coyote.big_city_library.rest_server.dao.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  Service class handling users
 * 
 * @see UserRepository
 */
@Component
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    /**
     * Adds a new given user.
     * 
     * @param user to add.
     * @return The added user; will never be null.
     * @see User
     */
    public User addUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Returns a list of all the users.
     * 
     * @return All the users.
     * @see User
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Returns a user with a given id.
     * 
     * @param id of a user.
     * @return The user with the given id or Optional#empty() if none found.
     */
    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

     /**
     * Updates a given user.
     * 
     * @param user to update.
     * @return The updated user; will never be null.
     * @see User
     */
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Deletes a given user.
     * 
     * @param user to delete.
     * @see User
     */
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    /**
     * Deletes a user with a given id
     * 
     * @param id of a user.
     */
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

}
