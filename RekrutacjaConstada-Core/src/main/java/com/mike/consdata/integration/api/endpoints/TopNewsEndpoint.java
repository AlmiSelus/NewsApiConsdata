package com.mike.consdata.integration.api.endpoints;

public class TopNewsEndpoint extends BaseNewsEndpoint{

    private static final String NEWS_API_TOP_HEADLINES_ENDPOINT = "/v2/top-headlines";

    @Override
    public String getUrl() {
        return super.getUrl() + NEWS_API_TOP_HEADLINES_ENDPOINT;
    }
}
