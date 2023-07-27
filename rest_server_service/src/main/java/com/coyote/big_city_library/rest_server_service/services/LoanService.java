package com.coyote.big_city_library.rest_server_service.services;

import java.time.LocalDate;
import java.util.List;

import com.coyote.big_city_library.rest_server_model.dao.entities.Loan;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.LoanRepository;
import com.coyote.big_city_library.rest_server_service.dto.LoanDto;
import com.coyote.big_city_library.rest_server_service.dto.LoanMapper;
import com.coyote.big_city_library.rest_server_service.security.JwtProvider;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class handling loans
 *
 * @see LoanRepository
 */
@Slf4j
@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    protected LoanMapper loanMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    protected JwtProvider jwtProvider;

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
     * Returns all loans owned by a given user's pseudo
     *
     * @param pseudo of the user
     * @return The loans list or null if none found.
     */
    public List<LoanDto> findLoansByUserPseudo(String pseudo) {
        return loanMapper.toDto(loanRepository.findByUserPseudoOrderByLoanDateAsc(pseudo));
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
     * Updates Loan's extend attribut to true
     * by given Loan's id.
     *
     * @param id
     * @param token
     * @see Loan
     */
    public void extendLoan(Integer id, String token) {

        // Exctract user from JWT
        String tokenUser = jwtProvider.getUsername(token);
        log.debug("User name : {}", tokenUser);

        // Get the loan
        Loan loan = loanRepository.findById(id).orElseThrow();

        // Verify user from JWT is the loan user
        if (!tokenUser.equals(loan.getUser().getPseudo())) {
            throw new JwtException("Jwt user is different from loan user");
        }

        loanRepository.extendLoan(id);
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

    /**
     * Send mail to users with outdated loans not returned
     */
    public void userLoanReminder() {

        // Dates
        LocalDate today = LocalDate.now();
        LocalDate oneMonthEarlier = today.minusMonths(1);
        LocalDate twoMonthsEarlier = today.minusMonths(2);

        // Find loans overdue
        List<Loan> loans = loanRepository.findOverdue(oneMonthEarlier, twoMonthsEarlier);
        log.debug("Loans overdue : {}", loans.size());

        // Check date & extend and mail if necessary
        for (Loan loan : loans) {

            // If loan is not extended & loan date is farthest than 1 months -> mail
            if (Boolean.FALSE.equals(loan.getExtend()) && loan.getLoanDate().isBefore(oneMonthEarlier)) {

                mailService.sendUserLoanReminder(loan);

                String pseudo = loan.getUser().getPseudo();
                String bookTitle = loan.getExemplary().getBook().getTitle();
                log.debug("Mail send for more than 1 month delay (Not Extended). To '{}' for book '{}'.",
                        pseudo,
                        bookTitle);
            }

            // If loan is extend & loan date is farthest than 2 months -> mail
            if (Boolean.TRUE.equals(loan.getExtend()) && loan.getLoanDate().isBefore(twoMonthsEarlier)) {

                mailService.sendUserLoanReminder(loan);

                String pseudo = loan.getUser().getPseudo();
                String bookTitle = loan.getExemplary().getBook().getTitle();
                log.debug("Mail send for more than 2 months delay (Extended). To '{}' for book '{}'.",
                        pseudo,
                        bookTitle);

            }
        }
    }

}
