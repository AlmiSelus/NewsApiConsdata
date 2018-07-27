package com.mike.consdata.integration;

import com.mike.consdata.integration.api.NewsManager;
import com.mike.consdata.integration.api.endpoints.BaseNewsEndpoint;
import com.mike.consdata.integration.api.endpoints.NewsEndpoint;
import com.mike.consdata.integration.api.endpoints.TopNewsEndpoint;
import com.mike.consdata.news.io.NewsResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NewsManagerTests {
    @Autowired
    private RestTemplate restTemplate;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void callRegisterEndpoint_passNullEndpointObject_shouldThrowIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(is("News endpoint is null!"));
        NewsManager newsManager = new NewsManager(StringUtils.EMPTY);
        newsManager.registerEndpoint((NewsEndpoint)null);
    }

    @Test
    public void callRegisterEndpoint_passNullApiKey_shouldThrowIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(is("Api key is empty or null!"));
        new NewsManager(null);
    }

    @Test
    public void callRegisterEndpoint_passEmptyApiKey_shouldThrowIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(is("Api key is empty or null!"));
        new NewsManager(StringUtils.EMPTY);
    }
}
