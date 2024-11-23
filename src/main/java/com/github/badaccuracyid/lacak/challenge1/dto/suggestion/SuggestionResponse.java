package com.github.badaccuracyid.lacak.challenge1.dto.suggestion;

import java.util.List;

public record SuggestionResponse(
        List<Suggestion> suggestions
) {
}
