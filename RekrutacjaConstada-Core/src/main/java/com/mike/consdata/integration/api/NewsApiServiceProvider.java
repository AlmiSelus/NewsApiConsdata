package com.mike.consdata.integration.api;

import com.mike.consdata.integration.api.converters.NewsApiResponseToQueryParamsConverter;
import com.mike.consdata.integration.api.endpoints.BaseNewsEndpoint;
import com.mike.consdata.integration.api.endpoints.EndpointEnum;
import com.mike.consdata.integration.api.endpoints.TopNewsEndpoint;
import com.mike.consdata.integration.api.io.NewsApiRequest;
import com.mike.consdata.integration.api.io.NewsApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class NewsApiServiceProvider {

    @Autowired
    private NewsApiResponseToQueryParamsConverter newsApiResponseToQueryParamsConverter;

    @Autowired
    private RestTemplate restTemplate;

    public NewsApiResponse getTopHeadlines(NewsApiRequest newsApiRequest) {
        NewsManager newsManager = new NewsManager(newsApiRequest.getApiKey())
                .registerEndpoint(EndpointEnum.TOP_HEADLINES)
                .registerQueryParameters(newsApiResponseToQueryParamsConverter.convert(newsApiRequest));
        return newsManager.call(restTemplate, NewsApiResponse.class);
    }

}
