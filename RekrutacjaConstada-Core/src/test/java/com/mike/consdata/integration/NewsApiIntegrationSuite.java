package com.mike.consdata.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    QueryParamsMapTests.class,
    NewsApiServiceProviderTests.class
})
public class NewsApiIntegrationSuite {
}
