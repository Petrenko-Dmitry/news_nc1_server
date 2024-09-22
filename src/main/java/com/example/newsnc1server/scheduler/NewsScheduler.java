package com.example.newsnc1server.scheduler;

import com.example.newsnc1server.entity.News;
import com.example.newsnc1server.service.NewsService;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.example.newsnc1server.constants.StringConstants.*;

@Slf4j
@Component
public class NewsScheduler {

    @Autowired
    private NewsService newsService;

    private static final AtomicBoolean IS_COMPLETE = new AtomicBoolean(true);

    /**
     * Scheduled task that fetches news every 20 minutes.
     * It checks if the previous fetching task is complete before proceeding.
     * Fetches and parses the news from the specified source, then adds the news to the service.
     * If an exception occurs during the process, it logs an error message.
     */
    @Scheduled(cron = "* */20 * * * *")
    public void fetchNews() {
        try {
            if (IS_COMPLETE.get()) {
                IS_COMPLETE.set(false);
                var news = this.parseNews();
                this.newsService.addNews(news);
                IS_COMPLETE.set(true);
            }
        } catch (Exception e) {
            log.error(String.format(FAILED_FETCH_NEWS_FORMAT, e.getMessage()));
        }
    }

    /**
     * Parses news from the specified website.
     * Connects to the site, selects the news elements based on the provided CSS class,
     * and processes each element to create a set of News objects.
     * If an error occurs while parsing the website, it throws a runtime exception.
     *
     * @return a set of News objects parsed from the website
     */
    private Set<News> parseNews() {
        try {
            var doc = Jsoup.connect(NEWS_URL).get();
            var items = doc.select(NEWS_CLASS_ON_SITE);
            return items.stream()
                    .map(item -> {
                        var titleContainer = item.select(NEWS_TITLE_CLASS_ON_SITE);
                        var link = NEWS_URL + titleContainer.attr(NEWS_LINK_ON_SITE);
                        return findDescriptionAndPublicationDate(new News(titleContainer.text()), link);
                    })
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Finds and sets the description and publication date for a given news item.
     * Connects to the provided link, extracts the description and publication time using CSS selectors,
     * and updates the News object with this information.
     * If an error occurs while fetching the details, it logs an error message.
     *
     * @param news the News object to update with the description and publication date
     * @param link the URL to the specific news article
     * @return the updated News object with the description and publication time
     */
    private News findDescriptionAndPublicationDate(News news, String link) {
        try {
            var doc = Jsoup.connect(link).get();
            var description = doc.select(NEWS_DESCRIPTION_CLASS_ON_SITE).text();
            var time = doc.select(NEWS_PUBLICATION_TIME_CLASS_ON_SITE).attr(NEWS_PUBLICATION_TIME_VALUE_ON_SITE);
            news.setDescription(description.replaceAll(TEXT_TO_REMOVE, EMPTY));
            news.setPublicationTime(LocalDateTime.ofInstant(Instant.parse(time), ZoneId.systemDefault()));
        } catch (Exception e) {
            log.error(String.format(FAILED_FINDING_DESCRIPTION_FORMAT, e.getMessage()));
        }
        return news;
    }
}
