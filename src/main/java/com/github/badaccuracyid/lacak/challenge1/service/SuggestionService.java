package com.github.badaccuracyid.lacak.challenge1.service;

import com.github.badaccuracyid.lacak.challenge1.dto.Suggestion;
import com.github.badaccuracyid.lacak.challenge1.dto.SuggestionResponse;
import com.github.badaccuracyid.lacak.challenge1.model.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SuggestionService {

    private static final Logger logger = LoggerFactory.getLogger(SuggestionService.class);
    private final CityService cityService;

    public SuggestionService(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Get suggestions for a city based on the query and location
     *
     * @param query     The query string to search for city names
     * @param latitude  The latitude of the location
     * @param longitude The longitude of the location
     * @return The {@link SuggestionResponse} containing the suggestions
     */
    @Cacheable("suggestions")
    public SuggestionResponse getSuggestions(String query, Double latitude, Double longitude) {
        logger.info("Fetching suggestions for query: {}, latitude: {}, longitude: {}", query, latitude, longitude);

        List<City> cities = cityService.findByNameContainingIgnoreCase(query);
        List<Suggestion> suggestions = cities.stream()
                .map(city -> new Suggestion(
                        city.getName(),
                        city.getLatitude(),
                        city.getLongitude(),
                        calculateCombinedScore(city, query, latitude, longitude)
                ))
                .filter(suggestion -> suggestion.score() > 0.0)
                .sorted(Comparator.comparingDouble(Suggestion::score).reversed())
                .toList();

        return new SuggestionResponse(suggestions);
    }

    /**
     * Calculate the combined score for a city based on the searched parameters
     *
     * @param city      The {@link City} to calculate the score for
     * @param query     The queried city name
     * @param latitude  The queried latitude of the location
     * @param longitude The queried longitude of the location
     * @return The combined score for the city
     */
    private double calculateCombinedScore(City city, String query, Double latitude, Double longitude) {
        double nameScore = calculateScoreName(city, query);
        double locationScore = calculateScoreLocation(city, latitude, longitude);

        double nameWeight = 0.7;
        double locationWeight = 0.3;

        double combinedScore = (nameScore * nameWeight) + (locationScore * locationWeight);

        return Math.min(1.0, Math.max(0.0, combinedScore));
    }

    /**
     * Calculate the score for the city name based on the query
     *
     * @param city  The {@link City} to calculate the score for
     * @param query The queried city name
     * @return The score for the city name
     */
    private double calculateScoreName(City city, String query) {
        double score;

        if (city.getName().equalsIgnoreCase(query)) {
            score = 1.0;
        } else {
            double distance = levenshteinDistance(city.getName(), query);
            score = 1.0 / (1 + distance);
        }

        return score;
    }

    /**
     * Calculate the Levenshtein distance between two strings.
     * <p>
     * The Levenshtein distance is a string metric for measuring the difference between two sequences, in this case, two
     * strings.
     * <p>
     * Source: <a href="https://en.wikipedia.org/wiki/Levenshtein_distance">Wikipedia</a>
     *
     * @param first  The first string
     * @param second The second string
     * @return The Levenshtein distance between the two strings
     */
    private double levenshteinDistance(String first, String second) {
        int n = first.length();
        int m = second.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int cost = first.charAt(i - 1) == second.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }
        return dp[n][m];
    }

    /**
     * Calculate the score for the city location based on the queried location
     *
     * @param city      The {@link City} to calculate the score for
     * @param latitude  The queried latitude of the location
     * @param longitude The queried longitude of the location
     * @return The score for the city location
     */
    private double calculateScoreLocation(City city, Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            return 0.0;
        }

        double distance = haversineDistance(latitude, longitude, city.getLatitude(), city.getLongitude());
        return 1.0 / (1 + distance);
    }

    /**
     * Calculate the Haversine distance between two locations
     * <p>
     * The Haversine formula determines the great-circle distance between two points on a sphere given their longitudes
     * and latitudes.
     * <p>
     * Source: <a href="https://en.wikipedia.org/wiki/Haversine_formula">Wikipedia</a>
     *
     * @param lat1 The latitude of the first location
     * @param lon1 The longitude of the first location
     * @param lat2 The latitude of the second location
     * @param lon2 The longitude of the second location
     * @return The Haversine distance between the two locations
     */
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
