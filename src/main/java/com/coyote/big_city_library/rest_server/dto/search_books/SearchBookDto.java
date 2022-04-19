package com.coyote.big_city_library.rest_server.dto.search_books;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.coyote.big_city_library.rest_server.dto.AuthorDto;
import com.coyote.big_city_library.rest_server.dto.PublisherDto;

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

    /**
     * Custom Map to get exemplaries group by libraries.
     */
    public Map<String, Integer> getExemplariesByLibrary() {

        TreeMap<String, Integer> exemplariesByLibrary = new TreeMap<>();

        Boolean available = true;

        for (SearchExemplaryDto exemplary : exemplaries) {

            Set<SearchLoanDto> loans = exemplary.getLoans();
            available = true;

            log.debug("Exemplary id:{} => Loans size {}", exemplary.getId(), loans.size());

            // Find if a loan is not return yet
            for (SearchLoanDto loan : loans) {
                if (loan.getReturnDate() == null) {
                    available = false;
                }
                log.debug("Exemplary id:{} => Loan {} returnDate is {}", exemplary.getId(), loan.getId(),
                        loan.getReturnDate());
                log.debug("Exemplary id:{} => available : {}", exemplary.getId(), available);
            }

            // If no loan or available
            if (exemplary.getLoans().isEmpty() || Boolean.TRUE.equals(available)) {

                // If library doesn't exist add it
                if (!exemplariesByLibrary.containsKey(exemplary.getLibrary().getName())) {
                    exemplariesByLibrary.put(exemplary.getLibrary().getName(), 1);

                    // If library already exist increment the value
                } else {
                    exemplariesByLibrary.replace(exemplary.getLibrary().getName(),
                            exemplariesByLibrary.get(exemplary.getLibrary().getName()) + 1);
                }
            }
        }

        return exemplariesByLibrary;
    }

}
