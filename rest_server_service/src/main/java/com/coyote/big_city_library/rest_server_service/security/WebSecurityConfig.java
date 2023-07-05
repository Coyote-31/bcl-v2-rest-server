package com.coyote.big_city_library.rest_server_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class WebSecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder,
            CustomUserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(userDetailsService)
                   .passwordEncoder(passwordEncoder)
                   .and()
                   .build();
    }

    // Roles declaration
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_BATCH = "BATCH";
    private static final String ROLE_EMPLOYEE = "EMPLOYEE";
    private static final String ROLE_USER = "USER";

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http.cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(
                    sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(
                    ahr -> ahr.mvcMatchers("/auth/login", "/books/search")
                              .permitAll()
                              .mvcMatchers("/libraries", "/loans/user/**", "/loans/extend/**")
                              .hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                              .mvcMatchers("/loans/add", "/loans/add/partial", "/loans/update")
                              .hasAnyAuthority(ROLE_EMPLOYEE, ROLE_ADMIN)
                              .mvcMatchers("/loans/batch/**")
                              .hasAnyAuthority(ROLE_BATCH, ROLE_ADMIN)
                              .anyRequest()
                              .hasAuthority(ROLE_ADMIN))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
