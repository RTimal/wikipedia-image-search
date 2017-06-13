package com.rajievtimal.wikipediaimagesearch.network;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.rajievtimal.wikipediaimagesearch.entities.Page;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

//This is our API Response Wrapper. T is the model object

public class APIResponse<T> {

    @SerializedName("query")

    private JsonElement mQuery;
    private T mObject;

    public T getObject() {
        //This turns the query into objects when it's accessed.
        //TODO: This could be cleaned up and optimized a bit
        //TODO: Use setters & Jackson Converter to make deserialization happen on network thread instead of when response accessed
        if (mQuery != null && mObject == null) {
            JsonObject query = mQuery.getAsJsonObject();
            //Flattening Page ID Keys
            JsonArray array = new JsonArray();
            if (query.get("pages") != null) {
                for (Map.Entry<String, JsonElement> pageMap : query.get("pages").getAsJsonObject().entrySet())
                    if (pageMap.getValue() != null) {
                        array.add(pageMap.getValue());
                    }
            }

            //TODO: Not working with generic Type yet
            Type listType = new TypeToken<List<Page>>() { // object can be String here
            }.getType();
            mObject = new Gson().fromJson(array, listType);
        }
        return mObject;
    }

}
