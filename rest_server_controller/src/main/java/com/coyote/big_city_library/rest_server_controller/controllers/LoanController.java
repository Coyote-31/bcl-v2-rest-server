package com.coyote.big_city_library.rest_server_controller.controllers;

import java.time.DateTimeException;
import java.util.List;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server_service.dto.LoanDto;
import com.coyote.big_city_library.rest_server_service.services.LoanService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    LoanService loanService;

    @PostMapping("/add")
    public LoanDto addLoan(@Valid @RequestBody LoanDto loanDto) {
        LoanDto loanSaved = loanService.addLoan(loanDto);
        log.debug("addLoan() => loan with id '{}' added", loanSaved.getId());
        return loanSaved;
    }

    @GetMapping("")
    public List<LoanDto> findAllLoans() {
        List<LoanDto> loans = loanService.findAllLoans();
        log.debug("findAllLoans() => {} loan(s) found", loans.size());
        return loans;
    }

    @GetMapping("/user/{pseudo}")
    public List<LoanDto> findLoansByUserPseudo(@PathVariable String pseudo) {
        List<LoanDto> loans = loanService.findLoansByUserPseudo(pseudo);
        if (loans != null) {
            log.debug("findLoansByUserPseudo() => {} loan(s) with pseudo '{}' found", loans.size(), pseudo);
        } else {
            log.debug("findLoansByUserPseudo() => No loan found with pseudo '{}'", pseudo);
        }
        return loans;
    }

    @GetMapping("/{id}")
    public LoanDto findLoanById(@PathVariable Integer id) {
        LoanDto loanDto = loanService.findLoanById(id);
        if (loanDto != null) {
            log.debug("findLoanById() => loan with id '{}' found", loanDto.getId());
        } else {
            log.debug("findLoanById() => No loan found with id '{}'", id);
        }
        return loanDto;
    }

    @PutMapping("/update")
    public LoanDto updateLoan(@Valid @RequestBody LoanDto loanDto) {
        LoanDto loanUpdated = loanService.updateLoan(loanDto);
        log.debug("updateLoan() => loan with id '{}' updated", loanUpdated.getId());
        return loanUpdated;
    }

    @PutMapping("/extend/{id}")
    public ResponseEntity<Void> extendLoan(
            @PathVariable Integer id,
            @RequestHeader(name = "Authorization") String token) {

        try {
            loanService.extendLoan(id, token);
            return ResponseEntity.ok().build();

        } catch (JwtException e) {
            log.warn("JwtException : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (DateTimeException e) {
            log.warn("DateTimeException : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/delete")
    public void deleteLoan(@Valid @RequestBody LoanDto loanDto) {
        loanService.deleteLoan(loanDto);
        log.debug("deleteLoan() => loan with id '{}' removed", loanDto.getId());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLoanById(@PathVariable Integer id) {
        loanService.deleteLoanById(id);
        log.debug("deleteLoanById() => loan with id '{}' removed", id);
    }

    @GetMapping("/batch/user-reminder")
    public void userLoanReminder() {
        log.debug("Batch Server Task : userLoanReminder() - TimeStamp : " + System.currentTimeMillis() / 1000);
        loanService.userLoanReminder();
    }

}
