package com.example.newsnc1server.repository;

import com.example.newsnc1server.exception.NewsNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.newsnc1server.entity.News;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    /**
     * Retrieves all news entries where the publication time is between the specified start and end times.
     * This query uses native SQL to filter news based on the time of day.
     *
     * @param startTime the start time in the format "HH:MM"
     * @param endTime the end time in the format "HH:MM"
     * @return a list of News objects that were published between the specified times
     */
    @Query(value = "SELECT * FROM news WHERE TIME(publication_time) BETWEEN :startTime AND :endTime", nativeQuery = true)
    List<News> findByPublicationTimeBetween(String startTime, String endTime);

    /**
     * Retrieves a news entry by its ID. If the news entry is not found, throws a NewsNotFoundException.
     * This method provides a default implementation that simplifies error handling.
     *
     * @param id the ID of the news entry to retrieve
     * @return the News object if found
     * @throws NewsNotFoundException if the news entry is not found
     */
    default News findByIdOrThrowException(Long id) {
        return this.findById(id)
                .orElseThrow(NewsNotFoundException::new);
    }
}
