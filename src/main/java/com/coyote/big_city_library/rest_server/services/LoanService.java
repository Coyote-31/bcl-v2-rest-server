package com.coyote.big_city_library.rest_server.services;

import java.util.List;

import com.coyote.big_city_library.rest_server.dao.entities.Loan;
import com.coyote.big_city_library.rest_server.dao.repositories.LoanRepository;
import com.coyote.big_city_library.rest_server.dto.LoanDto;
import com.coyote.big_city_library.rest_server.dto.LoanMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  Service class handling loans
 * 
 * @see LoanRepository
 */
@Service
public class LoanService {
    
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    protected LoanMapper loanMapper;

    /**
     * Adds a new given loan.
     * 
     * @param loanDto to add.
     * @return The added loan; will never be null.
     * @see Loan
     * @see LoanDto
     */
    public LoanDto addLoan(LoanDto loanDto) {
        Loan loan = loanMapper.toModel(loanDto);
        loan = loanRepository.save(loan);

        return loanMapper.toDto(loan);
    }

    /**
     * Returns a list of all the loans.
     * 
     * @return All the loans.
     * @see Loan
     * @see LoanDto
     */
    public List<LoanDto> findAllLoans() {
        return loanMapper.toDto(loanRepository.findAll());
    }

    /**
     * Returns a loan with a given id.
     * 
     * @param id of a loan.
     * @return The loan with the given id or null if none found.
     * @see Loan
     * @see LoanDto
     */
    public LoanDto findLoanById(Integer id) {
        return loanMapper.toDto(loanRepository.findById(id).orElse(null));
    }

    /**
     * Updates a given loan.
     * 
     * @param loanDto to update.
     * @return The updated loan; will never be null.
     * @see Loan
     * @see LoanDto
     */
    public LoanDto updateLoan(LoanDto loanDto) {
        Loan loan = loanMapper.toModel(loanDto);
        return loanMapper.toDto(loanRepository.save(loan));
    }

    /**
     * Deletes a given loan.
     * 
     * @param loanDto to delete.
     * @see Loan
     * @see LoanDto
     */
    public void deleteLoan(LoanDto loanDto) {
        loanRepository.delete(loanMapper.toModel(loanDto));
    }

    /**
     * Deletes a loan with a given id
     * 
     * @param id of a loan.
     * @see Loan
     * @see LoanDto
     */
    public void deleteLoanById(Integer id) {
        loanRepository.deleteById(id);
    }

}
