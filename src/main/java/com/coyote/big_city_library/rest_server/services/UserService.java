package com.coyote.big_city_library.rest_server.services;

import java.util.List;

import com.coyote.big_city_library.rest_server.dao.entities.User;
import com.coyote.big_city_library.rest_server.dao.repositories.UserRepository;
import com.coyote.big_city_library.rest_server.dto.UserDto;
import com.coyote.big_city_library.rest_server.dto.UserMapper;

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

    @Autowired
    protected UserMapper userMapper;

    /**
     * Adds a new given user.
     * 
     * @param userDto to add.
     * @return The added user; will never be null.
     * @see User
     * @see UserDto
     */
    public UserDto addUser(UserDto userDto) {

        User user = userMapper.toModel(userDto);
        user = userRepository.save(user);
        
        return userMapper.toDto(user);
    }

    /**
     * Returns a list of all the users.
     * 
     * @return All the users.
     * @see User
     * @see UserDto
     */
    public List<UserDto> findAllUsers() {
        return userMapper.toDto(userRepository.findAll());
    }

    /**
     * Returns a user with a given id.
     * 
     * @param id of a user.
     * @return The user with the given id or null if none found.
     * @see User
     * @see UserDto
     */
    public UserDto findUserById(Integer id) {
        return userMapper.toDto(userRepository.findById(id).orElse(null));
    }

     /**
     * Updates a given user.
     * 
     * @param userDto to update.
     * @return The updated user; will never be null.
     * @see User
     * @see UserDto
     */
    public UserDto updateUser(UserDto userDto) {
        User user = userMapper.toModel(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    /**
     * Deletes a given user.
     * 
     * @param userDto to delete.
     * @see User
     * @see UserDto
     */
    public void deleteUser(UserDto userDto) {
        userRepository.delete(userMapper.toModel(userDto));
    }

    /**
     * Deletes a user with a given id
     * 
     * @param id of a user.
     * @see User
     * @see UserDto
     */
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

}
