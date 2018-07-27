package com.mike.consdata.integration.api;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.stream.Collectors;

public class QueryParamsMap extends HashMap<String, Object> {
    private static final String QUERY_PARAM_KEY_VALUE_SEPARATOR = "=";
    private static final String QUERY_PARAMS_SEPARATOR = "&";

    @Override
    public String toString() {
        if(entrySet().isEmpty()) {
            return StringUtils.EMPTY;
        }

        return entrySet().stream()
                .map(entry->entry.getKey() + QUERY_PARAM_KEY_VALUE_SEPARATOR + entry.getValue().toString())
                .collect(Collectors.joining(QUERY_PARAMS_SEPARATOR));
    }
}