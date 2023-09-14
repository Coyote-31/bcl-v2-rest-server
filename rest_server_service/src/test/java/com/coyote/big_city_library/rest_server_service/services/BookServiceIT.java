package com.coyote.big_city_library.rest_server_service.services;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.coyote.big_city_library.rest_server_service.dto.search_books.SearchBookDto;

@SpringBootTest
@Transactional
public class BookServiceIT {

    @Autowired
    BookService bookService;

    @Test
    void searchBooks() {

        // --- ARRANGE ---
        String bookTitle = "Coyote céleste";
        String authorName = "Vil Coyote";
        String publisherName = "Coyote Corp.";

        // --- ACT ---
        List<SearchBookDto> books = bookService.searchBooks(bookTitle, authorName, publisherName);

        // --- ASSERT ---
        assertThat(books).isNotNull().isNotEmpty().hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo(bookTitle);
    }

}
