package com.coyote.big_city_library.rest_server.dao.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loan")
@Getter @Setter @NoArgsConstructor
public class Loan {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "loan_date", nullable=false)
    private Date loanDate;

    @Column(name = "extend", nullable=false)
    private Boolean extend;

    @Column(name = "return_date")
    private Date returnDate;

    @ManyToOne
    @JoinColumn(name = "exemplary_id")
    private Exemplary exemplary;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Loan (Date loanDate, Boolean extend, Date returnDate) {
        this.loanDate = loanDate;
        this.extend = extend;
        this.returnDate = returnDate;
    }

}
