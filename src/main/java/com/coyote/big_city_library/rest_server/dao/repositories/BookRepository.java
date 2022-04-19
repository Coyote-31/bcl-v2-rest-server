package com.coyote.big_city_library.rest_server.dao.repositories;

import java.util.List;

import com.coyote.big_city_library.rest_server.dao.entities.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class handling books.
 *
 * @see Book
 */
public interface BookRepository extends JpaRepository<Book, Integer> {

        @Transactional
        @Query("SELECT DISTINCT B FROM Book B "
                        + "JOIN B.authors A "
                        + "JOIN B.exemplaries E "
                        + "WHERE (:authorName IS NULL OR :authorName = '' OR LOWER(A.name) LIKE LOWER(CONCAT('%', :authorName, '%'))) "
                        + "AND (:bookTitle IS NULL OR :bookTitle = '' OR  LOWER(B.title) LIKE LOWER(CONCAT('%', :bookTitle, '%'))) "
                        + "AND (:publisherName IS NULL OR :publisherName = '' OR LOWER(B.publisher.name) LIKE LOWER(CONCAT('%', :publisherName, '%'))) ")
        List<Book> search(
                        @Param("bookTitle") String bookTitle,
                        @Param("authorName") String authorName,
                        @Param("publisherName") String publisherName);

}
