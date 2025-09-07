package com.FirstProject.WordOfTheDay.persistence.repository;

import com.FirstProject.WordOfTheDay.persistence.entity.DailyDefinitionCache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/** JPA repository for per-day definition cache. */
public interface DailyDefinitionCacheRepository extends JpaRepository<DailyDefinitionCache, LocalDate> { }