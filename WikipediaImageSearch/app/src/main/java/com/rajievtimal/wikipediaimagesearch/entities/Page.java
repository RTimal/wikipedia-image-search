package com.rajievtimal.wikipediaimagesearch.entities;

import com.google.gson.annotations.SerializedName;

public class Page {
    @SerializedName("thumbnail")
    Thumbnail mThumbnail;

    @SerializedName("pageid")
    String mPageId;

    @SerializedName("title")
    String mCat;
}
