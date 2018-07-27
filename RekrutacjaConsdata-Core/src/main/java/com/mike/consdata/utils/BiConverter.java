package com.mike.consdata.utils;

public interface BiConverter<S, T, R> {
    R convert(S source1, T source2);
}
