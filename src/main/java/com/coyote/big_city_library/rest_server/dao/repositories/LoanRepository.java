package com.coyote.big_city_library.rest_server.dao.repositories;

import java.time.LocalDate;
import java.util.List;

import com.coyote.big_city_library.rest_server.dao.entities.Loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *  Repository class handling loans.
 *
 * @see Loan
 */
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findByUserPseudo(String pseudo);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Loan SET Loan.extend = true WHERE Loan.id = :id", nativeQuery=true)
    void extendLoan(@Param("id") Integer id);

    @Query("FROM Loan L "
         + "WHERE L.returnDate IS NULL "
         + "AND ((L.extend = FALSE AND L.loanDate < :oneMonth) "
         + "OR (L.extend = TRUE AND L.loanDate < :twoMonth))")
    List<Loan> findOverdue(@Param("oneMonth") LocalDate oneMonth, @Param("twoMonth") LocalDate twoMonth);
}
