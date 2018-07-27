package com.mike.consdata.news;

import com.mike.consdata.integration.api.io.NewsApiArticle;
import com.mike.consdata.integration.api.io.NewsApiSource;
import com.mike.consdata.news.converters.NewsApiArticleToArticleConverter;
import com.mike.consdata.news.converters.NewsApiResponseToNewsConverter;
import com.mike.consdata.news.io.Article;
import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.ZonedDateTime;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsApiArticleToArticleConverterTests {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private NewsApiArticleToArticleConverter apiArticleToArticleConverter;

    @Test
    public void testConvert_passEmptyObject_shouldReturnEmptyObject() {
        NewsApiArticle newsApiArticle = NewsApiArticle.builder().build();

        Article article = apiArticleToArticleConverter.convert(newsApiArticle);

        assertThat(article, is(notNullValue()));
        assertThat(article.getTitle(), is(nullValue()));
        assertThat(article.getImageUrl(), is(nullValue()));
        assertThat(article.getArticleUrl(), is(nullValue()));
        assertThat(article.getDate(), is(nullValue()));
        assertThat(article.getSourceName(), is(nullValue()));
        assertThat(article.getAuthor(), is(nullValue()));
        assertThat(article.getDescription(), is(nullValue()));
    }

    @Test
    public void testConvert_passedCorrectObject_shouldReturnNonNullValue() {
        NewsApiArticle newsApiArticle = NewsApiArticle.builder()
                .urlToImage("http://someUrl.com/test.jpg")
                .url("http://someUrl.com/")
                .title("title")
                .publishedAt("2018-08-01T13:12:00Z")
                .source(NewsApiSource.builder().id("SourceId").name("Source Name").build())
                .author("Author")
                .description("Descr")
                .build();

        Article article = apiArticleToArticleConverter.convert(newsApiArticle);
        assertThat(article, is(notNullValue()));
        assertThat(article.getTitle(), is(newsApiArticle.getTitle()));
        assertThat(article.getImageUrl(), is(newsApiArticle.getUrlToImage()));
        assertThat(article.getArticleUrl(), is(newsApiArticle.getUrl()));
        assertThat(article.getDate(), is(ZonedDateTime.parse(newsApiArticle.getPublishedAt()).toLocalDate()));
        assertThat(article.getSourceName(), is(newsApiArticle.getSource().getName()));
        assertThat(article.getAuthor(), is(newsApiArticle.getAuthor()));
        assertThat(article.getDescription(), is(newsApiArticle.getDescription()));
    }

    @Test
    public void testConvert_passedNull_shouldThrowIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Passed null api response object!");
        apiArticleToArticleConverter.convert(null);
    }
}
