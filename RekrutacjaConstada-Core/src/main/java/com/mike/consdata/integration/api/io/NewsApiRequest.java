package com.mike.consdata.integration.api.io;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsApiRequest {
    private String country;
    private String category;
    private Integer page;
    private String apiKey;
    private long resultsPerPage;
    private String query;
}
