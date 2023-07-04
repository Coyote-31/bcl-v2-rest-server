package com.coyote.big_city_library.rest_server_model.dao.entities;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exemplary")
@NoArgsConstructor
@Getter
@Setter
public class Exemplary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "exemplary")
    private Set<Loan> loans;

    // Bi-directional synchronization :

    public void setLibrary(Library library) {
        this.library = library;
        library.addExemplary(this);
    }

    public void setBook(Book book) {
        this.book = book;
        book.addExemplary(this);
    }

    public void addLoan(Loan loan) {
        if (loans == null) {
            loans = new HashSet<>();
        }
        loans.add(loan);
        loan.setExemplary(this);
    }

}
