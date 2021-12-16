package com.coyote.big_city_library.rest_server.dao.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter 
@Setter 
public class Book {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "title", nullable=false)
    private String title;

    @NonNull
    @Column(name = "publication_date", nullable=false)
    private LocalDate publicationDate;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @NonNull
    @ManyToMany
    @JoinTable(name = "book_author", 
        joinColumns = @JoinColumn(name = "book_id", nullable = false), 
        inverseJoinColumns = @JoinColumn(name = "author_id", nullable = false))
    private Set<Author> authors = new HashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<Exemplary> examplaries = new HashSet<>();

    public Book (String title, LocalDate publicationDate, Publisher publisher, Set<Author> authors) {
        this.title = title;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.authors = authors;
    }

}
