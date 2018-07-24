package com.mike.consdata.news;

import com.mike.consdata.integration.api.NewsApiServiceProvider;
import com.mike.consdata.news.converters.NewsApiResponseToNewsConverter;
import com.mike.consdata.news.io.NewsRequest;
import com.mike.consdata.news.io.NewsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NewsService {

    @Autowired
    private NewsApiServiceProvider newsApiServiceProvider;

    @Autowired
    private NewsApiResponseToNewsConverter newsApiResponseToNewsConverter;

    @Value("${api.news.key}")
    private String apiKey;

    public NewsResponse getAllNews(NewsRequest newsRequest) {
        try {
            return Optional.ofNullable(newsApiServiceProvider
                    .getTopHeadlines(apiKey, newsRequest.getCountry(), newsRequest.getCategory()))
                    .map(newsApiResponse -> {
                        log.info("News api response = {}", newsApiResponse.toString());
                        log.info("News api is not null ? {}", newsApiResponseToNewsConverter != null);
                        return newsApiResponseToNewsConverter.convert(newsApiResponse, newsRequest);
                    })
                    .orElseThrow(IllegalArgumentException::new);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return NewsResponse.builder().build();
    }

    public List<String> getAllCategories() {
        return Arrays.asList("business", "entertainment", "general", "health", "science", "sports", "technology");
    }
}
