package com.github.badaccuracyid.lacak.challenge1.service.suggestion;

import com.github.badaccuracyid.lacak.challenge1.dto.SuggestionResponse;

public interface SuggestionService {

    SuggestionResponse getSuggestions(String query, Double latitude, Double longitude);

}
