package com.mike.consdata.news;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsRestControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Value("${api.news.key}")
    private String apiKey;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testGetCategories_shouldReturnJsonArrayWithCategories() throws Exception {
        String[] availableCategories = new String[]{"business","entertainment","general","health","science","sports","technology"};

        mockMvc.perform(get("/news/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$", Matchers.containsInAnyOrder(availableCategories)));
    }

    @Test
    public void testGetNews_passedCountryCategoryAndPage_shouldReturnMockedNews() throws Exception {
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
                "https://newsapi.org/v2/top-headlines?apiKey="+apiKey+"&category=technology&country=pl&page=1&pageSize=10"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mockJsonResponse, MediaType.APPLICATION_JSON_UTF8));


        mockMvc.perform(get("/news/pl/technology/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.country", Is.is("pl")))
                .andExpect(jsonPath("$.category", Is.is("technology")))
                .andExpect(jsonPath("$.page", Is.is(1)))
                .andExpect(jsonPath("$.totalPages", Is.is(1)))
                .andExpect(jsonPath("$.articles[0].title", Is.is("title")))
                .andExpect(jsonPath("$.articles[0].description", Is.is("description")))
                .andExpect(jsonPath("$.articles[0].sourceName", Is.is("test")))
                .andExpect(jsonPath("$.articles[0].date", Is.is("2018-07-27")))
                .andExpect(jsonPath("$.articles[0].articleUrl", Is.is("http://getUrl.pl/test")))
                .andExpect(jsonPath("$.articles[0].imageUrl", Is.is("http://getUrl.pl/img.jpg")))
        ;
    }
}
