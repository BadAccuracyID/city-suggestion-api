package com.github.badaccuracyid.lacak.challenge1.controller;

import com.github.badaccuracyid.lacak.challenge1.dto.suggestion.SuggestionResponse;
import com.github.badaccuracyid.lacak.challenge1.service.suggestion.SuggestionService;
import com.github.badaccuracyid.lacak.challenge1.service.suggestion.SuggestionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionServiceImpl suggestionServiceImpl) {
        this.suggestionService = suggestionServiceImpl;
    }

    @Operation(
            summary = "Get city suggestions",
            description = "Get city suggestions based on the query",
            parameters = {
                    @Parameter(name = "q", description = "Query string to search for city names", required = true),
                    @Parameter(name = "latitude", description = "Latitude of the location"),
                    @Parameter(name = "longitude", description = "Longitude of the location")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuggestionResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input parameters",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
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
