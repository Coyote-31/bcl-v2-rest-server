package com.coyote.big_city_library.rest_server.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server.dto.UserDto;
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
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        UserDto userSaved = userService.addUser(userDto);
        log.debug("addUser() => user with pseudo '{}' added", userSaved.getPseudo());
        return userSaved;
    }

    @GetMapping("/all")
    public List<UserDto> findAllUsers() {
        List<UserDto> users = userService.findAllUsers();
        log.debug("findAllUsers() => {} user(s) found", users.size());
        return users;
    }
    
    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable Integer id) {
        Optional<UserDto> optionalUser = userService.findUserById(id);
        UserDto user;
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
    public UserDto updateUser(@Valid @RequestBody UserDto userDto) {
        UserDto userUpdated = userService.updateUser(userDto);
        log.debug("updateUser() => user with pseudo '{}' updated", userUpdated.getPseudo());
        return userUpdated;
    }

    @DeleteMapping("/delete")
    public void deleteUser(@Valid @RequestBody UserDto userDto) {
        userService.deleteUser(userDto);
        log.debug("deleteUser() => user with pseudo '{}' removed", userDto.getPseudo());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        log.debug("deleteUserById() => user with id '{}' removed", id);
    }

}
