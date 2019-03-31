package com.mameen.mytask.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mameen.mytask.R;
import com.mameen.mytask.app.AppAsistant;
import com.mameen.mytask.app.Constants;
import com.mameen.mytask.data.apis.responses.NewsResponse;
import com.mameen.mytask.data.apis.services.NewsService;
import com.mameen.mytask.data.apis.services.impl.NewsServiceImple;
import com.mameen.mytask.data.models.News;
import com.mameen.mytask.interfaces.OnItemClickListener;
import com.mameen.mytask.ui.adapter.NewsAdapter;
import com.mameen.mytask.util.ParentFragment;

import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindView;
import io.github.sporklibrary.annotations.BindComponent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends ParentFragment {

    static final String TAG = NewsFragment.class.getSimpleName();

    @BindView(R.id.srlNews)
    SwipeRefreshLayout srlNews;

    @BindView(R.id.rvNews)
    RecyclerView rvNews;

    private NewsAdapter adapter;
    private List<News> newsList = new ArrayList<>();

    @BindComponent(NewsServiceImple.class)
    NewsService newsService;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spork.bind(this);
        srlNews.setOnRefreshListener(srlNewsListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initList();
    }

    @Override
    public void onStart() {
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        adapter = new NewsAdapter(getActivity(), newsList, R.layout.row_news);
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

        if (AppAsistant.isConnected(getActivity())) {
            srlNews.setRefreshing(true);

            int period = 7;

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
