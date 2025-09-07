package com.FirstProject.WordOfTheDay.persistence.repository;

import com.FirstProject.WordOfTheDay.persistence.entity.SavedWord;
import org.springframework.data.jpa.repository.JpaRepository;

/** JPA repository for the single SavedWord row. */
public interface SavedWordRepository extends JpaRepository<SavedWord, Long> { }