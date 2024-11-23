package com.github.badaccuracyid.lacak.challenge1.dto.suggestion;

public record Suggestion(
        String name,
        double latitude,
        double longitude,
        double score
) {
}
