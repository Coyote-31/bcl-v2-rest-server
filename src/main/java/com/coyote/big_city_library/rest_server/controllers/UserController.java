package com.coyote.big_city_library.rest_server.controllers;

import java.util.List;

import com.coyote.big_city_library.rest_server.dao.entities.User;
import com.coyote.big_city_library.rest_server.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<User> findAllUsers() {
        List<User> users = userService.findAllUsers();
        log.debug("findAllUsers : {} user(s) found.", users.size());
        return users;
    }
    
}
