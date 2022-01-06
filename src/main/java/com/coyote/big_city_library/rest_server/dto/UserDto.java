package com.coyote.big_city_library.rest_server.dto;

import java.util.HashSet;
import java.util.Set;

import com.coyote.big_city_library.rest_server.dao.entities.Loan;

import lombok.Getter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter 
@Setter 
public class UserDto {

    private Integer id;

    private String pseudo;

    private String email;

    private String password;

    private Set<Loan> loans = new HashSet<>();

    public UserDto (Integer id, String pseudo, String email, String password) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
    }

    public UserDto (String pseudo, String email, String password) {
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
    }
}
