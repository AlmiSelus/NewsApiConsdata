package com.mike.consdata.integration.api;

import com.mike.consdata.integration.api.io.NewsApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NewsApiServiceProvider {

    private static final String NEWS_API_BASE_URL = "https://newsapi.org";
    private static final String NEWS_API_TOP_HEADLINES_ENDPOINT = "/v2/top-headlines";

    public NewsApiResponse getTopHeadlines(String apiKey, String country, String category) {
        RestTemplate restTemplate = new RestTemplate();
        String topHeadlinesUrl = String.format("%s%s?apiKey=%s&country=%s&category=%s",
                NEWS_API_BASE_URL,
                NEWS_API_TOP_HEADLINES_ENDPOINT,
                apiKey,
                country,
                category);
        return restTemplate.getForObject(topHeadlinesUrl, NewsApiResponse.class);
    }

}
