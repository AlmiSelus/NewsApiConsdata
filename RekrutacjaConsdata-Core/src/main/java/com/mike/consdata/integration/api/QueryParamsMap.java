package com.mike.consdata.integration.api;

import org.apache.commons.lang3.StringUtils;

import java.util.TreeMap;
import java.util.stream.Collectors;

public class QueryParamsMap extends TreeMap<String, Object> {
    private static final String QUERY_PARAM_KEY_VALUE_SEPARATOR = "=";
    private static final String QUERY_PARAMS_SEPARATOR = "&";

    @Override
    public String toString() {
        if(entrySet().isEmpty()) {
            return StringUtils.EMPTY;
        }

        return entrySet().stream()
                .filter(x->x.getKey() != null && x.getValue() != null)
                .map(entry->entry.getKey() + QUERY_PARAM_KEY_VALUE_SEPARATOR + entry.getValue().toString())
                .collect(Collectors.joining(QUERY_PARAMS_SEPARATOR));
    }
}