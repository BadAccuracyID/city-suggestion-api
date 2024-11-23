package com.github.badaccuracyid.lacak.challenge1;

import com.github.badaccuracyid.lacak.challenge1.dto.suggestion.SuggestionResponse;
import com.github.badaccuracyid.lacak.challenge1.model.City;
import com.github.badaccuracyid.lacak.challenge1.service.city.CityServiceImpl;
import com.github.badaccuracyid.lacak.challenge1.service.suggestion.SuggestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SuggestionServiceImplTest {

    @MockitoBean
    private CityServiceImpl cityService;

    @Autowired
    private SuggestionService suggestionService;

    @Test
    void testGetSuggestions() {
        when(cityService.findByNameContainingIgnoreCase("New"))
                .thenReturn(List.of(
                        new City(1L, "New York", 40.7128, -74.0060),
                        new City(2L, "Newark", 40.7357, -74.1724)
                ));

        SuggestionResponse response = suggestionService.getSuggestions("New", null, null);

        assertNotNull(response);
        assertFalse(response.suggestions().isEmpty());
        assertEquals(2, response.suggestions().size());
        assertEquals("New York", response.suggestions().getFirst().name());
    }

    @Test
    void invalidQueryLat() {
        when(cityService.findByNameContainingIgnoreCase("New"))
                .thenReturn(List.of(
                        new City(1L, "New York", 40.7128, -74.0060),
                        new City(2L, "Newark", 40.7357, -74.1724)
                ));

        assertThrows(IllegalArgumentException.class, () -> {
            suggestionService.getSuggestions("New", 100D, null);
        });
    }

    @Test
    void invalidQueryLong() {
        when(cityService.findByNameContainingIgnoreCase("New"))
                .thenReturn(List.of(
                        new City(1L, "New York", 40.7128, -74.0060),
                        new City(2L, "Newark", 40.7357, -74.1724)
                ));

        assertThrows(IllegalArgumentException.class, () -> {
            suggestionService.getSuggestions("New", null, 200D);
        });
    }

    @Test
    void invalidQueryLatLong() {
        when(cityService.findByNameContainingIgnoreCase("New"))
                .thenReturn(List.of(
                        new City(1L, "New York", 40.7128, -74.0060),
                        new City(2L, "Newark", 40.7357, -74.1724)
                ));

        assertThrows(IllegalArgumentException.class, () -> {
            suggestionService.getSuggestions("New", 100D, 200D);
        });
    }

    @Test
    void invalidQueryQuery() {
        when(cityService.findByNameContainingIgnoreCase("New"))
                .thenReturn(List.of(
                        new City(1L, "New York", 40.7128, -74.0060),
                        new City(2L, "Newark", 40.7357, -74.1724)
                ));

        assertThrows(IllegalArgumentException.class, () -> {
            suggestionService.getSuggestions(null, null, null);
        });
    }

}
