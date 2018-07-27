package com.mike.consdata.news.converters;

import com.mike.consdata.integration.api.io.NewsApiArticle;
import com.mike.consdata.news.io.Article;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class NewsApiArticleToArticleConverter implements Converter<NewsApiArticle, Article> {
    private static final String NULL_INPUT_MESSAGE = "Passed null api response object!";

    @Override
    public Article convert(NewsApiArticle source) {
        if(source == null) {
            throw new IllegalArgumentException(NULL_INPUT_MESSAGE);
        }
        Article.ArticleBuilder articleBuilder = Article.builder()
                .author(source.getAuthor())
                .articleUrl(source.getUrl())
                .description(source.getDescription())
                .imageUrl(source.getUrlToImage())
                .title(source.getTitle());

        if(source.getPublishedAt() != null && !source.getPublishedAt().isEmpty()) {
            articleBuilder = articleBuilder.date(ZonedDateTime.parse(source.getPublishedAt()).toLocalDate());
        }

        if(source.getSource() != null) {
            articleBuilder = articleBuilder.sourceName(source.getSource().getName());
        }

        return articleBuilder.build();
    }
}
