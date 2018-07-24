package com.mike.consdata.integration.api.io;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsApiResponse {
    private String status;
    private long totalResults;
    @Singular("article")
    private List<NewsApiArticle> articles;
    private String code;
    private String message;
}
