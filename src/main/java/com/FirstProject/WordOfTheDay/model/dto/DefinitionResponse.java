package com.FirstProject.WordOfTheDay.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Shape returned by /api/definition.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DefinitionResponse {
    private String word;
    private List<DefinitionItem> definitions;
    private LocalDate date;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class DefinitionItem {
        private String definition;
        private String partOfSpeech;
    }
}
