package com.coyote.big_city_library.rest_server_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.coyote.big_city_library.rest_server_service.exceptions.UserAccessDeniedException;

@SpringBootTest
@Transactional
public class ReservationServiceIT {


    @Autowired
    ReservationService reservationService;

    @Test
    void addReservation_returnReservationDto() throws UserAccessDeniedException {

        // --- ARRANGE ---
        // Book : Le Prince
        Integer bookId = 3;
        // Token user : Charles
        String token =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJDaGFybGVzIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE2OTAzMjI0MDAsImV4cCI6MTcyMTk0NDgwMH0.Zo7k5_mc-NToTX3fiKKywvRCMo37Yjx-GFjvDiyS026g82M7-fH1RJFlfb1tDZ365TlTqv5rSOE492JQv0VBog";

        // --- ACT ---
        reservationService.addReservation(bookId, token);

        // --- ASSERT ---

    }

    @Test
    void addReservation_returnReservationDto2() throws UserAccessDeniedException {

        // --- ARRANGE ---
        // Book : Le Prince
        Integer bookId = 3;
        // Token user : Charles
        String token =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJDaGFybGVzIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE2OTAzMjI0MDAsImV4cCI6MTcyMTk0NDgwMH0.Zo7k5_mc-NToTX3fiKKywvRCMo37Yjx-GFjvDiyS026g82M7-fH1RJFlfb1tDZ365TlTqv5rSOE492JQv0VBog";

        // --- ACT ---
        reservationService.addReservation(bookId, token);

        // --- ASSERT ---

    }

}
