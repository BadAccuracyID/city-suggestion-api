package com.github.badaccuracyid.lacak.challenge1.dto.suggestion;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Response containing a list of city suggestions")
public record SuggestionResponse(
        @Schema(description = "List of suggestions")
        List<Suggestion> suggestions
) {
}
