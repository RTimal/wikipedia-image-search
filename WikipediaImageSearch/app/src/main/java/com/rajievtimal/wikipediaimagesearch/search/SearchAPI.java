package com.rajievtimal.wikipediaimagesearch.search;

import com.rajievtimal.wikipediaimagesearch.entities.Page;
import com.rajievtimal.wikipediaimagesearch.network.QueryResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

interface SearchAPI {
    @GET("api.php")
    Call<QueryResponse<List<Page>>> searchForImages(
            @QueryMap Map<String, String> params
    );
}
