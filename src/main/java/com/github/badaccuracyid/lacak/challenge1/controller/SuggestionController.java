package com.github.badaccuracyid.lacak.challenge1.controller;

import com.github.badaccuracyid.lacak.challenge1.dto.SuggestionDTO;
import com.github.badaccuracyid.lacak.challenge1.service.SuggestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping("/suggestions")
    public List<SuggestionDTO> suggestions(
            @RequestParam String query,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude
    ) {
        return suggestionService.getSuggestions(query, latitude, longitude);
    }
}
