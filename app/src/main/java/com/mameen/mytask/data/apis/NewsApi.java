package com.mameen.mytask.data.apis;

import com.mameen.mytask.data.apis.responses.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("viewed/{period}.json")
    Call<NewsResponse> getNews(
            @Path("period") int period,
            @Query("api-key") String api_key);
}
