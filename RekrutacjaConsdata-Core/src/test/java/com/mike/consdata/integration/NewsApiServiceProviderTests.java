package com.mike.consdata.integration;

import com.mike.consdata.integration.api.NewsApiServiceProvider;
import com.mike.consdata.integration.api.io.NewsApiArticle;
import com.mike.consdata.integration.api.io.NewsApiRequest;
import com.mike.consdata.integration.api.io.NewsApiResponse;
import com.mike.consdata.integration.api.io.NewsApiSource;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NewsApiServiceProviderTests {

    @Autowired
    private NewsApiServiceProvider newsApiServiceProvider;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Before
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void callGetTopHeadlines_callWithParameters_shouldReturnObjectWithoutArticles() {
        String mockJsonResponse = "{" +
                "\"status\": \"ok\"," +
                "\"totalResults\": 0," +
                "\"articles\": []}";
        mockServer.expect(requestTo(
                "https://newsapi.org/v2/top-headlines?apiKey=apiKey&category=entertainment&country=pl&page=1&pageSize=10&q=title"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mockJsonResponse, MediaType.APPLICATION_JSON_UTF8));

        NewsApiRequest newsApiRequest = NewsApiRequest.builder().query("title")
                .resultsPerPage(10)
                .page(1)
                .country("pl")
                .category("entertainment")
                .apiKey("apiKey")
                .build();

        NewsApiResponse newsApiResponse = newsApiServiceProvider.getTopHeadlines(newsApiRequest);
        mockServer.verify();

        assertThat(newsApiResponse, is(IsNull.notNullValue()));
        assertThat(newsApiResponse.getTotalResults(), is(0L));
        assertThat(newsApiResponse.getArticles(), IsCollectionWithSize.hasSize(0));
        assertThat(newsApiResponse.getStatus(), is("ok"));
    }

    @Test
    public void callGetTopHeadlines_callWithApiKeyButNoOtherData_shouldReturnErrorResponseWithParametersMissing() {
        String mockJsonResponse = "{" +
            "\"status\": \"error\"," +
            "\"code\": \"parametersMissing\"," +
            "\"message\": \"Required parameters are missing. Please set any of the following parameters and try again: sources, q, language, country, category.\"" +
        "}";
        mockServer.expect(requestTo(
                "https://newsapi.org/v2/top-headlines?apiKey=api"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mockJsonResponse, MediaType.APPLICATION_JSON_UTF8));

        NewsApiRequest newsApiRequest = NewsApiRequest.builder()
                .apiKey("api")
                .build();

        NewsApiResponse newsApiResponse = newsApiServiceProvider.getTopHeadlines(newsApiRequest);
        mockServer.verify();

        assertThat(newsApiResponse, is(IsNull.notNullValue()));
        assertThat(newsApiResponse.getMessage(), is("Required parameters are missing. Please set any of the following parameters and try again: sources, q, language, country, category."));
        assertThat(newsApiResponse.getStatus(), is("error"));
        assertThat(newsApiResponse.getCode(), is("parametersMissing"));
    }

    @Test
    public void callGetTopHeadlines_callWithoutApiKey_shouldReturnErrorFields() {
        String mockJsonResponse = "{" +
                "\"status\": \"error\"," +
                "\"code\": \"apiKeyMissing\"," +
                "\"message\": \"Your API key is missing. Append this to the URL with the apiKey param, or use the x-api-key HTTP header.\"" +
                "}";

        mockServer.expect(requestTo(
                "https://newsapi.org/v2/top-headlines?country=pl"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mockJsonResponse, MediaType.APPLICATION_JSON_UTF8));

        NewsApiRequest newsApiRequest = NewsApiRequest.builder()
                .country("pl")
                .build();

        NewsApiResponse newsApiResponse = newsApiServiceProvider.getTopHeadlines(newsApiRequest);
        mockServer.verify();

        assertThat(newsApiResponse, is(IsNull.notNullValue()));
        assertThat(newsApiResponse.getMessage(), is("Your API key is missing. Append this to the URL with the apiKey param, or use the x-api-key HTTP header."));
        assertThat(newsApiResponse.getStatus(), is("error"));
        assertThat(newsApiResponse.getCode(), is("apiKeyMissing"));
    }

    @Test
    public void callGetTopHeadlines_callWithAllParams_shouldReturnFullResponse() {
        String mockJsonResponse = "{" +
            "\"status\": \"ok\"," +
            "\"totalResults\": 1," +
            "\"articles\": [" +
                "{" +
                "\"source\": {" +
                    "\"id\": null," +
                    "\"name\": \"test\"" +
                    "}," +
                "\"author\": \"Author\"," +
                "\"title\": \"title\"," +
                "\"description\": \"description\"," +
                "\"url\": \"http://getUrl.pl/test\"," +
                "\"urlToImage\": \"http://getUrl.pl/img.jpg\"," +
                "\"publishedAt\": \"2018-07-27T16:22:05Z\"" +
                "}"+
            "]}";
        mockServer.expect(requestTo(
                "https://newsapi.org/v2/top-headlines?apiKey=apiKey&category=entertainment&country=pl&page=1&pageSize=10&q=title"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mockJsonResponse, MediaType.APPLICATION_JSON_UTF8));

        NewsApiRequest newsApiRequest = NewsApiRequest.builder().query("title")
                .resultsPerPage(10)
                .page(1)
                .country("pl")
                .category("entertainment")
                .apiKey("apiKey")
                .build();

        NewsApiResponse newsApiResponse = newsApiServiceProvider.getTopHeadlines(newsApiRequest);
        mockServer.verify();

        assertThat(newsApiResponse, is(IsNull.notNullValue()));
        assertThat(newsApiResponse.getMessage(), is(IsNull.nullValue()));
        assertThat(newsApiResponse.getTotalResults(), is(1L));
        assertThat(newsApiResponse.getArticles(), IsCollectionWithSize.hasSize(1));

        NewsApiArticle newsApiArticle = newsApiResponse.getArticles().get(0);
        assertThat(newsApiArticle.getAuthor(), is("Author"));
        assertThat(newsApiArticle.getDescription(), is("description"));
        assertThat(newsApiArticle.getTitle(), is("title"));
        assertThat(newsApiArticle.getUrl(), is("http://getUrl.pl/test"));
        assertThat(newsApiArticle.getUrlToImage(), is("http://getUrl.pl/img.jpg"));
        assertThat(newsApiArticle.getPublishedAt(), is("2018-07-27T16:22:05Z"));
        assertThat(newsApiArticle.getSource(), is(IsNull.notNullValue()));

        NewsApiSource source = newsApiArticle.getSource();
        assertThat(source.getName(), is("test"));
        assertThat(source.getId(), is(IsNull.nullValue()));
    }
}
