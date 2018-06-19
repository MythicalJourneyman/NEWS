package com.mythicaljourneyman.news.rest;

import com.mythicaljourneyman.news.objects.NewsResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Prabodh Dhabaria on 21-05-2018.
 */
public interface ApiInterface {
    @GET("v2/top-headlines")
    Call<NewsResults> getTopHeadlines(@Header("Authorization") String auth, @Query("page") int page, @Query("pageSize") int limit);

    @GET("v2/top-headlines")
    Call<NewsResults> getTopHeadlinesCountry(@Header("Authorization") String auth, @Query("country") String country, @Query("page") int page, @Query("pageSize") int limit);
}
