package com.mythicaljourneyman.news.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Prabodh Dhabaria on 22-05-2018.
 */
public class NewsItem implements Serializable {
    @SerializedName("author")
    @Expose
    private String mAuthor;
    @SerializedName("source")
    @Expose
    private NewsSource mSource;
    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("urlToImage")
    @Expose
    private String mImage;
    @SerializedName("publishedAt")
    @Expose
    private Date mPublishedAt;

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public NewsSource getSource() {
        return mSource;
    }

    public void setSource(NewsSource source) {
        mSource = source;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public Date getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        mPublishedAt = publishedAt;
    }
}
