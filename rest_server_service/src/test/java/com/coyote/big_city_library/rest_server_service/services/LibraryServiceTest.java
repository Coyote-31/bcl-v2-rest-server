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
import com.coyote.big_city_library.rest_server_model.dao.entities.Library;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.LibraryRepository;
import com.coyote.big_city_library.rest_server_service.dto.LibraryDto;
import com.coyote.big_city_library.rest_server_service.dto.LibraryMapper;
import com.coyote.big_city_library.rest_server_service.dto.LibraryMapperImpl;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

    @InjectMocks
    LibraryService libraryService;

    @Mock
    LibraryRepository libraryRepository;

    @Spy
    LibraryMapper libraryMapper = new LibraryMapperImpl();

    @Test
    void findAllLibraries() {

        // --- ARRANGE ---
        List<Library> libraries = new ArrayList<>();
        String libraryName = "LibraryName";
        String libraryAddress = "Address";
        String libraryPhone = "0505050505";
        Library library = new Library();
        library.setName(libraryName);
        library.setAddress(libraryAddress);
        library.setPhone(libraryPhone);
        libraries.add(library);

        when(libraryRepository.findAll()).thenReturn(libraries);

        // --- ACT ---
        List<LibraryDto> libraryDtos = libraryService.findAllLibraries();

        // --- ASSERT ---
        verify(libraryMapper, times(1)).toDto(libraries);
        assertThat(libraryDtos).isNotNull().isNotEmpty().hasSize(1);
        assertThat(libraries.get(0).getName()).isEqualTo(libraryName);
        assertThat(libraries.get(0).getAddress()).isEqualTo(libraryAddress);
        assertThat(libraries.get(0).getPhone()).isEqualTo(libraryPhone);

    }

}
