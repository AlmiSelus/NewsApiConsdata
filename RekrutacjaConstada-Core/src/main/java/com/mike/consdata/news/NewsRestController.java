package com.mike.consdata.news;

import com.mike.consdata.news.io.NewsRequest;
import com.mike.consdata.news.io.NewsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;

@Slf4j
@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "http://localhost:8080")
public class NewsRestController {

    private Predicate<String> errorResponsePredicate = (t) -> t != null && !t.isEmpty();

    private final NewsService newsService;

    @Autowired
    public NewsRestController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(newsService.getAllCategories());
    }

    @GetMapping("/{country}/{category}/{page}")
    public ResponseEntity<NewsResponse> findAllNews(@PathVariable("country") String country,
                                                    @PathVariable("category") String category,
                                                    @PathVariable("page") Integer page,
                                                    @RequestParam(value = "query", required = false) String query) {
        log.info("Query = {}", query);
        NewsRequest newsRequest =
                NewsRequest.builder().category(category).country(country).page(page == null ? 1 : page).query(query).build();
        NewsResponse newsResponse = newsService.getNewsList(newsRequest);

        log.info("Response error = {}", newsResponse.getError());

        ResponseEntity.BodyBuilder response = errorResponsePredicate.test(newsResponse.getError()) ?
                ResponseEntity.badRequest() :
                ResponseEntity.status(HttpStatus.OK);

        return response.body(newsResponse);
    }
}