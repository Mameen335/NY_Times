package com.mameen.mytask.data.apis.services.impl;

import android.util.Log;

import com.mameen.mytask.data.apis.NewsApi;
import com.mameen.mytask.data.apis.responses.NewsResponse;
import com.mameen.mytask.data.apis.services.BaseService;
import com.mameen.mytask.data.apis.services.NewsService;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import retrofit2.Callback;

public class NewsServiceImple implements NewsService {


    static final String TAG = NewsServiceImple.class.getSimpleName();

    @BindComponent(BaseServiceImpl.class)
    BaseService baseService;

    NewsApi newsApi;

    public NewsServiceImple() {
        Spork.bind(this);
        newsApi = baseService.getRetrofit().create(NewsApi.class);
    }


    @Override
    public void getNews(int period, String api_key, Callback<NewsResponse> callback) {
        try {
            newsApi.getNews(period, api_key).enqueue(callback);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
}
