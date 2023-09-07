package com.coyote.big_city_library.rest_server_service.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.coyote.big_city_library.rest_server_service.dto.LoanDto;
import com.coyote.big_city_library.rest_server_service.exceptions.LoanOverdueException;
import com.coyote.big_city_library.rest_server_service.exceptions.UserAccessDeniedException;

@SpringBootTest
@Transactional
public class LoanServiceIT {

    @Autowired
    LoanService loanService;

    @Test
    void findLoansById() {

        // --- ARRANGE ---
        Integer loanId = 2;

        // --- ACT ---
        LoanDto loanDto = loanService.findLoanById(loanId);

        // --- ASSERT ---
        assertThat(loanDto).isNotNull();
        assertThat(loanDto.getId()).isEqualTo(loanId);
    }

    @Test
    void findLoansByUserPseudo() {

        // --- ARRANGE ---
        String pseudo = "Anne";

        // --- ACT ---
        List<LoanDto> loanDtos = loanService.findLoansByUserPseudo(pseudo);

        // --- ASSERT ---
        assertThat(loanDtos).isNotNull().isNotEmpty().hasSizeGreaterThan(1);
    }

    @Test
    void updateLoan() {

        // --- ARRANGE ---
        Integer loanId = 2;
        LoanDto loanDto = loanService.findLoanById(loanId);
        LocalDate loanDate = LocalDate.now();
        loanDto.setLoanDate(loanDate);

        // --- ACT ---
        LoanDto loanDtoReturned = loanService.updateLoan(loanDto);

        // --- ASSERT ---
        assertThat(loanDtoReturned).isNotNull();
        assertThat(loanDtoReturned.getLoanDate()).isEqualTo(loanDate);
    }

    @Test
    void extendLoan() throws UserAccessDeniedException, LoanOverdueException {

        // --- ARRANGE ---
        String tokenAnne =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbm5lIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE2OTAzMjI0MDAsImV4cCI6MTg5MzQ1NjAwMH0.GSZIsWtOv3hxyB_rXnua-hKnEk4LnzEG3FPiCxFtj6fxTbMmEDG5xn5RGoPYSE9364fqRAZqwL16F77bEGiM0g";


        Integer loanId = 2;
        LoanDto loanDto = loanService.findLoanById(loanId);
        LocalDate loanDate = LocalDate.now().minusWeeks(2);
        loanDto.setLoanDate(loanDate);
        loanService.updateLoan(loanDto);

        // --- ACT ---
        loanService.extendLoan(loanId, tokenAnne);
        LoanDto loanDtoExtented = loanService.findLoanById(loanId);

        // --- ASSERT ---
        assertThat(loanDtoExtented).isNotNull();
        assertThat(loanDtoExtented.getId()).isEqualTo(2);
        assertThat(loanDtoExtented.getLoanDate()).isEqualTo(loanDate);
        assertThat(loanDtoExtented.getExtend()).isTrue();
    }

    @Test
    void extendLoan_throwsUserAccessDeniedException() throws UserAccessDeniedException, LoanOverdueException {

        // --- ARRANGE ---
        String tokenAnne =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbm5lIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE2OTAzMjI0MDAsImV4cCI6MTg5MzQ1NjAwMH0.GSZIsWtOv3hxyB_rXnua-hKnEk4LnzEG3FPiCxFtj6fxTbMmEDG5xn5RGoPYSE9364fqRAZqwL16F77bEGiM0g";


        Integer loanId = 4;
        LoanDto loanDto = loanService.findLoanById(loanId);
        LocalDate loanDate = LocalDate.now().minusWeeks(2);
        loanDto.setLoanDate(loanDate);
        loanService.updateLoan(loanDto);

        // --- ACT/ASSERT ---
        assertThatThrownBy(() -> {
            loanService.extendLoan(loanId, tokenAnne);
        }).isInstanceOf(UserAccessDeniedException.class)
          .hasMessage("Users can only extends their own loans");
    }

    @Test
    void extendLoan_throwsLoanOverdueException() throws UserAccessDeniedException, LoanOverdueException {

        // --- ARRANGE ---
        String tokenAnne =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbm5lIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE2OTAzMjI0MDAsImV4cCI6MTg5MzQ1NjAwMH0.GSZIsWtOv3hxyB_rXnua-hKnEk4LnzEG3FPiCxFtj6fxTbMmEDG5xn5RGoPYSE9364fqRAZqwL16F77bEGiM0g";


        Integer loanId = 2;
        LoanDto loanDto = loanService.findLoanById(loanId);
        LocalDate loanDate = LocalDate.now().minusWeeks(4).minusDays(1);
        loanDto.setLoanDate(loanDate);
        loanService.updateLoan(loanDto);

        // --- ACT/ASSERT ---
        assertThatThrownBy(() -> {
            loanService.extendLoan(loanId, tokenAnne);
        }).isInstanceOf(LoanOverdueException.class)
          .hasMessage("Loans can't be extended after 4 weeks");
    }

}
