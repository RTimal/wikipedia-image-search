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

//This is our API Response Wrapper. T represents is the model object type

public class APIResponse<T> {

    @SerializedName("query")
    private JsonElement mQuery;
    private T mPages;

    //This turns the query into objects when it's accessed. Doing some memoization here.

    public T getPagesWithImages() {
        //TODO: Refactor this to handle multiple types of objects, not just List<Page>
        //TODO: Not working with generic Type yet, maybe use custom Deserializer instead
        //TODO: This could be cleaned up and optimized a bit
        //TODO: Use setters & Jackson Converter to make deserialization happen on network thread instead of when response accessed from main thread
        if (mQuery != null && mPages == null) {
            JsonObject query = mQuery.getAsJsonObject();
            //Flattening Page ID Keys
            JsonArray array = new JsonArray();
            if (query.get("pages") != null) {
                for (Map.Entry<String, JsonElement> pageMap : query.get("pages").getAsJsonObject().entrySet())
                    if (pageMap.getValue() != null) {
                        //Only return pages with images
                        if (pageMap.getValue().getAsJsonObject().has("thumbnail")) {
                            array.add(pageMap.getValue());
                        }
                    }
            }
            Type listType = new TypeToken<List<Page>>() {
            }.getType();
            mPages = new Gson().fromJson(array, listType);
        }
        return mPages;
    }

}
