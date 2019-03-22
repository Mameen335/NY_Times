package com.mameen.mytask.ui.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mameen.mytask.R;
import com.mameen.mytask.app.AppAsistant;
import com.mameen.mytask.app.Constants;
import com.mameen.mytask.data.apis.responses.NewsResponse;
import com.mameen.mytask.data.apis.services.NewsService;
import com.mameen.mytask.data.apis.services.impl.NewsServiceImple;
import com.mameen.mytask.data.models.News;
import com.mameen.mytask.interfaces.OnItemClickListener;
import com.mameen.mytask.ui.adapter.NewsAdapter;
import com.mameen.mytask.util.ParentActivity;

import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindView;
import io.github.sporklibrary.annotations.BindComponent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends ParentActivity {

    static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.srlNews)
    SwipeRefreshLayout srlNews;

    @BindView(R.id.rvNews)
    RecyclerView rvNews;

    private NewsAdapter adapter;
    private List<News> newsList = new ArrayList<>();

    @BindComponent(NewsServiceImple.class)
    NewsService newsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getResources().getString(R.string.app_name) + " "
                +getResources().getString(R.string.mostpopular));


        Spork.bind(this);
        srlNews.setOnRefreshListener(srlNewsListener);

        initList();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadNews();
    }

    SwipeRefreshLayout.OnRefreshListener srlNewsListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            newsList.clear();
            loadNews();
        }
    };

    private void initList() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        adapter = new NewsAdapter(MainActivity.this, newsList, R.layout.row_news);
        rvNews.setLayoutManager(linearLayoutManager);
        rvNews.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                // something
            }
        });
    }

    private void loadNews() {

        if (AppAsistant.isConnected(MainActivity.this)) {
            srlNews.setRefreshing(true);

            int period = 1;

            newsService.getNews(period, Constants.API_KEY, new Callback<NewsResponse>() {
                @Override
                public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                    if (response.isSuccessful()) {
                        String status = response.body().getStatus();

                        switch (status) {
                            case "OK":
                                adapter.InsertAll(response.body().getResults());
                                break;
                            case "ERROR":
                                String error = response.body().getErrors().get(0);
                                showShortSnackbar(R.id.parent, error);
                                break;
                        }

                        srlNews.setRefreshing(false);
                    } else {
                        // no internet
                        showShortSnackbar(R.id.parent, getResources().getString(R.string.check_internet));
                    }
                }

                @Override
                public void onFailure(Call<NewsResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure Cause: " + t.getCause());
                    srlNews.setRefreshing(false);
                }
            });

        } else {
            // no internet
            showShortSnackbar(R.id.parent, getResources().getString(R.string.check_internet));
            srlNews.setRefreshing(false);
        }
    }
}
