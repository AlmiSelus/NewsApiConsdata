package com.mike.consdata.integration.api.endpoints;

public class BaseNewsEndpoint implements NewsEndpoint {
    public static final String NEWS_API_BASE_URL = "https://newsapi.org";

    @Override
    public String getUrl() {
        return NEWS_API_BASE_URL;
    }
}
