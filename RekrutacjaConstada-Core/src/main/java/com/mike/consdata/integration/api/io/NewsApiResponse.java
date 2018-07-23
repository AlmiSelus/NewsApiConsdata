package com.mike.consdata.integration.api.io;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewsApiResponse {
    private String status;
    private long totalResults;
    private List<NewsApiArticle> articles;
    private String code;
    private String message;
}
