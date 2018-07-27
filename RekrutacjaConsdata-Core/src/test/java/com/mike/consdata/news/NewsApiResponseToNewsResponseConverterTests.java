package com.mike.consdata.news;

import com.mike.consdata.integration.api.io.NewsApiArticle;
import com.mike.consdata.integration.api.io.NewsApiResponse;
import com.mike.consdata.news.converters.NewsApiResponseToNewsConverter;
import com.mike.consdata.news.io.NewsRequest;
import com.mike.consdata.news.io.NewsResponse;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.ZonedDateTime;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NewsApiResponseToNewsResponseConverterTests {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private NewsApiResponseToNewsConverter newsApiResponseToNewsConverter;

    @Value("${news.results.pageSize}")
    private long perPage;

    @Test
    public void testConvert_passedCorrectObject_shouldReturnNonNullValue() {
        NewsApiResponse newsApiResponse = NewsApiResponse.builder()
                .message("Message")
                .status("ok")
                .code("code")
                .article(NewsApiArticle.builder().build())
                .totalResults(1L)
                .build();
        NewsRequest newsRequest = NewsRequest.builder().page(1).category("technology").category("pl").build();

        NewsResponse newsResponse = newsApiResponseToNewsConverter.convert(newsApiResponse, newsRequest);
        assertThat(newsResponse, is(notNullValue()));
        assertThat(newsResponse.getArticles(), IsCollectionWithSize.hasSize(1));
        assertThat(new Long(newsResponse.getTotalPages()), isLong(Math.ceil(1/(double)perPage)));
        assertThat(newsResponse.getPage(), is(newsRequest.getPage().longValue()));
        assertThat(newsResponse.getError(), is(nullValue()));
        assertThat(newsResponse.getCountry(), is(newsRequest.getCountry()));
        assertThat(newsResponse.getCategory(), is(newsRequest.getCategory()));
    }

    @Test
    public void testConvert_passedNullNewsApiResponse_shouldThrowIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Input data is null!");
        newsApiResponseToNewsConverter.convert(null, NewsRequest.builder().build());
    }

    @Test
    public void testConvert_passedNullNewsRequest_shouldThrowIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Input data is null!");
        newsApiResponseToNewsConverter.convert(NewsApiResponse.builder().build(), null);
    }
    public static Matcher<Long> isLong(Double value) {
        return org.hamcrest.core.Is.is(value.longValue());
    }
}
