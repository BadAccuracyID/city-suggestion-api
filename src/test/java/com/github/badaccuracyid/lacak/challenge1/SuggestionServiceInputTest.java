package com.github.badaccuracyid.lacak.challenge1;

import com.github.badaccuracyid.lacak.challenge1.model.City;
import com.github.badaccuracyid.lacak.challenge1.properties.SuggestionScoringProperties;
import com.github.badaccuracyid.lacak.challenge1.service.city.CityServiceImpl;
import com.github.badaccuracyid.lacak.challenge1.service.suggestion.SuggestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SuggestionServiceInputTest {

    @MockitoBean
    private CityServiceImpl cityService;

    @Autowired
    private SuggestionService suggestionService;

    @Autowired
    private SuggestionScoringProperties scoringProperties;

    @Test
    void invalidQueryLat() {
        when(cityService.findByNameContainingIgnoreCase("New"))
                .thenReturn(List.of(
                        new City(1L, "New York", 40.7128, -74.0060, BigInteger.valueOf(8175133L)),
                        new City(2L, "Newark", 40.7357, -74.1724, BigInteger.valueOf(9145L))
                ));

        assertThrows(IllegalArgumentException.class, () -> {
            suggestionService.getSuggestions("New", 100D, null);
        });
    }

    @Test
    void invalidQueryLong() {
        when(cityService.findByNameContainingIgnoreCase("New"))
                .thenReturn(List.of(
                        new City(1L, "New York", 40.7128, -74.0060, BigInteger.valueOf(8175133L)),
                        new City(2L, "Newark", 40.7357, -74.1724, BigInteger.valueOf(9145L))
                ));

        assertThrows(IllegalArgumentException.class, () -> {
            suggestionService.getSuggestions("New", null, 200D);
        });
    }

    @Test
    void invalidQueryLatLong() {
        when(cityService.findByNameContainingIgnoreCase("New"))
                .thenReturn(List.of(
                        new City(1L, "New York", 40.7128, -74.0060, BigInteger.valueOf(8175133L)),
                        new City(2L, "Newark", 40.7357, -74.1724, BigInteger.valueOf(9145L))
                ));

        assertThrows(IllegalArgumentException.class, () -> {
            suggestionService.getSuggestions("New", 100D, 200D);
        });
    }

    @Test
    void invalidQueryQuery() {
        when(cityService.findByNameContainingIgnoreCase("New"))
                .thenReturn(List.of(
                        new City(1L, "New York", 40.7128, -74.0060, BigInteger.valueOf(8175133L)),
                        new City(2L, "Newark", 40.7357, -74.1724, BigInteger.valueOf(9145L))
                ));

        assertThrows(IllegalArgumentException.class, () -> {
            suggestionService.getSuggestions(null, null, null);
        });
    }

}
