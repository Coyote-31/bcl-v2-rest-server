package com.coyote.big_city_library.rest_server_model.dao.entities;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book")
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "author_id", nullable = false))
    private Set<Author> authors;

    @OneToMany(mappedBy = "book")
    private Set<Exemplary> exemplaries;

    @Column(name = "img_url")
    private String imgURL;

    // Bi-directional synchronization :

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
        publisher.addBook(this);
    }

    public void addAuthor(Author author) {
        if (authors == null) {
            authors = new HashSet<>();
        }
        authors.add(author);
        author.addBook(this);
    }

    public void addExemplary(Exemplary exemplary) {
        if (exemplaries == null) {
            exemplaries = new HashSet<>();
        }
        exemplaries.add(exemplary);
        exemplary.setBook(this);
    }

}
