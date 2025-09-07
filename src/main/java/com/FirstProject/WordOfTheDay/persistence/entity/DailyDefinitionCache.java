package com.FirstProject.WordOfTheDay.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Caches the exact definition JSON we return for a given day.
 * Keyed by LocalDate to ensure same response during the day.
 */
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class DailyDefinitionCache {
    @Id
    private LocalDate date;

    @Column(nullable = false)
    private String word;

    @Lob
    @Column(nullable = false)
    private String json;
}