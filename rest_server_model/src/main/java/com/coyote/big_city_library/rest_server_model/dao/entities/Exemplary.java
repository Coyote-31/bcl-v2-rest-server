package com.coyote.big_city_library.rest_server_model.dao.entities;

import java.io.Serializable;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "exemplary")
@Data
@NoArgsConstructor
@ToString(includeFieldNames = true)
@EqualsAndHashCode
public class Exemplary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "library_id", nullable = false)
    @JsonIgnoreProperties("exemplaries")
    private Library library;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties("exemplaries")
    private Book book;

    @OneToMany(mappedBy = "exemplary")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("exemplary")
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
