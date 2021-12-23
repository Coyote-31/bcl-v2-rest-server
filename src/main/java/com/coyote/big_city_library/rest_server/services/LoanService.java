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
     * Adds a new given loan.
     * 
     * @param loan to add.
     * @return The added loan; will never be null.
     * @see Loan
     */
    public Loan addLoan(Loan loan) {
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
     * Updates a given loan.
     * 
     * @param loan to update.
     * @return The updated loan; will never be null.
     * @see Loan
     */
    public Loan updateLoan(Loan loan) {
        return loanRepository.save(loan);
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