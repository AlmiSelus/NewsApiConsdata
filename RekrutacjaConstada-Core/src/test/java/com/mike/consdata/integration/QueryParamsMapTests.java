package com.mike.consdata.integration;


import com.mike.consdata.integration.api.QueryParamsMap;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.core.Is;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class QueryParamsMapTests {
    @Test
    public void testToString_shouldReturnConcatenatedString() {
        QueryParamsMap queryParamsMap = new QueryParamsMap();
        queryParamsMap.put("key", 1);
        queryParamsMap.put("key2", "somevalue");

        String queryParams = queryParamsMap.toString();

        assertThat(queryParams, is(notNullValue()));
        assertThat(queryParams, is("key=1&key2=somevalue"));
    }

    @Test
    public void testToString_emptyMap_shouldReturnEmptyString() {
        QueryParamsMap queryParamsMap = new QueryParamsMap();

        String queryParams = queryParamsMap.toString();

        assertThat(queryParams, is(notNullValue()));
        assertThat(queryParams, is(StringUtils.EMPTY));
    }
}
