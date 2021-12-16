package com.coyote.big_city_library.rest_server.services;

import java.util.List;
import java.util.Optional;

import com.coyote.big_city_library.rest_server.dao.entities.Loan;
import com.coyote.big_city_library.rest_server.dao.repositories.LoanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  Service class handling loans
 * 
 * @see LoanRepository
 */
@Component
public class LoanService {
    
    @Autowired
    private LoanRepository loanRepository;

    /**
     * Saves the given loan. 
     * Update or Create if the loan doesn't exist.
     * 
     * @param loan
     * @see Loan
     */
    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    /**
     * Returns a list of all the loans.
     * 
     * @return All the loans.
     * @see Loan
     */
    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

    /**
     * Returns a loan with a given id.
     * 
     * @param id of a loan.
     * @return The loan with the given id or Optional#empty() if none found.
     */
    public Optional<Loan> findLoanById(Integer id) {
        return loanRepository.findById(id);
    }

    /**
     * Deletes a given loan.
     * 
     * @param loan to delete.
     * @see Loan
     */
    public void deleteLoan(Loan loan) {
        loanRepository.delete(loan);
    }

    /**
     * Deletes a loan with a given id
     * 
     * @param id of a loan.
     */
    public void deleteLoanById(Integer id) {
        loanRepository.deleteById(id);
    }

}
