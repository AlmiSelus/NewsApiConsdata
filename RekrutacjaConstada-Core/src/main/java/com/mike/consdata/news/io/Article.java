package com.mike.consdata.news.io;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Article {
    private String author;
    private String title;
    private String description;
    private LocalDate date;
    private String sourceName;
    private String articleUrl;
    private String imageUrl;
}
