package com.coyote.big_city_library.rest_server.dao.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "exemplary")
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@Getter 
@Setter 
public class Exemplary {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "exemplary")
    private Set<Loan> loans = new HashSet<>();

    public Exemplary(Library library, Book book) {
        this.library = library;
        this.book = book;
    }

}
