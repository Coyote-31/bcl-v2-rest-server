package com.coyote.big_city_library.rest_server.dao.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loan")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter 
@Setter 
public class Loan {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "loan_date", nullable=false)
    private LocalDate loanDate;

    @NonNull
    @Column(name = "extend", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    private Boolean extend = false;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "exemplary_id", nullable = false)
    private Exemplary exemplary;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
