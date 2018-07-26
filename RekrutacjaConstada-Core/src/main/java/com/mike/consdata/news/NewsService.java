package com.mike.consdata.news;

import com.mike.consdata.integration.api.NewsApiServiceProvider;
import com.mike.consdata.integration.api.io.NewsApiRequest;
import com.mike.consdata.integration.api.io.NewsApiResponse;
import com.mike.consdata.news.converters.NewsApiResponseToNewsConverter;
import com.mike.consdata.news.io.NewsRequest;
import com.mike.consdata.news.io.NewsResponse;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.rest.core.ValidationErrors;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@Slf4j
@Service
public class NewsService {

    @Autowired
    private NewsApiServiceProvider newsApiServiceProvider;

    @Autowired
    private NewsApiResponseToNewsConverter newsApiResponseToNewsConverter;

    @Autowired
    private NewsRequestValidator newsRequestValidator;

    @Value("${api.news.key}")
    private String apiKey;

    @Value("${news.results.pageSize}")
    private long resultsPerPage;

    public NewsResponse getAllNews(NewsRequest newsRequest) {
        Errors errors = new MapBindingResult(new HashMap<>(), "NewsValidationResult");
        newsRequestValidator.validate(newsRequest, errors);

        if(errors.hasErrors()) {
            return NewsResponse.builder().error(errors.getAllErrors().get(0).getCode()).build();
        }

        NewsApiRequest newsApiRequest = NewsApiRequest.builder()
                .apiKey(apiKey)
                .category(newsRequest.getCategory())
                .country(newsRequest.getCountry())
                .page(newsRequest.getPage())
                .resultsPerPage(resultsPerPage)
                .build();


        NewsApiResponse newsApiResponse = Try.of(()->Optional.ofNullable(newsApiServiceProvider
                .getTopHeadlines(newsApiRequest)).orElseThrow(IllegalAccessError::new)).getOrElseThrow(() -> new IllegalArgumentException());

        log.info("Status = {}", newsApiResponse.getStatus());

        return Match(newsApiResponse.getStatus()).of(
                Case($("error"), () -> NewsResponse.builder().error(newsApiResponse.getMessage()).build()),
                Case($(), () -> newsApiResponseToNewsConverter.convert(newsApiResponse, newsRequest))
        );
    }

    public List<String> getAllCategories() {
        return Arrays.asList("business", "entertainment", "general", "health", "science", "sports", "technology");
    }
}
