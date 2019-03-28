package com.mameen.mytask;

import android.util.Log;

import com.mameen.mytask.app.Constants;
import com.mameen.mytask.data.apis.responses.NewsResponse;
import com.mameen.mytask.data.apis.services.impl.NewsServiceImple;

import org.junit.Test;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NYNewsTest {

    static final String TAG = NYNewsTest.class.getSimpleName();

    @Test
    public void testNews() throws Exception{


        NewsServiceImple newsService = new NewsServiceImple();
        newsService.getNews(7, Constants.API_KEY, new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {

                    String status = response.body().getStatus();

                    switch (status) {
                        case "OK":
                            assertEquals("No Results", true,response.body().getResults().isEmpty());
                            break;
                        case "ERROR":
                            Log.d(TAG, "Error Message: " + response.body().getErrors().get(0));
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e(TAG, t.getCause().toString());
            }
        });

    }


}
