package com.coyote.big_city_library.rest_server_model.dao.entities;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "loan")
@Data
@NoArgsConstructor
@ToString(includeFieldNames = true)
@EqualsAndHashCode
public class Loan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "extend", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    private Boolean extend = false;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "exemplary_id", nullable = false)
    @JsonIgnoreProperties("loans")
    private Exemplary exemplary;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("loans")
    private User user;

    // Bi-directional synchronization :

    public void setExemplary(Exemplary exemplary) {
        this.exemplary = exemplary;
        exemplary.addLoan(this);
    }

    public void setUser(User user) {
        this.user = user;
        user.addLoan(this);
    }

}
