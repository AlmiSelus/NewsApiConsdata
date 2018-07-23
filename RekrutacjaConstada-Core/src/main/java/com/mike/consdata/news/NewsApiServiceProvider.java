package com.mike.consdata.news;

import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;

@Component
public class NewsApiServiceProvider {

    private NewsApiService newsApiService;

    public NewsApiServiceProvider() {
        newsApiService = new Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(NewsApiService.class);
    }

    Call<List<NewsApiResponse>> listApiResponses(String apiKey, String country, String category) {
        return newsApiService.listApiResponses(apiKey, country, category);
    }

}
