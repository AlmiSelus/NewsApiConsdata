package com.mike.consdata.news;

import com.mike.consdata.integration.api.NewsApiServiceProvider;
import com.mike.consdata.integration.api.io.NewsApiArticle;
import com.mike.consdata.integration.api.io.NewsApiResponse;
import com.mike.consdata.integration.api.io.NewsApiSource;
import com.mike.consdata.news.io.Article;
import com.mike.consdata.news.io.NewsRequest;
import com.mike.consdata.news.io.NewsResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NewsServiceTests {

    @MockBean
    private NewsApiServiceProvider newsApiServiceProvider;

    @Autowired
    private NewsService newsService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetNews_allDataOk_shouldReturnMockedNews() {
        NewsApiArticle newsApiArticle = NewsApiArticle.builder()
                .author("Author")
                .description("description")
                .source(NewsApiSource.builder().name("source").id("id").build())
                .publishedAt("2017-07-01T01:01:01Z")
                .title("Title")
                .url("http://getUrl.test.com")
                .urlToImage("http://getUrl.test.com/img.1.png")
                .build();

        NewsApiResponse newsApiResponse = NewsApiResponse.builder()
                .status("ok")
                .article(newsApiArticle)
                .build();

        when(newsApiServiceProvider.getTopHeadlines(any())).thenReturn(newsApiResponse);

        NewsResponse news = newsService.getNewsList(NewsRequest.builder().category("technology").country("pl").page(1).build());

        assertThat(news, is(notNullValue()));
        assertThat(news.getCountry(), is("pl"));
        assertThat(news.getCategory(), is("technology"));
        assertThat(news.getArticles(), hasSize(1));
        Article article = news.getArticles().get(0);
        assertThat(article, is(notNullValue()));
        assertThat(article.getAuthor(), is(newsApiArticle.getAuthor()));
        assertThat(article.getDescription(), is(newsApiArticle.getDescription()));
        assertThat(article.getSourceName(), is(newsApiArticle.getSource().getName()));
        assertThat(article.getArticleUrl(), is(newsApiArticle.getUrl()));
        assertThat(article.getDate(), is(ZonedDateTime.parse(newsApiArticle.getPublishedAt()).toLocalDate()));
        assertThat(article.getImageUrl(), is(newsApiArticle.getUrlToImage()));
        assertThat(article.getTitle(), is(newsApiArticle.getTitle()));
    }

    @Test
    public void testGetNews_dataOkServiceReturnsErrorInvalidApiKey_shouldReturnErrorMockedResponse() {
        NewsApiResponse newsApiResponse = NewsApiResponse.builder()
                .code("apiKeyInvalid")
                .status("error")
                .message("Your API key is invalid or incorrect. Check your key, or go to https://newsapi.org to create a free API key.")
                .build();

        when(newsApiServiceProvider.getTopHeadlines(any())).thenReturn(newsApiResponse);

        NewsResponse news = newsService.getNewsList(NewsRequest.builder().category("technology").country("pl").build());

        assertThat(news, is(notNullValue()));
        assertThat(news.getError(), is(newsApiResponse.getMessage())); //forward message from external service
    }

    @Test
    public void testGetNews_passedNullCountry_shouldReturnErrorMockedResponse() {
        NewsResponse news = newsService.getNewsList(NewsRequest.builder().category("technology").country(null).build());

        assertThat(news, is(notNullValue()));
        assertThat(news.getError(), is("Country is null"));
    }

    @Test
    public void testGetNews_passedNullCategory_shouldReturnErrorMockedResponse() {
        NewsResponse news = newsService.getNewsList(NewsRequest.builder().category(null).country("pl").build());

        assertThat(news, is(notNullValue()));
        assertThat(news.getError(), is("Category is null"));
    }

    @Test
    public void testGetNews_passedEmptyCountry_shouldReturnErrorMockedResponse() {
        NewsResponse news = newsService.getNewsList(NewsRequest.builder().category("technology").country("").build());

        assertThat(news, is(notNullValue()));
        assertThat(news.getError(), is("Country is empty"));
    }

    @Test
    public void testGetNews_passedEmptyCategory_shouldReturnErrorMockedResponse() {
        NewsResponse news = newsService.getNewsList(NewsRequest.builder().category("").country("pl").build());

        assertThat(news, is(notNullValue()));
        assertThat(news.getError(), is("Category is empty"));
    }
}
