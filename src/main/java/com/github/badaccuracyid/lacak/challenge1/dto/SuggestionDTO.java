package com.github.badaccuracyid.lacak.challenge1.dto;

public record SuggestionDTO(
        String name,
        double latitude,
        double longitude,
        double score
) {
}
