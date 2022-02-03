package com.coyote.big_city_library.rest_server.security;

import java.util.ArrayList;
import java.util.List;

import com.coyote.big_city_library.rest_server.dao.entities.User;
import com.coyote.big_city_library.rest_server.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
        
        User user = userService.findUserEntityByPseudo(pseudo);

        if(user != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
            return new org.springframework.security.core.userdetails.User(user.getPseudo(), user.getPassword(), authorities);

        } else {
            throw new UsernameNotFoundException("Username/Pseudo not found");
        }
    }
    
}
