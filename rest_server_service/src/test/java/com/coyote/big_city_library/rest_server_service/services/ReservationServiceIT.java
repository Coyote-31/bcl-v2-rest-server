package com.coyote.big_city_library.rest_server_service.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.coyote.big_city_library.rest_server_service.dto.ReservationDto;
import com.coyote.big_city_library.rest_server_service.dto.ReservationIdDto;
import com.coyote.big_city_library.rest_server_service.dto.reservation.my.MyReservationDto;
import com.coyote.big_city_library.rest_server_service.exceptions.UserAccessDeniedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional
public class ReservationServiceIT {


    @Autowired
    ReservationService reservationService;

    // Users tokens
    final String TOKEN_ANNE =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbm5lIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE2OTAzMjI0MDAsImV4cCI6MTg5MzQ1NjAwMH0.GSZIsWtOv3hxyB_rXnua-hKnEk4LnzEG3FPiCxFtj6fxTbMmEDG5xn5RGoPYSE9364fqRAZqwL16F77bEGiM0g";
    final String TOKEN_BRUNO =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJCcnVubyIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNjkwMzIyNDAwLCJleHAiOjE4OTM0NTYwMDB9._rnA8VCwc94-GjFlxHIOekhlPDL96wslzUA8aBbRGchg_gakxWgnycy0eaTaQhHfP3N6AOBF0Q3-zP70xDaPiA";
    final String TOKEN_CHARLES =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJDaGFybGVzIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE2OTAzMjI0MDAsImV4cCI6MTg5MzQ1NjAwMH0.UmUi3iapbxnV4nvCyRVWlx4llco6OVPx9UA0IG9wL9GjbZlWQVcI2PbQ6KM5_4UcBC1pY8-URZh4lasfEGfvMg";

    @Test
    void addReservation_returnsReservationDto() throws UserAccessDeniedException, EntityExistsException {

        // --- ARRANGE ---
        // Book : Le Prince
        Integer bookId = 3;

        // --- ACT ---
        ReservationDto reservationDto = reservationService.addReservation(bookId, TOKEN_CHARLES);

        // --- ASSERT ---
        assertThat(reservationDto.getBook().getTitle()).isEqualTo("Le Prince");
        assertThat(reservationDto.getUser().getPseudo()).isEqualTo("Charles");
        assertThat(reservationDto.getCreatedAt()).isNotNull();
        assertThat(reservationDto.getCreatedAt()).isBefore(ZonedDateTime.now(ZoneId.of("UTC")));
    }

    @Test
    void addReservation_RG2_throwsUserAccessDeniedException() throws UserAccessDeniedException, EntityExistsException {

        // --- ARRANGE ---
        // Book : Coyote céleste
        Integer bookId = 1;

        // --- ACT / ASSERT ---
        assertThatThrownBy(() -> {
            reservationService.addReservation(bookId, TOKEN_BRUNO);
        }).isInstanceOf(UserAccessDeniedException.class)
          .hasMessage("RG_Reservation_2 : Reservation list is already full");
    }

    @Test
    void addReservation_RG3_throwsUserAccessDeniedException() throws UserAccessDeniedException, EntityExistsException {

        // --- ARRANGE ---
        // Book : L’Art de la guerre
        Integer bookId = 2;

        // --- ACT / ASSERT ---
        assertThatThrownBy(() -> {
            reservationService.addReservation(bookId, TOKEN_ANNE);
        }).isInstanceOf(UserAccessDeniedException.class)
          .hasMessage("RG_Reservation_3 : Reservation of loaned book is forbidden");
    }

    @Test
    void addReservation_ExistingEntity_throwsEntityExistsException()
            throws UserAccessDeniedException, EntityExistsException {

        // --- ARRANGE ---
        // Book : Le Prince
        Integer bookId = 3;

        // --- ACT / ASSERT ---
        assertThatThrownBy(() -> {
            reservationService.addReservation(bookId, TOKEN_ANNE);
        }).isInstanceOf(EntityExistsException.class)
          .hasMessage("Reservation entity with bookId:3 userId:3 already exists");
    }

    @Test
    void findAllReservations_returnsNotEmptyList() {

        // --- ARRANGE ---
        List<ReservationDto> reservationDtos = new ArrayList<>();

        // --- ACT ---
        reservationDtos = reservationService.findAllReservations();

        // --- ASSERT ---
        assertThat(reservationDtos).isNotEmpty();
    }

    @Test
    void findReservationById_returnsValidReservationDto() {

        // --- ARRANGE ---
        ReservationIdDto reservationIdDto = new ReservationIdDto();
        reservationIdDto.setBookId(1);
        reservationIdDto.setUserId(3);


        // --- ACT ---
        ReservationDto reservationDto = reservationService.findReservationById(reservationIdDto);

        // --- ASSERT ---
        assertThat(reservationDto).isNotNull();
        assertThat(reservationDto.getBook().getTitle()).isEqualTo("Coyote céleste");
        assertThat(reservationDto.getUser().getPseudo()).isEqualTo("Anne");
    }

    @Test
    void findReservationsByUserPseudo_returnsNotEmptyList() {

        // --- ARRANGE ---
        String pseudo = "Anne";

        // --- ACT ---
        List<MyReservationDto> reservationDtos = reservationService.findReservationsByUserPseudo(pseudo);

        // --- ASSERT ---
        assertThat(reservationDtos).isNotNull()
                                   .isNotEmpty()
                                   .hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    void updateReservation_returnsUpdatedReservation() {

        // --- ARRANGE ---
        ReservationIdDto reservationIdDto = new ReservationIdDto();
        reservationIdDto.setBookId(1);
        reservationIdDto.setUserId(3);
        ReservationDto reservationDto = reservationService.findReservationById(reservationIdDto);

        ZonedDateTime nowZDT = ZonedDateTime.now(ZoneId.of("UTC"));
        reservationDto.setNotifiedAt(nowZDT);

        // --- ACT ---
        ReservationDto reservationDtoUpdated = reservationService.updateReservation(reservationDto);

        // --- ASSERT ---
        assertThat(reservationDtoUpdated).isNotNull();
        assertThat(reservationDtoUpdated.getBook().getId()).isEqualTo(1);
        assertThat(reservationDtoUpdated.getUser().getId()).isEqualTo(3);
        assertThat(reservationDtoUpdated.getNotifiedAt()).isNotNull()
                                                         .isEqualTo(nowZDT);
    }

    @Test
    void updateReservation_isPersisted() {

        // --- ARRANGE ---
        ReservationIdDto reservationIdDto = new ReservationIdDto();
        reservationIdDto.setBookId(1);
        reservationIdDto.setUserId(3);
        ReservationDto reservationDto = reservationService.findReservationById(reservationIdDto);

        ZonedDateTime nowZDT = ZonedDateTime.now(ZoneId.of("UTC"));
        reservationDto.setNotifiedAt(nowZDT);

        // --- ACT ---
        reservationService.updateReservation(reservationDto);
        ReservationDto reservationDtoFromDB = reservationService.findReservationById(reservationIdDto);

        // --- ASSERT ---
        assertThat(reservationDtoFromDB).isNotNull();
        assertThat(reservationDtoFromDB.getBook().getId()).isEqualTo(1);
        assertThat(reservationDtoFromDB.getUser().getId()).isEqualTo(3);
        assertThat(reservationDtoFromDB.getNotifiedAt()).isNotNull()
                                                        .isEqualTo(nowZDT);
    }

    @Test
    void deleteReservation_isNoMoreInDB() {

        // --- ARRANGE ---
        ReservationIdDto reservationIdDto = new ReservationIdDto();
        reservationIdDto.setBookId(1);
        reservationIdDto.setUserId(3);
        ReservationDto reservationDto = reservationService.findReservationById(reservationIdDto);

        // --- ACT ---
        reservationService.deleteReservation(reservationDto);
        ReservationDto reservationDtoReturned = reservationService.findReservationById(reservationIdDto);

        // --- ASSERT ---
        assertThat(reservationDto).isNotNull();
        assertThat(reservationDtoReturned).isNull();
    }

    @Test
    void deleteReservationById_isNoMoreInDB() throws UserAccessDeniedException {

        // --- ARRANGE ---
        ReservationIdDto reservationIdDto = new ReservationIdDto();
        reservationIdDto.setBookId(1);
        reservationIdDto.setUserId(3);
        ReservationDto reservationDto = reservationService.findReservationById(reservationIdDto);

        // --- ACT ---
        reservationService.deleteReservationById(reservationIdDto, TOKEN_ANNE);
        ReservationDto reservationDtoReturned = reservationService.findReservationById(reservationIdDto);

        // --- ASSERT ---
        assertThat(reservationDto).isNotNull();
        assertThat(reservationDtoReturned).isNull();
    }

    @Test
    void deleteReservationById_throwsUserAccessDeniedException() throws UserAccessDeniedException {

        // --- ARRANGE ---
        ReservationIdDto reservationIdDto = new ReservationIdDto();
        reservationIdDto.setBookId(1);
        reservationIdDto.setUserId(3);

        // --- ACT/ASSERT ---
        assertThatThrownBy(() -> {
            reservationService.deleteReservationById(reservationIdDto, TOKEN_BRUNO);
        }).isInstanceOf(UserAccessDeniedException.class)
          .hasMessage("User can only delete their own reservation");

    }

    @Test
    void reservationNotification_validatedOperations() throws UserAccessDeniedException {

        // --- ARRANGE ---
        // Must be notified
        ReservationIdDto reservationIdDto1 = new ReservationIdDto();
        reservationIdDto1.setBookId(3);
        reservationIdDto1.setUserId(3);

        // Must be deleted
        ReservationIdDto reservationIdDto2 = new ReservationIdDto();
        reservationIdDto2.setBookId(3);
        reservationIdDto2.setUserId(4);

        // Must not be notified (still in loan)
        ReservationIdDto reservationIdDto3 = new ReservationIdDto();
        reservationIdDto3.setBookId(1);
        reservationIdDto3.setUserId(3);

        // --- ACT ---
        reservationService.reservationNotification();
        ReservationDto reservationDto1 = reservationService.findReservationById(reservationIdDto1);
        ReservationDto reservationDto2 = reservationService.findReservationById(reservationIdDto2);
        ReservationDto reservationDto3 = reservationService.findReservationById(reservationIdDto3);

        // --- ASSERT ---
        assertThat(reservationDto1).isNotNull();
        assertThat(reservationDto1.getNotifiedAt()).isNotNull();
        assertThat(reservationDto1.getExemplary()).isNotNull();

        assertThat(reservationDto2).isNull();

        assertThat(reservationDto3).isNotNull();
        assertThat(reservationDto3.getNotifiedAt()).isNull();
    }

}
