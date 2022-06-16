package com.coyote.big_city_library.rest_server_controller.controllers;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server_service.security.AuthRequest;
import com.coyote.big_city_library.rest_server_service.security.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@Valid @RequestBody AuthRequest authRequest) {

        try {
            Authentication authentication;

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()));

            return jwtProvider.generateJwtToken(authentication);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid Pseudo/password");
        }
    }

}
