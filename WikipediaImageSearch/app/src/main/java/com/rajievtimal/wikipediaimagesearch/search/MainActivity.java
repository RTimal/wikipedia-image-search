package com.rajievtimal.wikipediaimagesearch.search;

import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajievtimal.wikipediaimagesearch.R;
import com.rajievtimal.wikipediaimagesearch.base.BaseActivity;
import com.rajievtimal.wikipediaimagesearch.base.ServiceCallback;
import com.rajievtimal.wikipediaimagesearch.entities.Page;
import com.rajievtimal.wikipediaimagesearch.imagedetails.ImageDetailsFragment;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;

import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;

public class MainActivity extends BaseActivity implements PageImageTapResponder {

    private TextView mTextMessage;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private PageImagesAdapter mPageImagesAdapter;
    private String mSearchTerm = "";
    private char[] mAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();    //Using this to generate random images [a-z] to populate discovery

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(final ImageView view, final String url) {
        //Not doing anything with this right now. For future use
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        mRecyclerView = (RecyclerView) findViewById(R.id.page_images_recycler_view);
        mRecyclerView.setItemAnimator(new FadeInDownAnimator());
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mPageImagesAdapter = new PageImagesAdapter(new WeakReference<PageImageTapResponder>(this));
        mRecyclerView.setAdapter(mPageImagesAdapter);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            setupSearchView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Search for some random images if there is no search term
        if (mSearchTerm.length() == 0) {
            //Refresh random images
            searchForRandomImages();
        }
    }

    void searchForImagesWithTerm(final String searchTerm) {
        if (searchTerm.length() > 0) {
            SearchService.getInstance().searchForImagesWithTerm(searchTerm, true, new ServiceCallback<List<Page>>() {
                @Override
                public void finishedLoading(List<Page> pages, String error) {
                    //TODO: handle errors
                    showPageImages(pages, searchTerm, false);
                }
            });
        } else {
            searchForRandomImages();
        }

    }

    void searchForRandomImages() {
        //Not truly random, uses alphabet to get letter to search for
        int randomIndex = new Random().nextInt(mAlphabet.length);
        final char letter = mAlphabet[randomIndex];
        SearchService.getInstance().searchForImagesWithTerm(String.valueOf(letter), false, new ServiceCallback<List<Page>>() {
            @Override
            public void finishedLoading(List<Page> pages, String error) {
                //TODO: Handle errors
                showPageImages(pages, String.valueOf(letter), true);
            }
        });
    }

    //TODO: this could bea bit more readable, some code duplicated
    void showPageImages(List<Page> pages, String searchTerm, Boolean random) {
        if (!random && !searchTerm.equals(mSearchTerm)) {
            //This is for network race conditions, current term needs to match term that the http response is for
            return;
        }
        if (pages != null && pages.size() > 0) {
            mPageImagesAdapter.clearItems();
            mRecyclerView.getRecycledViewPool().clear();
            mPageImagesAdapter.addItems(pages);

            if (random) {
                String message = getString(R.string.discover_images_text) + " " + searchTerm.toUpperCase();
                mTextMessage.setText(message);
            } else {
                String message = getString(R.string.search_results_for_string) + " " + "\"" + searchTerm + "\"";
                mTextMessage.setText(message);
            }

        } else if (searchTerm.length() > 0) {
            String message = getString(R.string.no_results) + " " + "\"" + searchTerm + "\"";
            mTextMessage.setText(message);
            mPageImagesAdapter.clearItems();
            mRecyclerView.getRecycledViewPool().clear();
        }
    }

    private void setupSearchView() {
        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setFocusable(false);
        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();
        searchView.setQueryHint(getString(R.string.search_for_images_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchTerm = query;
                searchForImagesWithTerm(mSearchTerm);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchTerm = newText;
                searchForImagesWithTerm(newText);
                return true;
            }
        });
    }

    @Override
    public void didClickOnPageImage(Page page) {
        DialogFragment imageDetailsFragment = ImageDetailsFragment.create(page);
        imageDetailsFragment.show(getSupportFragmentManager(), "page image details");
    }
}
