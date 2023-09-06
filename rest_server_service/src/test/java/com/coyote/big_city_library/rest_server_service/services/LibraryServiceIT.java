package com.coyote.big_city_library.rest_server_service.services;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.coyote.big_city_library.rest_server_service.dto.LibraryDto;

@SpringBootTest
@Transactional
public class LibraryServiceIT {

    @Autowired
    LibraryService libraryService;

    @Test
    void findAllLibraries() {

        // --- ACT ---
        List<LibraryDto> libraryDtos = libraryService.findAllLibraries();

        // --- ASSERT ---
        assertThat(libraryDtos).isNotNull().isNotEmpty().hasSizeGreaterThan(1);
    }

}
