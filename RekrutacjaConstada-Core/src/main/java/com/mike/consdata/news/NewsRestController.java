package com.mike.consdata.news;

import com.mike.consdata.news.io.NewsRequest;
import com.mike.consdata.news.io.NewsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("/news")
public class NewsRestController {

    private Predicate<String> errorResponsePredicate = (t) -> t == null || t.isEmpty();

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
    public ResponseEntity<NewsResponse> findAllNews(@PathVariable("country") String country,
                                                    @PathVariable("category") String category,
                                                    @RequestParam(value = "page", required = false) Integer page) {
        NewsRequest newsRequest =
                NewsRequest.builder().category(category).country(country).page(page == null ? 1 : page).build();
        NewsResponse newsResponse = newsService.getAllNews(newsRequest);

        ResponseEntity.BodyBuilder response = errorResponsePredicate.test(newsResponse.getError()) ?
                ResponseEntity.badRequest() : ResponseEntity.status(HttpStatus.OK);

        return response.body(newsResponse);
    }
}