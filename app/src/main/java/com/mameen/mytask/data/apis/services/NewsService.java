package com.mameen.mytask.data.apis.services;

import com.mameen.mytask.data.apis.responses.NewsResponse;

import retrofit2.Callback;

public interface NewsService {

    void getNews(int period, String api_key
            , Callback<NewsResponse> callback);
}
