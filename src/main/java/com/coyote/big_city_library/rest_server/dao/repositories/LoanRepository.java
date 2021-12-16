package com.coyote.big_city_library.rest_server.dao.repositories;

import com.coyote.big_city_library.rest_server.dao.entities.Loan;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Repository class handling loans.
 * 
 * @see Loan
 */
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    
}
