package com.github.badaccuracyid.lacak.challenge1;

import com.github.badaccuracyid.lacak.challenge1.dto.suggestion.SuggestionResponse;
import com.github.badaccuracyid.lacak.challenge1.model.City;
import com.github.badaccuracyid.lacak.challenge1.properties.SuggestionScoringProperties;
import com.github.badaccuracyid.lacak.challenge1.service.city.CityServiceImpl;
import com.github.badaccuracyid.lacak.challenge1.service.suggestion.SuggestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = {
        "suggestion.scoring.name-weight=0.5",
        "suggestion.scoring.location-weight=0.3",
        "suggestion.scoring.use-population=false",
        "suggestion.scoring.population-weight=0.2"
})
@ActiveProfiles("test")
public class SuggestionServiceWithoutPopulationTest {

    @MockitoBean
    private CityServiceImpl cityService;

    @Autowired
    private SuggestionService suggestionService;

    @Autowired
    private SuggestionScoringProperties scoringProperties;

    @Test
    void testGetSuggestionsWithoutPopulation() {
        when(cityService.findByNameContainingIgnoreCase("New"))
                .thenReturn(List.of(
                        new City(1L, "New York", 40.7128, -74.0060, BigInteger.valueOf(8175133L)),
                        new City(2L, "Newark", 40.7357, -74.1724, BigInteger.valueOf(9145L))
                ));

        SuggestionResponse response = suggestionService.getSuggestions("New", null, null);

        assertNotNull(response);
        assertFalse(response.suggestions().isEmpty());
        assertEquals(2, response.suggestions().size());
        assertEquals("Newark", response.suggestions().getFirst().name());
    }

}
