package com.mike.consdata.integration.api;

import com.mike.consdata.integration.api.converters.NewsApiResponseToQueryParamsConverter;
import com.mike.consdata.integration.api.io.NewsApiRequest;
import com.mike.consdata.integration.api.io.NewsApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class NewsApiServiceProvider {

    private static final String NEWS_API_BASE_URL = "https://newsapi.org";
    private static final String NEWS_API_TOP_HEADLINES_ENDPOINT = "/v2/top-headlines";

    @Autowired
    private NewsApiResponseToQueryParamsConverter newsApiResponseToQueryParamsConverter;

    private RestTemplate restTemplate = new RestTemplate();

    public NewsApiResponse getTopHeadlines(NewsApiRequest newsApiRequest) {
        String restUrl = createRestUrl(newsApiRequest);
        log.info("Calling url {}", restUrl);
        return restTemplate.getForObject(restUrl, NewsApiResponse.class);
    }

    private String createRestUrl(NewsApiRequest newsApiRequest) {
        String baseUrl = NEWS_API_BASE_URL + NEWS_API_TOP_HEADLINES_ENDPOINT;
        QueryParamsMap queryParams = newsApiResponseToQueryParamsConverter.convert(newsApiRequest);
        return baseUrl + "?" + queryParams.toString();
    }

}
