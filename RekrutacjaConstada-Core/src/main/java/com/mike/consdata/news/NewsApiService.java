package com.mike.consdata.news;

import com.mike.consdata.integration.api.io.NewsApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface NewsApiService {
    @GET("/v2/top-headlines")
    Call<List<NewsApiResponse>> listApiResponses(@Query("apiKey") String apiKey,
                                                 @Query("country") String country,
                                                 @Query("category") String category);
}
