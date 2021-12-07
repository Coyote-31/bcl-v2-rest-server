package com.coyote.big_city_library.rest_server.dao.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "user")
@Getter @Setter @NoArgsConstructor
public class User {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "pseudo", unique=true, nullable=false)
    private String pseudo;

    @Column(name = "email", unique=true, nullable=false)
    private String email;

    @Column(name = "password", nullable=false)
    private String password;

    @OneToMany
    private Set<Loan> loans = new HashSet<>();

    public User (String pseudo, String email, String password) {
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
    }

}
