package com.coyote.big_city_library.rest_server_model.dao.entities;

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
@Table(name = "publisher")
@NoArgsConstructor
@Getter
@Setter
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "publisher")
    private Set<Book> books;

    // Bi-directional synchronization :

    public void addBook(Book book) {
        if (books == null) {
            books = new HashSet<>();
        }
        books.add(book);
        book.setPublisher(this);
    }

}
