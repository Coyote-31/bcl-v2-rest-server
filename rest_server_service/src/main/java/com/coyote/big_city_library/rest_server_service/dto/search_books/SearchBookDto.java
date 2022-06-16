package com.coyote.big_city_library.rest_server_service.dto.search_books;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.coyote.big_city_library.rest_server_service.dto.AuthorDto;
import com.coyote.big_city_library.rest_server_service.dto.PublisherDto;

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

    private List<AuthorDto> authors;

    private List<SearchExemplaryDto> exemplaries;

    private String imgURL;

    private Boolean available;

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

            // If no loan or available
            if (exemplary.getLoans().isEmpty() || Boolean.TRUE.equals(loanAvailable)) {

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

}
