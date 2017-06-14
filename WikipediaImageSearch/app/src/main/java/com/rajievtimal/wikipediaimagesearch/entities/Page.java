package com.rajievtimal.wikipediaimagesearch.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Page implements Serializable {
    @SerializedName("thumbnail")
    Thumbnail mThumbnail;

    @SerializedName("pageid")
    String mPageId;

    @SerializedName("title")
    String mTitle;

    public String getImageURL() {
        return (mThumbnail != null ? mThumbnail.getSourceURL() : null);
    }

    public String getPageUrl() {
        String url = "http://en.wikipedia.org/?curid=";
        if (mPageId != null) {
            url += mPageId;
        }
        return url;
    }

    public String getTitle() {
        String title = "Title";
        title = mTitle;
        return title;
    }
}
