package com.mike.consdata.integration.api;

import com.mike.consdata.integration.api.endpoints.EndpointEnum;
import com.mike.consdata.integration.api.endpoints.NewsEndpoint;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

public class NewsManager {

    private static final String NEWS_ENDPOINT_NULL_MSG = "News endpoint is null!";
    private static final String API_KEY_QUERY_PARAM = "apiKey";
    private static final String API_KEY_NULL_OR_EMPTY_MSG = "Api key is empty or null!";

    private String apiKey;
    private NewsEndpoint newsEndpoint;
    private QueryParamsMap queryParamsMap;

    public NewsManager(String apiKey) {
        if(apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException(API_KEY_NULL_OR_EMPTY_MSG);
        }

        this.apiKey = apiKey;
    }

    public NewsManager registerEndpoint(NewsEndpoint newsEndpoint) {
        if(newsEndpoint == null) {
            throw new IllegalArgumentException(NEWS_ENDPOINT_NULL_MSG);
        }
        this.newsEndpoint = newsEndpoint;
        return this;
    }

    public NewsManager registerEndpoint(EndpointEnum endpointEnum) {
        return registerEndpoint(endpointEnum.getNewsEndpoint());
    }

    public NewsManager registerQueryParameters(QueryParamsMap queryParamsMap) {
        this.queryParamsMap = queryParamsMap != null ? queryParamsMap : new QueryParamsMap();
        this.queryParamsMap.put(API_KEY_QUERY_PARAM, this.apiKey);
        return this;
    }

    public <T> T call(RestTemplate restTemplate, Class<? extends T> responseClass) {

        if(newsEndpoint == null) {
            throw new IllegalArgumentException(NEWS_ENDPOINT_NULL_MSG);
        }

        String queryParameters = this.queryParamsMap.toString();
        String url = newsEndpoint.getUrl() + (queryParameters.isEmpty() ? StringUtils.EMPTY : "?" + queryParameters);
        return restTemplate.getForObject(url, responseClass);
    }

}
