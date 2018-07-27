package com.mike.consdata.integration.api.converters;

import com.mike.consdata.integration.api.QueryParamsMap;
import com.mike.consdata.integration.api.io.NewsApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NewsApiResponseToQueryParamsConverter implements Converter<NewsApiRequest, QueryParamsMap> {

    private static final String API_KEY_QUERY_PARAM = "apiKey";
    private static final String COUNTRY_QUERY_PARAM = "country";
    private static final String CATEGORY_QUERY_PARAM = "category";
    private static final String PAGE_SIZE_QUERY_PARAM = "pageSize";
    private static final String PAGE_QUERY_PARAM = "page";
    private static final String QUERY_SEARCH_QUERY_PARAM = "q";

    @Override
    public QueryParamsMap convert(NewsApiRequest source) {
        QueryParamsMap queryParams = new QueryParamsMap();
        queryParams.put(API_KEY_QUERY_PARAM, source.getApiKey());
        queryParams.put(COUNTRY_QUERY_PARAM, source.getCountry());
        queryParams.put(CATEGORY_QUERY_PARAM, source.getCategory());
        queryParams.put(PAGE_SIZE_QUERY_PARAM, Long.toString(source.getResultsPerPage()));
        queryParams.put(PAGE_QUERY_PARAM, Long.toString(source.getPage()));

        if(source.getQuery() != null && !source.getQuery().isEmpty()) {
            log.info("Adding query information to request!");
            queryParams.put(QUERY_SEARCH_QUERY_PARAM, source.getQuery());
        }
        return queryParams;
    }
}
