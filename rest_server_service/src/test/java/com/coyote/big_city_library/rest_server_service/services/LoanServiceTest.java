package com.coyote.big_city_library.rest_server_service.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import com.coyote.big_city_library.rest_server_model.dao.attributes.Role;
import com.coyote.big_city_library.rest_server_model.dao.entities.Author;
import com.coyote.big_city_library.rest_server_model.dao.entities.Book;
import com.coyote.big_city_library.rest_server_model.dao.entities.Exemplary;
import com.coyote.big_city_library.rest_server_model.dao.entities.Library;
import com.coyote.big_city_library.rest_server_model.dao.entities.Loan;
import com.coyote.big_city_library.rest_server_model.dao.entities.Publisher;
import com.coyote.big_city_library.rest_server_model.dao.entities.User;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.LoanRepository;
import com.coyote.big_city_library.rest_server_service.dto.LoanDto;
import com.coyote.big_city_library.rest_server_service.dto.LoanMapper;
import com.coyote.big_city_library.rest_server_service.dto.LoanMapperImpl;
import com.coyote.big_city_library.rest_server_service.exceptions.LoanOverdueException;
import com.coyote.big_city_library.rest_server_service.exceptions.UserAccessDeniedException;
import com.coyote.big_city_library.rest_server_service.security.JwtProvider;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @InjectMocks
    LoanService loanService;

    @Mock
    LoanRepository loanRepository;

    @Mock
    JwtProvider jwtProvider;

    @Mock
    MailService mailService;

    @Spy
    LoanMapper loanMapper = new LoanMapperImpl();

    @Test
    void findLoansByUserPseudo() {

        // --- ARRANGE ---
        String pseudo = "Pseudo";

        // exemplary
        Exemplary exemplary = new Exemplary();

        // exemplary-library
        String libraryName = "LibraryName";
        String libraryAddress = "LibraryAddress";
        String libraryPhone = "0505050505";
        Library library = new Library();
        library.setName(libraryName);
        library.setAddress(libraryAddress);
        library.setPhone(libraryPhone);
        exemplary.setLibrary(library);

        // exemplary-book
        Book book = new Book();
        String bookTitle = "BookTitle";
        LocalDate bookPublicationDate = LocalDate.now();
        Publisher bookPublisher = new Publisher();
        bookPublisher.setName("BookPublisher");
        Set<Author> bookAuthors = new HashSet<>();
        Author bookAuthor = new Author();
        bookAuthor.setName("BookAuthor");
        bookAuthors.add(bookAuthor);
        book.setTitle(bookTitle);
        book.setPublicationDate(bookPublicationDate);
        book.setPublisher(bookPublisher);
        book.setAuthors(bookAuthors);
        exemplary.setBook(book);

        // user
        User user = new User();
        user.setPseudo(pseudo);
        String userEmail = "User@Email.com";
        user.setEmail(userEmail);
        Role userRole = Role.USER;
        user.setRole(userRole);

        // loan
        Loan loan = new Loan();
        Integer loanId = 1;
        loan.setId(loanId);
        LocalDate loanDate = LocalDate.now();
        loan.setLoanDate(loanDate);

        loan.setExemplary(exemplary);
        loan.setUser(user);

        // loans
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);

        when(loanRepository.findByUserPseudoOrderByLoanDateAsc(pseudo)).thenReturn(loans);

        // --- ACT ---
        List<LoanDto> loanDtos = loanService.findLoansByUserPseudo(pseudo);

        // --- ASSERT ---
        verify(loanMapper, times(1)).toDto(loans);
        assertThat(loanDtos).isNotNull().isNotEmpty().hasSize(1);
        assertThat(loanDtos.get(0).getUser().getPseudo()).isEqualTo(pseudo);
    }

    @Test
    void extendLoan() throws UserAccessDeniedException, LoanOverdueException {

        // --- ARRANGE ---
        String token = "Token";
        Integer loanId = 1;

        // jwt
        String pseudo = "Pseudo";
        when(jwtProvider.getUsername(token)).thenReturn(pseudo);

        // exemplary
        Exemplary exemplary = new Exemplary();

        // exemplary-library
        String libraryName = "LibraryName";
        String libraryAddress = "LibraryAddress";
        String libraryPhone = "0505050505";
        Library library = new Library();
        library.setName(libraryName);
        library.setAddress(libraryAddress);
        library.setPhone(libraryPhone);
        exemplary.setLibrary(library);

        // exemplary-book
        Book book = new Book();
        String bookTitle = "BookTitle";
        LocalDate bookPublicationDate = LocalDate.now();
        Publisher bookPublisher = new Publisher();
        bookPublisher.setName("BookPublisher");
        Set<Author> bookAuthors = new HashSet<>();
        Author bookAuthor = new Author();
        bookAuthor.setName("BookAuthor");
        bookAuthors.add(bookAuthor);
        book.setTitle(bookTitle);
        book.setPublicationDate(bookPublicationDate);
        book.setPublisher(bookPublisher);
        book.setAuthors(bookAuthors);
        exemplary.setBook(book);

        // user
        User user = new User();
        user.setPseudo(pseudo);
        String userEmail = "User@Email.com";
        user.setEmail(userEmail);
        Role userRole = Role.USER;
        user.setRole(userRole);

        // loan
        Loan loan = new Loan();
        loan.setId(loanId);
        LocalDate loanDate = LocalDate.now().minusWeeks(2);
        loan.setLoanDate(loanDate);

        loan.setExemplary(exemplary);
        loan.setUser(user);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        // --- ACT ---
        loanService.extendLoan(loanId, token);

        // --- ASSERT ---
        verify(jwtProvider, times(1)).getUsername(token);
        verify(loanRepository, times(1)).findById(loanId);
        verify(loanRepository, times(1)).extendLoan(loanId);
    }

    @Test
    void extendLoan_throwsUserAccessDeniedException() throws UserAccessDeniedException, LoanOverdueException {

        // --- ARRANGE ---
        String token = "Token";
        Integer loanId = 1;

        // jwt
        String pseudo = "Pseudo";
        when(jwtProvider.getUsername(token)).thenReturn(pseudo);

        // exemplary
        Exemplary exemplary = new Exemplary();

        // exemplary-library
        String libraryName = "LibraryName";
        String libraryAddress = "LibraryAddress";
        String libraryPhone = "0505050505";
        Library library = new Library();
        library.setName(libraryName);
        library.setAddress(libraryAddress);
        library.setPhone(libraryPhone);
        exemplary.setLibrary(library);

        // exemplary-book
        Book book = new Book();
        String bookTitle = "BookTitle";
        LocalDate bookPublicationDate = LocalDate.now();
        Publisher bookPublisher = new Publisher();
        bookPublisher.setName("BookPublisher");
        Set<Author> bookAuthors = new HashSet<>();
        Author bookAuthor = new Author();
        bookAuthor.setName("BookAuthor");
        bookAuthors.add(bookAuthor);
        book.setTitle(bookTitle);
        book.setPublicationDate(bookPublicationDate);
        book.setPublisher(bookPublisher);
        book.setAuthors(bookAuthors);
        exemplary.setBook(book);

        // user
        User user = new User();
        String pseudoDiff = "PseudoDiff";
        user.setPseudo(pseudoDiff);
        String userEmail = "User@Email.com";
        user.setEmail(userEmail);
        Role userRole = Role.USER;
        user.setRole(userRole);

        // loan
        Loan loan = new Loan();
        loan.setId(loanId);
        LocalDate loanDate = LocalDate.now().minusWeeks(2);
        loan.setLoanDate(loanDate);

        loan.setExemplary(exemplary);
        loan.setUser(user);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        // --- ACT/ASSERT ---
        assertThatThrownBy(() -> {
            loanService.extendLoan(loanId, token);
        }).isInstanceOf(UserAccessDeniedException.class)
          .hasMessage("Users can only extends their own loans");

        verify(jwtProvider, times(1)).getUsername(token);
        verify(loanRepository, times(1)).findById(loanId);
        verify(loanRepository, times(0)).extendLoan(loanId);
    }

    @Test
    void extendLoan_throwsLoanOverdueException() throws UserAccessDeniedException, LoanOverdueException {

        // --- ARRANGE ---
        String token = "Token";
        Integer loanId = 1;

        // jwt
        String pseudo = "Pseudo";
        when(jwtProvider.getUsername(token)).thenReturn(pseudo);

        // exemplary
        Exemplary exemplary = new Exemplary();

        // exemplary-library
        String libraryName = "LibraryName";
        String libraryAddress = "LibraryAddress";
        String libraryPhone = "0505050505";
        Library library = new Library();
        library.setName(libraryName);
        library.setAddress(libraryAddress);
        library.setPhone(libraryPhone);
        exemplary.setLibrary(library);

        // exemplary-book
        Book book = new Book();
        String bookTitle = "BookTitle";
        LocalDate bookPublicationDate = LocalDate.now();
        Publisher bookPublisher = new Publisher();
        bookPublisher.setName("BookPublisher");
        Set<Author> bookAuthors = new HashSet<>();
        Author bookAuthor = new Author();
        bookAuthor.setName("BookAuthor");
        bookAuthors.add(bookAuthor);
        book.setTitle(bookTitle);
        book.setPublicationDate(bookPublicationDate);
        book.setPublisher(bookPublisher);
        book.setAuthors(bookAuthors);
        exemplary.setBook(book);

        // user
        User user = new User();
        user.setPseudo(pseudo);
        String userEmail = "User@Email.com";
        user.setEmail(userEmail);
        Role userRole = Role.USER;
        user.setRole(userRole);

        // loan
        Loan loan = new Loan();
        loan.setId(loanId);
        LocalDate loanDate = LocalDate.now().minusWeeks(4).minusDays(1);
        loan.setLoanDate(loanDate);

        loan.setExemplary(exemplary);
        loan.setUser(user);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        // --- ACT/ASSERT ---
        assertThatThrownBy(() -> {
            loanService.extendLoan(loanId, token);
        }).isInstanceOf(LoanOverdueException.class)
          .hasMessage("Loans can't be extended after 4 weeks");

        verify(jwtProvider, times(1)).getUsername(token);
        verify(loanRepository, times(1)).findById(loanId);
        verify(loanRepository, times(0)).extendLoan(loanId);
    }

    @Test
    void userLoanReminder_givenLoanNotExtendOlderThan1Month_shouldSend1Mail() {

        // --- ARRANGE ---

        // exemplary
        Exemplary exemplary = new Exemplary();

        // exemplary-library
        String libraryName = "LibraryName";
        String libraryAddress = "LibraryAddress";
        String libraryPhone = "0505050505";
        Library library = new Library();
        library.setName(libraryName);
        library.setAddress(libraryAddress);
        library.setPhone(libraryPhone);
        exemplary.setLibrary(library);

        // exemplary-book
        Book book = new Book();
        String bookTitle = "BookTitle";
        LocalDate bookPublicationDate = LocalDate.now();
        Publisher bookPublisher = new Publisher();
        bookPublisher.setName("BookPublisher");
        Set<Author> bookAuthors = new HashSet<>();
        Author bookAuthor = new Author();
        bookAuthor.setName("BookAuthor");
        bookAuthors.add(bookAuthor);
        book.setTitle(bookTitle);
        book.setPublicationDate(bookPublicationDate);
        book.setPublisher(bookPublisher);
        book.setAuthors(bookAuthors);
        exemplary.setBook(book);

        // user
        User user = new User();
        String pseudo = "Pseudo";
        user.setPseudo(pseudo);
        String userEmail = "User@Email.com";
        user.setEmail(userEmail);
        Role userRole = Role.USER;
        user.setRole(userRole);

        // loan
        Loan loan = new Loan();
        Integer loanId = 1;
        loan.setId(loanId);
        LocalDate loanDate = LocalDate.now().minusMonths(1).minusDays(1);
        loan.setLoanDate(loanDate);
        loan.setExtend(false);

        loan.setExemplary(exemplary);
        loan.setUser(user);

        List<Loan> loans = new ArrayList<>();
        loans.add(loan);

        when(loanRepository.findOverdue(any(), any())).thenReturn(loans);

        // --- ACT ---
        loanService.userLoanReminder();

        // --- ASSERT ---
        verify(loanRepository, times(1)).findOverdue(any(), any());
        verify(mailService, times(1)).sendUserLoanReminder(loan);
    }

    @Test
    void userLoanReminder_givenLoanNotExtendNotOlderThan1Month_shouldNotSendMail() {

        // --- ARRANGE ---

        // exemplary
        Exemplary exemplary = new Exemplary();

        // exemplary-library
        String libraryName = "LibraryName";
        String libraryAddress = "LibraryAddress";
        String libraryPhone = "0505050505";
        Library library = new Library();
        library.setName(libraryName);
        library.setAddress(libraryAddress);
        library.setPhone(libraryPhone);
        exemplary.setLibrary(library);

        // exemplary-book
        Book book = new Book();
        String bookTitle = "BookTitle";
        LocalDate bookPublicationDate = LocalDate.now();
        Publisher bookPublisher = new Publisher();
        bookPublisher.setName("BookPublisher");
        Set<Author> bookAuthors = new HashSet<>();
        Author bookAuthor = new Author();
        bookAuthor.setName("BookAuthor");
        bookAuthors.add(bookAuthor);
        book.setTitle(bookTitle);
        book.setPublicationDate(bookPublicationDate);
        book.setPublisher(bookPublisher);
        book.setAuthors(bookAuthors);
        exemplary.setBook(book);

        // user
        User user = new User();
        String pseudo = "Pseudo";
        user.setPseudo(pseudo);
        String userEmail = "User@Email.com";
        user.setEmail(userEmail);
        Role userRole = Role.USER;
        user.setRole(userRole);

        // loan
        Loan loan = new Loan();
        Integer loanId = 1;
        loan.setId(loanId);
        LocalDate loanDate = LocalDate.now().minusWeeks(2);
        loan.setLoanDate(loanDate);
        loan.setExtend(false);

        loan.setExemplary(exemplary);
        loan.setUser(user);

        List<Loan> loans = new ArrayList<>();
        loans.add(loan);

        when(loanRepository.findOverdue(any(), any())).thenReturn(loans);

        // --- ACT ---
        loanService.userLoanReminder();

        // --- ASSERT ---
        verify(loanRepository, times(1)).findOverdue(any(), any());
        verify(mailService, times(0)).sendUserLoanReminder(loan);
    }

    @Test
    void userLoanReminder_givenLoanExtendOlderThan2Month_shouldSend1Mail() {

        // --- ARRANGE ---

        // exemplary
        Exemplary exemplary = new Exemplary();

        // exemplary-library
        String libraryName = "LibraryName";
        String libraryAddress = "LibraryAddress";
        String libraryPhone = "0505050505";
        Library library = new Library();
        library.setName(libraryName);
        library.setAddress(libraryAddress);
        library.setPhone(libraryPhone);
        exemplary.setLibrary(library);

        // exemplary-book
        Book book = new Book();
        String bookTitle = "BookTitle";
        LocalDate bookPublicationDate = LocalDate.now();
        Publisher bookPublisher = new Publisher();
        bookPublisher.setName("BookPublisher");
        Set<Author> bookAuthors = new HashSet<>();
        Author bookAuthor = new Author();
        bookAuthor.setName("BookAuthor");
        bookAuthors.add(bookAuthor);
        book.setTitle(bookTitle);
        book.setPublicationDate(bookPublicationDate);
        book.setPublisher(bookPublisher);
        book.setAuthors(bookAuthors);
        exemplary.setBook(book);

        // user
        User user = new User();
        String pseudo = "Pseudo";
        user.setPseudo(pseudo);
        String userEmail = "User@Email.com";
        user.setEmail(userEmail);
        Role userRole = Role.USER;
        user.setRole(userRole);

        // loan
        Loan loan = new Loan();
        Integer loanId = 1;
        loan.setId(loanId);
        LocalDate loanDate = LocalDate.now().minusMonths(2).minusDays(1);
        loan.setLoanDate(loanDate);
        loan.setExtend(true);

        loan.setExemplary(exemplary);
        loan.setUser(user);

        List<Loan> loans = new ArrayList<>();
        loans.add(loan);

        when(loanRepository.findOverdue(any(), any())).thenReturn(loans);

        // --- ACT ---
        loanService.userLoanReminder();

        // --- ASSERT ---
        verify(loanRepository, times(1)).findOverdue(any(), any());
        verify(mailService, times(1)).sendUserLoanReminder(loan);
    }

    @Test
    void userLoanReminder_givenLoanExtendNotOlderThan2Month_shouldNotSendMail() {

        // --- ARRANGE ---

        // exemplary
        Exemplary exemplary = new Exemplary();

        // exemplary-library
        String libraryName = "LibraryName";
        String libraryAddress = "LibraryAddress";
        String libraryPhone = "0505050505";
        Library library = new Library();
        library.setName(libraryName);
        library.setAddress(libraryAddress);
        library.setPhone(libraryPhone);
        exemplary.setLibrary(library);

        // exemplary-book
        Book book = new Book();
        String bookTitle = "BookTitle";
        LocalDate bookPublicationDate = LocalDate.now();
        Publisher bookPublisher = new Publisher();
        bookPublisher.setName("BookPublisher");
        Set<Author> bookAuthors = new HashSet<>();
        Author bookAuthor = new Author();
        bookAuthor.setName("BookAuthor");
        bookAuthors.add(bookAuthor);
        book.setTitle(bookTitle);
        book.setPublicationDate(bookPublicationDate);
        book.setPublisher(bookPublisher);
        book.setAuthors(bookAuthors);
        exemplary.setBook(book);

        // user
        User user = new User();
        String pseudo = "Pseudo";
        user.setPseudo(pseudo);
        String userEmail = "User@Email.com";
        user.setEmail(userEmail);
        Role userRole = Role.USER;
        user.setRole(userRole);

        // loan
        Loan loan = new Loan();
        Integer loanId = 1;
        loan.setId(loanId);
        LocalDate loanDate = LocalDate.now().minusMonths(1);
        loan.setLoanDate(loanDate);
        loan.setExtend(true);

        loan.setExemplary(exemplary);
        loan.setUser(user);

        List<Loan> loans = new ArrayList<>();
        loans.add(loan);

        when(loanRepository.findOverdue(any(), any())).thenReturn(loans);

        // --- ACT ---
        loanService.userLoanReminder();

        // --- ASSERT ---
        verify(loanRepository, times(1)).findOverdue(any(), any());
        verify(mailService, times(0)).sendUserLoanReminder(loan);
    }
}
