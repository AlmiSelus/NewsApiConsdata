package com.mike.consdata.news.converters;

import com.mike.consdata.integration.api.io.NewsApiArticle;
import com.mike.consdata.news.io.Article;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class NewsApiArticleToArticleConverter implements Converter<NewsApiArticle, Article> {
    @Override
    public Article convert(NewsApiArticle source) {
        return Article.builder()
                .author(source.getAuthor())
                .articleUrl(source.getUrl())
                .date(ZonedDateTime.parse(source.getPublishedAt()).toLocalDate())
                .description(source.getDescription())
                .imageUrl(source.getUrlToImage())
                .sourceName(source.getSource().getName())
                .title(source.getTitle())
                .build();
    }
}
