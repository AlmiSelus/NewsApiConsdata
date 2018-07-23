package com.mike.consdata.news;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NewsApiResponseToNewsConverter implements Converter<NewsApiResponse, News> {
    @Override
    public News convert(NewsApiResponse source) {
        return null;
    }
}
