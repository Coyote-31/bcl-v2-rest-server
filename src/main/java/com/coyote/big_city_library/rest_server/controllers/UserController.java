package com.coyote.big_city_library.rest_server.controllers;

import com.coyote.big_city_library.rest_server.dao.entities.User;
import com.coyote.big_city_library.rest_server.dao.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    public Iterable<User> getAllUser() {
        
        return userRepository.findAll();
    }
    
}
