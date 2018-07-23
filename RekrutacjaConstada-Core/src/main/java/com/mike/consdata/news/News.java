package com.mike.consdata.news;

import lombok.Data;

import java.util.List;

@Data
public class News {
    private String country;
    private String category;
    private List<Article> article;
}
