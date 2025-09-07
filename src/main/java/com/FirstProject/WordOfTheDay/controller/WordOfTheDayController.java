package com.FirstProject.WordOfTheDay.controller;

import com.FirstProject.WordOfTheDay.service.DefinitionService;
import com.FirstProject.WordOfTheDay.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

/**
 * REST endpoints: /api/word and /api/definition.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "WordOfTheDay API", description = "Fetches random word and daily cached definitions.")
public class WordOfTheDayController {

    private final WordService wordService;
    private final DefinitionService definitionService;

    /**
     * Calls random-word API, saves only one word in DB (overwriting previous), and returns it.
     */
    @GetMapping(value = "/word", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get & save a random word",
            description = "Fetches a random word and stores it as the only row in the database.")
    @ApiResponse(responseCode = "200", description = "Word saved",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(example = "{\"word\":\"unlaced\",\"saved\":true,\"updatedAt\":\"2025-09-07T12:00:00Z\"}")))
    public ResponseEntity<?> getAndSaveWord() {
        String word = wordService.fetchAndSaveNewWord();
        return ResponseEntity.ok(Map.of(
                "word", word,
                "saved", true,
                "updatedAt", Instant.now().toString()
        ));
    }

    /**
     * Returns word + definitions (cached for the whole day).
     */
    @GetMapping(value = "/definition", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get definitions (cached for the day)",
            description = "Uses the saved word to fetch definitions and caches the JSON per calendar day.")
    @ApiResponse(responseCode = "200", description = "Definition JSON")
    public ResponseEntity<String> getDefinition() {
        String json = definitionService.getDefinitionJsonCachedForToday();
        return ResponseEntity.ok(json);
    }
}
