package com.coyote.big_city_library.rest_server.dto.search_books;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.coyote.big_city_library.rest_server.dto.AuthorDto;
import com.coyote.big_city_library.rest_server.dto.PublisherDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

        for (SearchExemplaryDto exemplary : exemplaries) {

            // If library doesn't exist add it
            if (!exemplariesByLibrary.containsKey(exemplary.getLibrary().getName())) {
                exemplariesByLibrary.put(exemplary.getLibrary().getName(), 1);

            // If library already exist increment the value
            } else {
                exemplariesByLibrary.replace(exemplary.getLibrary().getName(), exemplariesByLibrary.get(exemplary.getLibrary().getName()) + 1);
            }
        }

        log.debug("exemplariesByLibrary => Done !");
        return exemplariesByLibrary;
    }

}
