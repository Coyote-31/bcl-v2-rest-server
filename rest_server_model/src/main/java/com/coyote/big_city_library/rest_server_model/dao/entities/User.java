package com.coyote.big_city_library.rest_server_model.dao.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.coyote.big_city_library.rest_server_model.dao.attributes.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "pseudo", unique = true, nullable = false, length = 45)
    @Size(min = 3, max = 45)
    private String pseudo;

    @Column(name = "email", unique = true, nullable = false, length = 90)
    @Size(max = 90)
    @Email
    private String email;

    @Column(name = "password", columnDefinition = "CHAR(60) BINARY NOT NULL")
    @Size(min = 59, max = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "VARCHAR(20) NOT NULL DEFAULT 'USER'")
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<Loan> loans;

    // Bi-directional synchronization :

    public void addLoan(Loan loan) {
        if (loans == null) {
            loans = new HashSet<>();
        }
        loans.add(loan);
        loan.setUser(this);
    }

}
