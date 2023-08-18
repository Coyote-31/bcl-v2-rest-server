package com.coyote.big_city_library.rest_server_model.dao.entities;

import java.io.Serializable;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "library")
@Data
@NoArgsConstructor
@ToString(includeFieldNames = true)
@EqualsAndHashCode
public class Library implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", columnDefinition = "CHAR(10) NOT NULL")
    @Size(min = 10, max = 10)
    private String phone;

    @OneToMany(mappedBy = "library")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("library")
    private Set<Exemplary> exemplaries = new HashSet<>();

    // Bi-directional synchronization :

    public void addExemplary(Exemplary exemplary) {
        exemplaries.add(exemplary);
        exemplary.setLibrary(this);
    }

}
