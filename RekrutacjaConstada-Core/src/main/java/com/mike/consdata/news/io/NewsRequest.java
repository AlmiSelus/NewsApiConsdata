package com.mike.consdata.news.io;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsRequest {
    private String country;
    private String category;
    private Integer page;
}
