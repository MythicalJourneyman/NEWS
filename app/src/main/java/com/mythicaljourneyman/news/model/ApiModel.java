package com.mythicaljourneyman.news.model;

import com.mythicaljourneyman.news.objects.NewsResults;
import com.mythicaljourneyman.news.rest.ApiModule;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Response;

/**
 * Created by Prabodh Dhabaria on 22-05-2018.
 */
public class ApiModel {
    private static final String AUTH = "f708a9332bba4d3f83bde120761ecaf0";

    public static Observable<NewsResults> getTopHeadlinesByCountry(final String country, final int page, final int limit) {
        return Observable.create(new ObservableOnSubscribe<NewsResults>() {
            @Override
            public void subscribe(ObservableEmitter<NewsResults> emitter) throws Exception {
                Response<NewsResults> response = ApiModule.getApiInterface().getTopHeadlinesCountry(AUTH, country, page, limit).execute();
                if (response.isSuccessful()) emitter.onNext(response.body());
            }
        });
    }
}
