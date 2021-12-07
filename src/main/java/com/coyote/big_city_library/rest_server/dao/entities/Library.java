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
@Table(name = "library")
@Getter @Setter @NoArgsConstructor
public class Library {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "address", nullable=false)
    private String address;

    @Column(name = "phone", nullable=false)
    private String phone;

    @OneToMany
    private Set<Exemplary> examplaries = new HashSet<>();

    public Library (String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

}
