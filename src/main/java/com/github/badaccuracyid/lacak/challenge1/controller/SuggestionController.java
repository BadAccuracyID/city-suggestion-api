package com.github.badaccuracyid.lacak.challenge1.controller;

import com.github.badaccuracyid.lacak.challenge1.dto.SuggestionResponse;
import com.github.badaccuracyid.lacak.challenge1.service.SuggestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping("/suggestions")
    public ResponseEntity<SuggestionResponse> suggestions(
            @RequestParam String q,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude
    ) {
        SuggestionResponse response = suggestionService.getSuggestions(q, latitude, longitude);
        return ResponseEntity.ok(response);
    }

}
