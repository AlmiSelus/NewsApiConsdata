package com.mike.consdata.integration.api.endpoints;

public enum EndpointEnum {
    TOP_HEADLINES(new TopNewsEndpoint()),
    SOURCES(new SourcesEndpoint());

    private NewsEndpoint newsEndpoint;

    EndpointEnum(NewsEndpoint newsEndpoint) {
        this.newsEndpoint = newsEndpoint;
    }

    public NewsEndpoint getNewsEndpoint() {
        return newsEndpoint;
    }
}
