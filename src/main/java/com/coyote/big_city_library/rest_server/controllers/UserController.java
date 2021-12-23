package com.coyote.big_city_library.rest_server.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server.dao.entities.User;
import com.coyote.big_city_library.rest_server.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public User addUser(@Valid @RequestBody User user) {
        User userSaved = userService.addUser(user);
        log.debug("addUser() => user with pseudo '{}' added", userSaved.getPseudo());
        return userSaved;
    }

    @GetMapping("/all")
    public List<User> findAllUsers() {
        List<User> users = userService.findAllUsers();
        log.debug("findAllUsers() => {} user(s) found", users.size());
        return users;
    }
    
    @GetMapping("/{id}")
    public User findUserById(@PathVariable Integer id) {
        Optional<User> optionalUser = userService.findUserById(id);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            log.debug("findUserById() => user with pseudo '{}' found", user.getPseudo());
        } else {
            user = null;
            log.debug("findUserById() => No user found with id '{}'", id);
        }
        return user;
    }

    @PutMapping("/update")
    public User updateUser(@Valid @RequestBody User user) {
        User userUpdated = userService.updateUser(user);
        log.debug("updateUser() => user with pseudo '{}' updated", userUpdated.getPseudo());
        return userUpdated;
    }

    @DeleteMapping("/delete")
    public void deleteUser(@Valid @RequestBody User user) {
        userService.deleteUser(user);
        log.debug("deleteUser() => user with pseudo '{}' removed", user.getPseudo());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        log.debug("deleteUserById() => user with id '{}' removed", id);
    }

}
