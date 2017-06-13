package com.rajievtimal.wikipediaimagesearch.network;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

//This is our API Response Wrapper. T is the model object

public class APIResponse<T> {

    @SerializedName("query")
    private JsonElement mQuery;

    private T mObject;
    private Class<T> type;

    public T getObject() {
        return mObject;
    }

    //Can handle other types of responses here, not just queries
    public void setQuery(JsonElement query) {
        JsonObject queryObject = query.getAsJsonObject();
        JsonArray pageArray = new JsonArray();
        //Flattening Page Keys
        if (queryObject.has("pages")) {
            for (Map.Entry<String, JsonElement> pageMap : queryObject.getAsJsonObject("pages").entrySet()) {
                JsonElement pageElement = pageMap.getValue();
                pageArray.add(pageElement);
            }

            mObject = new Gson().fromJson(pageArray, type);
        }
    }

}
