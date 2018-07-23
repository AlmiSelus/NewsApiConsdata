package com.mike.consdata.integration.api.io;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsApiArticle {
    private NewsApiSource source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
}
