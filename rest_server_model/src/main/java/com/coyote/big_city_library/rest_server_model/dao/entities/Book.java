package com.coyote.big_city_library.rest_server_model.dao.entities;

import java.io.Serializable;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@ToString(includeFieldNames = true)
@EqualsAndHashCode
public class Book implements Serializable {

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

    @Column(name = "img_url")
    private String imgURL;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "author_id", nullable = false))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("books")
    private Set<Author> authors;

    @OneToMany(mappedBy = "book")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("book")
    private Set<Exemplary> exemplaries;

    @OneToMany(mappedBy = "book")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("book")
    private Set<Reservation> reservations;

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

    public void addReservation(Reservation reservation) {
        if (reservations == null) {
            reservations = new HashSet<>();
        }
        reservations.add(reservation);
        reservation.setBook(this);
    }
}
