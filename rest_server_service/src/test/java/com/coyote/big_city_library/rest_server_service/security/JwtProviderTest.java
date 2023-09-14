package com.coyote.big_city_library.rest_server_service.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class JwtProviderTest {

    @InjectMocks
    JwtProvider jwtProvider;

    @Mock
    Authentication authentication;

    @Mock
    UserDetails userPrincipal;



    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtProvider, "jwtSecretKey", "secretKey");
        ReflectionTestUtils.setField(jwtProvider, "jwtExpirationMs", 3600000L);
    }


    @Test
    void generateJwtToken() {

        // --- ARRANGE ---
        String username = "Anne";
        String role = "USER";

        when(userPrincipal.getUsername()).thenReturn(username);

        Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(role));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, "", authorities);
        doReturn(authorities).when(userPrincipal).getAuthorities();

        // --- ACT ---
        String token = jwtProvider.generateJwtToken(authentication);

        // --- ASSERT ---
        assertThat(token).isNotNull().isNotEmpty();
    }

    @Test
    void validateToken_returnsTrue() {

        // --- ARRANGE ---
        String token =
                "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbm5lIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE2OTAzMjI0MDAsImV4cCI6MTg5MzQ1NjAwMH0.GSZIsWtOv3hxyB_rXnua-hKnEk4LnzEG3FPiCxFtj6fxTbMmEDG5xn5RGoPYSE9364fqRAZqwL16F77bEGiM0g";

        // --- ACT ---
        boolean valid = jwtProvider.validateToken(token);

        // --- ASSERT ---
        assertThat(valid).isTrue();
    }

    @Test
    void validateToken_givenGeneratedJwtToken_returnsTrue() {

        // --- ARRANGE ---
        String username = "Anne";
        String role = "USER";

        when(userPrincipal.getUsername()).thenReturn(username);

        Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(role));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, "", authorities);
        doReturn(authorities).when(userPrincipal).getAuthorities();

        String token = jwtProvider.generateJwtToken(authentication);

        // --- ACT ---
        boolean valid = jwtProvider.validateToken(token);

        // --- ASSERT ---
        assertThat(valid).isTrue();
    }

    @Test
    void getUsername() {

        // --- ARRANGE ---
        String username = "Anne";
        String token =
                "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbm5lIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE2OTAzMjI0MDAsImV4cCI6MTg5MzQ1NjAwMH0.GSZIsWtOv3hxyB_rXnua-hKnEk4LnzEG3FPiCxFtj6fxTbMmEDG5xn5RGoPYSE9364fqRAZqwL16F77bEGiM0g";

        // --- ACT ---
        String usernameReturned = jwtProvider.getUsername(token);

        // --- ASSERT ---
        assertThat(usernameReturned).isEqualTo(username);
    }

    @Test
    void getRole() {

        // --- ARRANGE ---
        String role = "USER";
        String token =
                "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbm5lIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE2OTAzMjI0MDAsImV4cCI6MTg5MzQ1NjAwMH0.GSZIsWtOv3hxyB_rXnua-hKnEk4LnzEG3FPiCxFtj6fxTbMmEDG5xn5RGoPYSE9364fqRAZqwL16F77bEGiM0g";

        // --- ACT ---
        String roleReturned = jwtProvider.getRole(token);

        // --- ASSERT ---
        assertThat(roleReturned).isEqualTo(role);
    }

}
