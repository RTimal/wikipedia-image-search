package com.rajievtimal.wikipediaimagesearch.entities;

import com.google.gson.annotations.SerializedName;


class Thumbnail {
    @SerializedName("source")
    String mSource;

    @SerializedName("width")
    Integer mWidth;

    @SerializedName("height")
    Integer mHeight;
}
