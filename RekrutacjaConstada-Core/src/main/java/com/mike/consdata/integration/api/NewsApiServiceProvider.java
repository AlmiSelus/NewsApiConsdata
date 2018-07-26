package com.mike.consdata.integration.api;

import com.mike.consdata.integration.api.io.NewsApiRequest;
import com.mike.consdata.integration.api.io.NewsApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class NewsApiServiceProvider {

    private static final String NEWS_API_BASE_URL = "https://newsapi.org";
    private static final String NEWS_API_TOP_HEADLINES_ENDPOINT = "/v2/top-headlines";

    public NewsApiResponse getTopHeadlines(NewsApiRequest newsApiRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = NEWS_API_BASE_URL + NEWS_API_TOP_HEADLINES_ENDPOINT;
        QueryParamsMap queryParams = prepareQueryParamsFromNewsRequest(newsApiRequest);
        String restUrl = baseUrl + "?" + queryParams.toString();
        log.info("Calling {}", restUrl);
        return restTemplate.getForObject(restUrl, NewsApiResponse.class);
    }

    private QueryParamsMap prepareQueryParamsFromNewsRequest(NewsApiRequest newsApiRequest) {
        QueryParamsMap queryParams = new QueryParamsMap();
        queryParams.put("apiKey", newsApiRequest.getApiKey());
        queryParams.put("country", newsApiRequest.getCountry());
        queryParams.put("category", newsApiRequest.getCategory());
        queryParams.put("pageSize", Long.toString(newsApiRequest.getResultsPerPage()));
        queryParams.put("page", Long.toString(newsApiRequest.getPage()));
        return queryParams;
    }

    private class QueryParamsMap extends HashMap<String, Object> {
        private static final String QUERY_PARAM_KEY_VALUE_SEPARATOR = "=";
        private static final String QUERY_PARAMS_SEPARATOR = "&";

        @Override
        public String toString() {
            if(entrySet().isEmpty()) {
                return StringUtils.EMPTY;
            }

            return entrySet().stream()
                    .map(entry->entry.getKey() + QUERY_PARAM_KEY_VALUE_SEPARATOR + entry.getValue().toString())
                    .collect(Collectors.joining(QUERY_PARAMS_SEPARATOR));
        }
    }

}
