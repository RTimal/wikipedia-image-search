package com.rajievtimal.wikipediaimagesearch.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Page implements Serializable {
    @SerializedName("thumbnail")
    Thumbnail mThumbnail;

    @SerializedName("pageid")
    String mPageId;

    @SerializedName("title")
    String mCat;

    public String getImageURL() {
        return (mThumbnail != null ? mThumbnail.getSourceURL() : null);
    }
}
