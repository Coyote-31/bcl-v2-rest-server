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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "publisher")
@Data
@NoArgsConstructor
@ToString(includeFieldNames = true)
@EqualsAndHashCode
public class Publisher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "publisher")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("publisher")
    private Set<Book> books = new HashSet<>();

    // Bi-directional synchronization :

    public void addBook(Book book) {
        books.add(book);
        book.setPublisher(this);
    }

}
