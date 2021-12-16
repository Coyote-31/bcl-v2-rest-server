package com.coyote.big_city_library.rest_server.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server.dao.entities.Loan;
import com.coyote.big_city_library.rest_server.services.LoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    LoanService loanService;

    @PutMapping("/save")
    public Loan saveLoan(@Valid @RequestBody Loan loan) {
        Loan loanSaved = loanService.saveLoan(loan);
        log.debug("saveLoan() => loan with id '{}' saved", loanSaved.getId());
        return loanSaved;
    }

    @GetMapping("/all")
    public List<Loan> findAllLoans() {
        List<Loan> loans = loanService.findAllLoans();
        log.debug("findAllLoans() => {} loan(s) found", loans.size());
        return loans;
    }
    
    @GetMapping("/{id}")
    public Loan findLoanById(@PathVariable Integer id) {
        Optional<Loan> optionalLoan = loanService.findLoanById(id);
        Loan loan;
        if (optionalLoan.isPresent()) {
            loan = optionalLoan.get();
            log.debug("findLoanById() => loan with id '{}' found", loan.getId());
        } else {
            loan = null;
            log.debug("findLoanById() => No loan found with id '{}'", id);
        }
        return loan;
    }

    @DeleteMapping("/delete")
    public void deleteLoan(@Valid @RequestBody Loan loan) {
        loanService.deleteLoan(loan);
        log.debug("deleteLoan() => loan with id '{}' removed", loan.getId());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLoanById(@PathVariable Integer id) {
        loanService.deleteLoanById(id);
        log.debug("deleteLoanById() => loan with id '{}' removed", id);
    }

}
