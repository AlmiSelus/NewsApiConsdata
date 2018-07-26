package com.mike.consdata.news.converters;

import com.mike.consdata.integration.api.io.NewsApiResponse;
import com.mike.consdata.news.io.NewsResponse;
import com.mike.consdata.news.io.NewsRequest;
import com.mike.consdata.utils.BiConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class NewsApiResponseToNewsConverter implements BiConverter<NewsApiResponse, NewsRequest, NewsResponse> {

    @Autowired
    private NewsApiArticleToArticleConverter newsApiArticleToArticleConverter;

    @Value("${news.results.pageSize}")
    private int resultsPerPage;

    @Override
    public NewsResponse convert(NewsApiResponse newsApiResponse, NewsRequest newsRequest) {
        log.info("Converting response");
        return NewsResponse.builder()
                .articles(newsApiResponse.getArticles().stream().map(newsApiArticleToArticleConverter::convert).collect(Collectors.toList()))
                .category(newsRequest.getCategory())
                .country(newsRequest.getCountry())
                .page(newsRequest.getPage())
                .totalPages((long) Math.ceil((double)newsApiResponse.getTotalResults() / resultsPerPage))
                .build();
    }
}
