package com.coyote.big_city_library.rest_server_service.security;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class JwtProviderIT {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    void generateJwtToken() {

        // --- ARRANGE ---
        String username = "Anne";
        String password = "Anne";

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password));

        // --- ACT ---
        String token = jwtProvider.generateJwtToken(authentication);

        // --- ASSERT ---
        assertThat(token).isNotNull().isNotEmpty();
    }

    @Test
    void validateToken_givenGeneratedJwtToken_returnsTrue() {

        // --- ARRANGE ---
        String username = "Anne";
        String password = "Anne";

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password));

        String token = jwtProvider.generateJwtToken(authentication);
        String bearerToken = "Bearer " + token;

        // --- ACT ---
        boolean valid = jwtProvider.validateToken(bearerToken);

        // --- ASSERT ---
        assertThat(valid).isTrue();
    }

}
