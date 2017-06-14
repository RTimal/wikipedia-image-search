package com.rajievtimal.wikipediaimagesearch.search;


import android.os.CountDownTimer;
import android.support.annotation.NonNull;

import com.rajievtimal.wikipediaimagesearch.base.BaseService;
import com.rajievtimal.wikipediaimagesearch.base.Constants;
import com.rajievtimal.wikipediaimagesearch.base.ServiceCallback;
import com.rajievtimal.wikipediaimagesearch.entities.Page;
import com.rajievtimal.wikipediaimagesearch.network.QueryResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class SearchService extends BaseService {

    private static final Map<String, String> defaultParams = new HashMap<>();
    private static SearchService mInstance = null;
    private SearchAPI mSearchAPI;
    private CountDownTimer mCountDownToSearchTimer;

    private SearchService() {
        mSearchAPI = mRetrofit.create(SearchAPI.class);
        //Set default params;
        //TODO: Generate params in another class
        defaultParams.put(Constants.ACTION, Constants.ACTION_VALUE);
        defaultParams.put(Constants.FORMAT, Constants.FORMAT_VALUE);
        defaultParams.put(Constants.GENERATOR, Constants.GENERATOR_VALUE_PREFIX);
        defaultParams.put(Constants.GPSLIMIT, Constants.GPS_LIMIT_VALUE);
        defaultParams.put(Constants.PILIMIT, Constants.PILIMIT_VALUE);
        defaultParams.put(Constants.PROP, Constants.PROP_VALUE);
        defaultParams.put(Constants.PITHUMBSIZE, Constants.PITHUMBSIZE_VALUE);
        defaultParams.put(Constants.PIPROP, Constants.PIPROP_VALUE);
    }

    static SearchService getInstance() {
        if (mInstance == null) {
            mInstance = new SearchService();
        }
        return mInstance;
    }

    void searchForImagesWithTerm(String term, Boolean delay, final ServiceCallback<List<Page>> cb) {
        final Map<String, String> params = defaultParams;
        params.put(Constants.GPSSEARCH, term);

        //Cancels current timer if it exists, so it will never fire for the previous request.
        if (mCountDownToSearchTimer != null) {
            mCountDownToSearchTimer.cancel();
        }

        if (delay) {
            int delayMS = 150;
            mCountDownToSearchTimer = new CountDownTimer(delayMS, delayMS) {
                public void onTick(long millisUntilFinished) {

                }
                public void onFinish() {
                    searchImagesWithParams(params, cb);
                }
            }.start();
        } else {
            searchImagesWithParams(params, cb);
        }

    }

    //TODO: This is not used right now, consider remoging
    void searchForRandomImages(final ServiceCallback<List<Page>> cb) {
        Map<String, String> params = defaultParams;
        params.put(Constants.GENERATOR, Constants.GENERATOR_VALUE_RANDOM);
        params.put(Constants.GRNLIMIT, Constants.GRN_LIMIT_VALUE);
        params.remove(Constants.GPSLIMIT);
        searchImagesWithParams(params, cb);
    }

    private void searchImagesWithParams(Map<String, String> params, final ServiceCallback<List<Page>> cb) {
        //TODO: Not necessary with timer, but will still keep for now
        cancelPendingRequests();

        Call<QueryResponse<List<Page>>> apiCall = mSearchAPI.searchForImages(defaultParams);
        apiCall.enqueue(new Callback<QueryResponse<List<Page>>>() {
            @Override
            public void onResponse(@NonNull Call<QueryResponse<List<Page>>> call, @NonNull Response<QueryResponse<List<Page>>> response) {
                if (response.body().getPagesWithImages() != null) {
                    cb.finishedLoading(response.body().getPagesWithImages(), null);
                } else {
                    //TODO: Return error returned from API here, not just a string !
                    cb.finishedLoading(null, "Error loading pages");
                }
            }

            @Override
            public void onFailure(@NonNull Call<QueryResponse<List<Page>>> call, @NonNull Throwable t) {
                cb.finishedLoading(null, "Error loading pages");
            }
        });

    }
}
