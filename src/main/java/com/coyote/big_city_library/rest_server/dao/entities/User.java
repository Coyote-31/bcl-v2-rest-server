package com.coyote.big_city_library.rest_server.dao.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter 
@Setter 
public class User {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "pseudo", unique=true, nullable=false, length = 45)
    @Size(min= 3, max = 45)
    private String pseudo;

    @NonNull
    @Column(name = "email", unique=true, nullable=false, length = 90)
    @Size(max = 90)
    @Email
    private String email;

    @NonNull
    @Column(name = "password", columnDefinition = "CHAR(64) NOT NULL")
    @Size(min = 64, max = 64)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Loan> loans;

}
