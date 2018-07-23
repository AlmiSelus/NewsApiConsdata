package com.mike.consdata.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class NewsService {

    @Autowired
    private NewsApiServiceProvider newsApiServiceProvider;

    @Autowired
    private NewsApiResponseToNewsConverter newsApiResponseToNewsConverter;

    @Value("${api.news.key}")
    private String apiKey;

    public List<News> getAllNews(NewsRequest newsRequest) {
        try {
            return Objects.requireNonNull(newsApiServiceProvider
                    .listApiResponses(apiKey, newsRequest.getCountry(), newsRequest.getCategory())
                    .execute()
                    .body())
                    .stream()
                    .map(newsApiResponseToNewsConverter::convert)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<String> getAllCategories() {
        return Arrays.asList("business", "entertainment", "general", "health", "science", "sports", "technology");
    }
}
