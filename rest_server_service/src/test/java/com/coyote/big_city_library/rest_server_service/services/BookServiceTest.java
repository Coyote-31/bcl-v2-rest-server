package com.coyote.big_city_library.rest_server_service.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import com.coyote.big_city_library.rest_server_model.dao.entities.Book;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.BookRepository;
import com.coyote.big_city_library.rest_server_service.dto.search_books.SearchBookDto;
import com.coyote.big_city_library.rest_server_service.dto.search_books.SearchBookMapper;
import com.coyote.big_city_library.rest_server_service.dto.search_books.SearchBookMapperImpl;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @Spy
    SearchBookMapper searchBookMapper = new SearchBookMapperImpl();

    @Test
    void searchBooks() {

        // --- ARRANGE ---
        String title = "Title";
        String author = "Author";
        String publisher = "Publisher";

        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setTitle(title);
        books.add(book);
        when(bookRepository.search(title, author, publisher)).thenReturn(books);

        // --- ACT ---
        List<SearchBookDto> searchBookDtos = bookService.searchBooks(title, author, publisher);

        // --- ASSERT ---
        verify(searchBookMapper, times(1)).toDto(books);
        assertThat(searchBookDtos).isNotNull().isNotEmpty().hasSize(1);
        assertThat(searchBookDtos.get(0).getTitle()).isEqualTo(title);
    }

}
