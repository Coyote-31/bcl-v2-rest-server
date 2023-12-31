package com.coyote.big_city_library.rest_server_model.dao.entities;

import java.io.Serializable;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@ToString(includeFieldNames = true)
@EqualsAndHashCode
public class User implements Serializable {

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
    @ToString.Exclude
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "VARCHAR(20) NOT NULL DEFAULT 'USER'")
    private Role role;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("user")
    private Set<Loan> loans = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("user")
    private Set<Reservation> reservations = new HashSet<>();

    // Bi-directional synchronization :

    public void addLoan(Loan loan) {
        loans.add(loan);
        loan.setUser(this);
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setUser(this);
    }
}
