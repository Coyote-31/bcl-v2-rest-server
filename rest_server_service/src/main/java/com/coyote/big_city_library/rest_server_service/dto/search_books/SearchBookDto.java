package com.coyote.big_city_library.rest_server_service.dto.search_books;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.text.DateFormatter;
import com.coyote.big_city_library.rest_server_service.dto.AuthorDto;
import com.coyote.big_city_library.rest_server_service.dto.ExemplaryDto;
import com.coyote.big_city_library.rest_server_service.dto.LoanDto;
import com.coyote.big_city_library.rest_server_service.dto.PublisherDto;
import com.coyote.big_city_library.rest_server_service.dto.ReservationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Getter
@Setter
public class SearchBookDto {

    private Integer id;

    private String title;

    private LocalDate publicationDate;

    private PublisherDto publisher;

    private Set<AuthorDto> authors;

    private Set<SearchExemplaryDto> exemplaries;

    private String imgURL;

    private Boolean available;

    private Set<ReservationDto> reservations;

    /**
     * Test the availability.
     */
    public Boolean isAvailable() {

        // Only do if not already done
        if (available == null) {

            Boolean availability = false;

            for (SearchExemplaryDto exemplary : exemplaries) {

                Set<SearchLoanDto> loans = exemplary.getLoans();
                Boolean exemplaryAvailable = true;

                // Find if a loan is not return yet
                for (SearchLoanDto loan : loans) {
                    if (loan.getReturnDate() == null) {
                        exemplaryAvailable = false;
                    }
                }

                if (Boolean.TRUE.equals(exemplaryAvailable)) {
                    availability = true;
                }
            }

            // Update book's attribut
            available = availability;

            return available;

            // If already done return the value
        } else {
            return available;
        }

    }

    /**
     * Custom Map to get exemplaries group by libraries.
     */
    public Map<String, Integer> getExemplariesByLibrary() {

        TreeMap<String, Integer> exemplariesByLibrary = new TreeMap<>();

        log.debug("<----- getExemplariesByLibrary ---->");
        log.debug("For -> Book id:{} title:{}", id, title);

        for (SearchExemplaryDto exemplary : exemplaries) {

            Set<SearchLoanDto> loans = exemplary.getLoans();
            Boolean loanAvailable = true;

            log.debug("Exemplary id:{} => Loans size {}", exemplary.getId(), loans.size());

            // Find if a loan is not return yet
            for (SearchLoanDto loan : loans) {
                if (loan.getReturnDate() == null) {
                    loanAvailable = false;
                }

                log.debug("Exemplary id:{} => Loan {} returnDate is {}",
                        exemplary.getId(),
                        loan.getId(),
                        loan.getReturnDate());
            }
            log.debug("Exemplary id:{} => available : {}", exemplary.getId(), loanAvailable);

            // If (no loan or available) and no reservation
            if ((exemplary.getLoans().isEmpty() || Boolean.TRUE.equals(loanAvailable))
                    && exemplary.getReservation() == null) {

                // If library doesn't exist add it
                if (!exemplariesByLibrary.containsKey(exemplary.getLibrary().getName())) {
                    exemplariesByLibrary.put(exemplary.getLibrary().getName(), 1);

                    log.debug("Exemplary id:{} => Add the library id:{}", exemplary.getId(),
                            exemplary.getLibrary().getId());

                    // If library already exist increment the value
                } else {
                    exemplariesByLibrary.replace(exemplary.getLibrary().getName(),
                            exemplariesByLibrary.get(exemplary.getLibrary().getName()) + 1);

                    log.debug("Exemplary id:{} => Increment the library id:{}", exemplary.getId(),
                            exemplary.getLibrary().getId());
                }
            }
        }

        log.debug("<---------- End ---------->");

        return exemplariesByLibrary;
    }

    /**
     * Find the closest date for the next exemplary return
     *
     * @return The formatted date
     */
    public String getClosestDateReturnLoan() {

        // If their is no exemplaries
        if (exemplaries == null || exemplaries.isEmpty()) {
            return "Il n'existe aucun exemplaire";
        }

        // if their is no loan
        boolean isLoanEmpty = true;
        for (SearchExemplaryDto exemplary : exemplaries) {
            if (exemplary.getLoans() != null && !exemplary.getLoans().isEmpty()) {
                isLoanEmpty = false;
                break;
            }
        }

        if (isLoanEmpty) {
            return "Pas encore de prêt en cours";
        }

        // if their is loans but all are returned
        boolean isAllLoansReturned = true;

        for (SearchExemplaryDto exemplary : exemplaries) {

            for (SearchLoanDto searchLoanDto : exemplary.getLoans()) {
                if (searchLoanDto.getReturnDate() == null) {
                    isAllLoansReturned = false;
                    break;
                }
            }

            if (!isAllLoansReturned) {
                break;
            }
        }

        if (isAllLoansReturned) {
            return "Aucun prêt en cours";
        }

        // Find the next closest loan to be returned :

        SearchLoanDto closestReturnLoan = null;

        for (SearchExemplaryDto exemplary : exemplaries) {
            for (SearchLoanDto searchLoanDto : exemplary.getLoans()) {

                // Find a loan not yet returned or continue
                if (searchLoanDto.getReturnDate() != null) {
                    continue;
                }

                // Initial assigment
                if (closestReturnLoan == null) {
                    closestReturnLoan = searchLoanDto;
                }

                // If the loan is extended
                if (Boolean.TRUE.equals(searchLoanDto.getExtend())) {

                    // if the closestReturnLoan is extended
                    if (Boolean.TRUE.equals(closestReturnLoan.getExtend())) {

                        // Compare both expected return dates
                        if (closestReturnLoan.getLoanDate()
                                             .plusWeeks(8)
                                             .isAfter(searchLoanDto.getLoanDate().plusWeeks(8))) {
                            closestReturnLoan = searchLoanDto;
                        }

                        // if the closestReturnLoan is not extended
                    } else {

                        // Compare both expected return dates
                        if (closestReturnLoan.getLoanDate()
                                             .plusWeeks(4)
                                             .isAfter(searchLoanDto.getLoanDate().plusWeeks(8))) {
                            closestReturnLoan = searchLoanDto;
                        }
                    }

                    // If the loan is not extended
                } else {

                    // if the closestReturnLoan is extended
                    if (Boolean.TRUE.equals(closestReturnLoan.getExtend())) {

                        // Compare both expected return dates
                        if (closestReturnLoan.getLoanDate()
                                             .plusWeeks(8)
                                             .isAfter(searchLoanDto.getLoanDate().plusWeeks(4))) {
                            closestReturnLoan = searchLoanDto;
                        }

                        // if the closestReturnLoan is not extended
                    } else {

                        // Compare both expected return dates
                        if (closestReturnLoan.getLoanDate()
                                             .plusWeeks(4)
                                             .isAfter(searchLoanDto.getLoanDate().plusWeeks(4))) {
                            closestReturnLoan = searchLoanDto;
                        }
                    }

                }
            }
        }

        // Exception if closestReturnLoan is null
        if (closestReturnLoan == null) {
            throw new NullPointerException("closestReturnLoan is null");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String closestDateFormatted;

        // Add 4 weeks if not extended
        if (Boolean.FALSE.equals(closestReturnLoan.getExtend())) {

            closestDateFormatted = closestReturnLoan.getLoanDate().plusWeeks(4).format(formatter);

            // Add 8 weeks if extended
        } else {

            closestDateFormatted = closestReturnLoan.getLoanDate().plusWeeks(8).format(formatter);
        }

        return closestDateFormatted;
    }

}
