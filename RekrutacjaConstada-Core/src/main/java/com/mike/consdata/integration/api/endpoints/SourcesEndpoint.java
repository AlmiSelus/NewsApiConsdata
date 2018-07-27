package com.mike.consdata.integration.api.endpoints;

import org.apache.commons.lang3.NotImplementedException;

public class SourcesEndpoint extends BaseNewsEndpoint {
    @Override
    public String getUrl() {
        throw new NotImplementedException("Not required! Exists only for api design demonstration!");
    }
}
