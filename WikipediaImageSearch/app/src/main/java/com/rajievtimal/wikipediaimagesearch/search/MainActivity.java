package com.rajievtimal.wikipediaimagesearch.search;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.rajievtimal.wikipediaimagesearch.R;
import com.rajievtimal.wikipediaimagesearch.base.BaseActivity;
import com.rajievtimal.wikipediaimagesearch.base.ServiceCallback;
import com.rajievtimal.wikipediaimagesearch.entities.Page;

import java.util.List;

public class MainActivity extends BaseActivity {

    private TextView mTextMessage;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SearchService.getInstance().searchForImagesWithTerm("cat", new ServiceCallback<List<Page>>() {
            @Override
            public void finishedLoading(List<Page> pages, String error) {
              //  Log.d("pages", pages.toString());
            }
        });
    }
}
