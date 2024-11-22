package com.github.badaccuracyid.lacak.challenge1.dto;

public record Suggestion(
        String name,
        double latitude,
        double longitude,
        double score
) {
}
