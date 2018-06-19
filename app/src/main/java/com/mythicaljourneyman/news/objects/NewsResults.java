package com.mythicaljourneyman.news.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prabodh Dhabaria on 22-05-2018.
 */
public class NewsResults {
    @SerializedName("status")
    @Expose
    private String mStatus;
    @SerializedName("totalResults")
    @Expose
    private int mTotalResults;
    @SerializedName("articles")
    @Expose
    private ArrayList<NewsItem> mArticles;

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public int getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(int totalResults) {
        mTotalResults = totalResults;
    }

    public ArrayList<NewsItem> getArticles() {
        return mArticles;
    }

    public void setArticles(ArrayList<NewsItem> articles) {
        mArticles = articles;
    }
}
