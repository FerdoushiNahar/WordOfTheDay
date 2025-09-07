package com.FirstProject.WordOfTheDay.service;

import com.FirstProject.WordOfTheDay.model.dto.DefinitionResponse;
import com.FirstProject.WordOfTheDay.persistence.entity.DailyDefinitionCache;
import com.FirstProject.WordOfTheDay.persistence.repository.DailyDefinitionCacheRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Fetches definitions for the saved word and caches the JSON for the current day.
 */
@Service
@RequiredArgsConstructor
public class DefinitionService {

    private final RestTemplate restTemplate;
    private final WordService wordService;
    private final DailyDefinitionCacheRepository cacheRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    static final String DICT_API = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    /**
     * Returns today's cached definition JSON if present; otherwise calls dictionary API,
     * builds the response shape, stores it as today's cache, and returns it.
     */
    public String getDefinitionJsonCachedForToday() {
        LocalDate today = LocalDate.now();

        DailyDefinitionCache cached = cacheRepository.findById(today).orElse(null);
        if (cached != null) return cached.getJson();

        String word = wordService.currentWordOrNull();
        if (word == null || word.isBlank()) {
            throw new IllegalStateException("No word saved yet. Call /api/word first.");
        }

        JsonNode resp = restTemplate.getForObject(DICT_API + word, JsonNode.class);

        List<DefinitionResponse.DefinitionItem> defs = new ArrayList<>();
        if (resp != null && resp.isArray() && resp.size() > 0) {
            for (JsonNode entry : resp) {
                JsonNode meanings = entry.get("meanings");
                if (meanings != null && meanings.isArray()) {
                    for (JsonNode meaning : meanings) {
                        String pos = meaning.hasNonNull("partOfSpeech") ? meaning.get("partOfSpeech").asText() : null;
                        JsonNode definitions = meaning.get("definitions");
                        if (definitions != null && definitions.isArray()) {
                            for (JsonNode d : definitions) {
                                String def = d.hasNonNull("definition") ? d.get("definition").asText() : null;
                                if (def != null && !def.isBlank()) {
                                    defs.add(DefinitionResponse.DefinitionItem.builder()
                                            .definition(def)
                                            .partOfSpeech(pos)
                                            .build());
                                }
                            }
                        }
                    }
                }
            }
        }

        DefinitionResponse out = DefinitionResponse.builder()
                .word(word)
                .definitions(defs)
                .date(today)
                .build();

        try {
            String json = mapper.writeValueAsString(out);
            cacheRepository.save(DailyDefinitionCache.builder()
                    .date(today)
                    .word(word)
                    .json(json)
                    .build());
            return json;
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize cached definition JSON", e);
        }
    }
}
