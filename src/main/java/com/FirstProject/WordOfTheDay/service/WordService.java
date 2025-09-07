package com.FirstProject.WordOfTheDay.service;

import com.FirstProject.WordOfTheDay.persistence.entity.SavedWord;
import com.FirstProject.WordOfTheDay.persistence.repository.SavedWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;

/**
 * Handles fetching and storing the single current word.
 */
@Service
@RequiredArgsConstructor
public class WordService {

    private final RestTemplate restTemplate;
    private final SavedWordRepository savedWordRepository;

    static final String RANDOM_WORD_API = "https://random-word-api.herokuapp.com/word";

    /**
     * Fetches a random word from the external API, overwrites the single DB row, and returns it.
     * @return the new word
     */
    public String fetchAndSaveNewWord() {
        ResponseEntity<String[]> resp = restTemplate.getForEntity(RANDOM_WORD_API, String[].class);
        String[] body = resp.getBody();
        if (body == null || body.length == 0 || body[0] == null || body[0].isBlank()) {
            throw new IllegalStateException("Random word API returned empty response: " + Arrays.toString(body));
        }
        String word = body[0].trim();

        SavedWord record = savedWordRepository.findById(1L)
                .orElse(SavedWord.builder().id(1L).word(word).updatedAt(Instant.now()).build());
        record.setWord(word);
        record.setUpdatedAt(Instant.now());
        savedWordRepository.save(record);
        return word;
    }

    /**
     * @return the currently saved word or null if not set.
     */
    public String currentWordOrNull() {
        return savedWordRepository.findById(1L).map(SavedWord::getWord).orElse(null);
    }
}
