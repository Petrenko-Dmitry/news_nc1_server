package com.example.newsnc1server.dto;

import com.example.newsnc1server.entity.News;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public record NewsDTO(
        Long id,
        String headline,
        String description,
        String publicationTime
) {

    public NewsDTO(News news) {
        this(
                news.getId(),
                news.getHeadline(),
                news.getDescription(),
                String.valueOf(news.getPublicationTime())
        );
    }

    public static List<NewsDTO> parseNews(Collection<? extends News> news) {
        return news.stream()
                .map(NewsDTO::new)
                .collect(Collectors.toList());
    }
}
