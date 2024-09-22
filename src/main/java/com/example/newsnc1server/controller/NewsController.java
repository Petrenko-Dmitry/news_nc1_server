package com.example.newsnc1server.controller;

import com.example.newsnc1server.dto.NewsDTO;
import com.example.newsnc1server.entity.News;
import com.example.newsnc1server.enums.TimeOfDay;
import com.example.newsnc1server.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping
    public List<NewsDTO> getAllNews() {
        return this.newsService.getAllNews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable Long id) {
        return ResponseEntity.of(this.newsService.getNewsById(id));
    }

    @GetMapping("/filter/{timeOfDay}")
    public List<NewsDTO> findNewsByTimeOfDay(@PathVariable TimeOfDay timeOfDay) {
        return this.newsService.findAllNewsByTimeOfDay(timeOfDay);
    }

    @PostMapping("/{id}/update")
    public void updateNews(@PathVariable Long id, @RequestBody News news) {
        this.newsService.updateNews(id, news);
    }

    @PostMapping("/{id}/delete")
    public void deleteNews(@PathVariable Long id) {
        this.newsService.deleteNews(id);
    }
}
