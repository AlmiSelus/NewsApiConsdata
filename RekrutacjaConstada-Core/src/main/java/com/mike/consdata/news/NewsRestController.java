package com.mike.consdata.news;

import com.mike.consdata.news.io.NewsResponse;
import com.mike.consdata.news.io.NewsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsRestController {

    private final NewsService newsService;

    @Autowired
    public NewsRestController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(newsService.getAllCategories());
    }

    @GetMapping("/{country}/{category}")
    public ResponseEntity<NewsResponse> findAllNews(@PathVariable("country") String country, @PathVariable("category") String category) {
        return ResponseEntity.ok(newsService.getAllNews(NewsRequest.builder().category(category).country(country).build()));
    }

}
