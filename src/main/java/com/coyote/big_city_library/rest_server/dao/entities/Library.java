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
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "library")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter 
public class Library {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "name", nullable=false)
    private String name;

    @NonNull
    @Column(name = "address", nullable=false)
    private String address;

    @NonNull
    @Column(name = "phone", columnDefinition = "CHAR(10) NOT NULL")
    @Size(min = 10, max = 10)
    private String phone;

    @OneToMany(mappedBy = "library")
    private Set<Exemplary> examplaries = new HashSet<>();

    public Library (String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // Bi-directional sync

    public void addExemplary(Exemplary exemplary) {
        examplaries.add(exemplary);
        exemplary.setLibrary(this);
    }

    public void removeExemplary(Exemplary exemplary) {
        examplaries.remove(exemplary);
        exemplary.setLibrary(null);
    }

}
