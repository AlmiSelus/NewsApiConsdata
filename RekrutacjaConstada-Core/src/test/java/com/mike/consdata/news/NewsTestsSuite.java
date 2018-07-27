package com.mike.consdata.news;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        NewsServiceTests.class,
        NewsRestControllerTests.class,
        NewsApiArticleToArticleConverterTests.class,
        NewsApiResponseToNewsResponseConverterTests.class
})
public class NewsTestsSuite {
}
