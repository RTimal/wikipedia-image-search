package com.rajievtimal.wikipediaimagesearch.search;

import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajievtimal.wikipediaimagesearch.R;
import com.rajievtimal.wikipediaimagesearch.base.BaseActivity;
import com.rajievtimal.wikipediaimagesearch.base.ServiceCallback;
import com.rajievtimal.wikipediaimagesearch.entities.Page;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends BaseActivity implements ImageResponder {

    private TextView mTextMessage;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private PageImagesAdapter mPageImagesAdapter;
    private String mSearchTerm = "";
    private char[] mAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();    //TODO: Use this

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(final ImageView view, final String url) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        mRecyclerView = (RecyclerView) findViewById(R.id.page_images_recycler_view);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mPageImagesAdapter = new PageImagesAdapter(new WeakReference<ImageResponder>(this));
        mRecyclerView.setAdapter(mPageImagesAdapter);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Show random images based on letter if there is no search term
        if (mSearchTerm.length() == 0) {
            searchForRandomImages();
        }
    }

    void searchForRandomImages() {
        int randomIndex = new Random().nextInt(mAlphabet.length);
        final char letter = mAlphabet[randomIndex];
        SearchService.getInstance().searchForImagesWithTerm(String.valueOf(letter), new ServiceCallback<List<Page>>() {
            @Override
            public void finishedLoading(List<Page> pages, String error) {
                setImages(pages, String.valueOf(letter), true);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        return true;
    }

    void setImages(List<Page> pages, String searchTerm, Boolean random) {
        if (pages != null || pages.size() > 0) {
            mPageImagesAdapter.clearItems();
            mPageImagesAdapter.addItems(pages);
            if (random) {
                String message = getString(R.string.discover_images_text) + " " + searchTerm.toUpperCase();
                mTextMessage.setText(message);
            } else {
                String message = getString(R.string.search_results_for_string) + searchTerm.toUpperCase();
                mTextMessage.setText(message);
            }
        } else {
            String message = "There are no results for" + '"' + mSearchTerm + '"' + ". Please try again.";
            mTextMessage.setText(message);
        }
    }

    @Override
    public void didClickOnPageImage(View view, int position) {
        //Show wikipedia
    }
}
