package com.github.badaccuracyid.lacak.challenge1.service;

import com.github.badaccuracyid.lacak.challenge1.dto.Suggestion;
import com.github.badaccuracyid.lacak.challenge1.dto.SuggestionResponse;
import com.github.badaccuracyid.lacak.challenge1.model.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuggestionService {

    private static final Logger logger = LoggerFactory.getLogger(SuggestionService.class);
    private final CityService cityService;

    public SuggestionService(CityService cityService) {
        this.cityService = cityService;
    }

    public SuggestionResponse getSuggestions(String query, Double latitude, Double longitude) {
        logger.info("Fetching suggestions for query: {}, latitude: {}, longitude: {}", query, latitude, longitude);

        List<City> cities = cityService.findByNameContainingIgnoreCase(query);
        List<Suggestion> suggestions = cities.stream()
                .map(city -> new Suggestion(
                        city.getName(),
                        city.getLatitude(),
                        city.getLongitude(),
                        calculateScore(city, latitude, longitude)
                ))
                .sorted((s1, s2) -> Double.compare(s2.score(), s1.score()))
                .toList();

        return new SuggestionResponse(suggestions);
    }

    private double calculateScore(City city, Double latitude, Double longitude) {
        double score = 1.0;

        if (latitude != null && longitude != null) {
            double distance = haversineDistance(latitude, longitude, city.getLatitude(), city.getLongitude());
            score = 1 / (1 + distance);
        }

        return Math.min(1.0, Math.max(0.0, score));
    }

    private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
