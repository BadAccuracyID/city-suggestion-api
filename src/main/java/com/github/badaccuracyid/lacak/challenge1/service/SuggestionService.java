package com.github.badaccuracyid.lacak.challenge1.service;

import com.github.badaccuracyid.lacak.challenge1.dto.Suggestion;
import com.github.badaccuracyid.lacak.challenge1.dto.SuggestionResponse;
import com.github.badaccuracyid.lacak.challenge1.model.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private double calculateCombinedScore(City city, String query, Double latitude, Double longitude) {
        double nameScore = calculateScoreName(city, query);
        double locationScore = calculateScoreLocation(city, latitude, longitude);

        double nameWeight = 0.7;
        double locationWeight = 0.3;

        double combinedScore = (nameScore * nameWeight) + (locationScore * locationWeight);

        return Math.min(1.0, Math.max(0.0, combinedScore));
    }

    private double calculateScoreName(City city, String query) {
        double score;

        if (city.getName().equalsIgnoreCase(query)) {
            score = 1.0;
        } else {
            double distance = levenshteinDistance(city.getName().toLowerCase(), query.toLowerCase());
            score = 1.0 / (1 + distance);
        }

        return score;
    }

    private double levenshteinDistance(String s, String t) {
        int n = s.length();
        int m = t.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int cost = s.charAt(i - 1) == t.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }
        return dp[n][m];
    }

    private double calculateScoreLocation(City city, Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            return 0.0;
        }

        double distance = haversineDistance(latitude, longitude, city.getLatitude(), city.getLongitude());
        return 1.0 / (1 + distance);
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
