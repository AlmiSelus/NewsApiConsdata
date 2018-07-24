package com.mike.consdata.news.io;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
public class NewsResponse {
    private String error;

    private String country;
    private String category;
    @Singular("article")
    private List<Article> articles;
}
