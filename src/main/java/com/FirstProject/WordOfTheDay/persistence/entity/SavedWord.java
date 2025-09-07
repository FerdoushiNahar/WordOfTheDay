package com.FirstProject.WordOfTheDay.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * Stores the single, most recently fetched word.
 * We always use id = 1 so only one row exists.
 */
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SavedWord {
    @Id
    private Long id; // fixed 1L

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private Instant updatedAt;
}