package com.github.badaccuracyid.lacak.challenge1.dto.suggestion;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Suggestion for a city with a match score")
public record Suggestion(
        @Schema(description = "Name of the city")
        String name,

        @Schema(description = "Latitude of the city")
        double latitude,

        @Schema(description = "Longitude of the city")
        double longitude,

        @Schema(description = "Match score of the city")
        double score
) {
}
