package com.mike.consdata.integration.api.io;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsApiSource {
    private String id;
    private String name;
}
