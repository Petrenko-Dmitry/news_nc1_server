package com.example.newsnc1server.service;

import com.example.newsnc1server.dto.NewsDTO;
import com.example.newsnc1server.entity.News;
import com.example.newsnc1server.enums.TimeOfDay;
import com.example.newsnc1server.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.newsnc1server.dto.NewsDTO.parseNews;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    /**
     * Retrieves all news from the repository and converts them into DTO objects.
     *
     * @return a list of NewsDTO objects representing all news entries in the repository
     */
    public List<NewsDTO> getAllNews() {
        return parseNews(this.newsRepository.findAll());
    }

    /**
     * Finds all news that were published within a specific time range during the day.
     * The time range is determined by the TimeOfDay object (e.g., morning, day, evening).
     *
     * @param time the time of day used to filter news by their publication time
     * @return a list of NewsDTO objects that match the specified time range
     */
    public List<NewsDTO> findAllNewsByTimeOfDay(TimeOfDay time) {
        return parseNews(this.newsRepository.findByPublicationTimeBetween(time.getStartHour(), time.getEndHour()));
    }

    /**
     * Retrieves a specific news entry by its ID from the repository.
     *
     * @param id the ID of the news entry to retrieve
     * @return an Optional containing the News object if found, or empty if not
     */
    public Optional<News> getNewsById(Long id) {
        return this.newsRepository.findById(id);
    }

    /**
     * Adds a set of news entries to the repository.
     * If a data integrity violation occurs (e.g., duplicate entries), the exception is caught and ignored.
     *
     * @param news the set of News objects to add to the repository
     */
    public void addNews(Set<News> news) {
        for (var oneOfNews : news) {
            try {
                this.newsRepository.save(oneOfNews);
            } catch (DataIntegrityViolationException e) {
                // ignore
            }
        }
    }

    /**
     * Updates an existing news entry in the repository by its ID.
     * If the news entry is found, its headline, description, and publication time are updated.
     *
     * @param id the ID of the news entry to update
     * @param news the updated News object containing the new data
     */
    public void updateNews(Long id, News news) {
        var existingNews = this.newsRepository.findByIdOrThrowException(id);

        existingNews.setHeadline(news.getHeadline());
        existingNews.setDescription(news.getDescription());
        existingNews.setPublicationTime(news.getPublicationTime());
        this.newsRepository.save(existingNews);
    }

    /**
     * Deletes a news entry from the repository by its ID.
     *
     * @param id the ID of the news entry to delete
     */
    public void deleteNews(Long id) {
        this.newsRepository.deleteById(id);
    }
}
