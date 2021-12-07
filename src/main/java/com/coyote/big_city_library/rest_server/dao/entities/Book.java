package com.coyote.big_city_library.rest_server.dao.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book")
@Getter @Setter @NoArgsConstructor
public class Book {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "title", nullable=false)
    private String title;

    @Column(name = "publication_date", nullable=false)
    private Date publicationDate;

    @ManyToOne
    private Publisher publisher;

    @ManyToMany
    private Set<Author> authors = new HashSet<>();

    @OneToMany
    private Set<Exemplary> examplaries = new HashSet<>();

    public Book (String title, Date publicationDate) {
        this.title = title;
        this.publicationDate = publicationDate;
    }

}
