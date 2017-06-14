package com.rajievtimal.wikipediaimagesearch.search;

import android.animation.LayoutTransition;
import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rajievtimal.wikipediaimagesearch.R;
import com.rajievtimal.wikipediaimagesearch.base.BaseActivity;
import com.rajievtimal.wikipediaimagesearch.base.ServiceCallback;
import com.rajievtimal.wikipediaimagesearch.entities.Page;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
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
    private SearchView mSearchView;
    private EditText mSearchViewEditText;

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
        setupSearchView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSearchTerm.length() == 0) {
            searchForRandomImages();
        }
    }

    void searchForImagesWithTerm(final String searchTerm) {
        SearchService.getInstance().searchForImagesWithTerm(searchTerm, new ServiceCallback<List<Page>>() {
            @Override
            public void finishedLoading(List<Page> pages, String error) {
                setImages(pages, searchTerm, false);
            }
        });
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

    void setImages(List<Page> pages, String searchTerm, Boolean random) {
        if (pages != null && pages.size() > 0) {
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

    private void setupSearchView() {
        mSearchView = (SearchView) findViewById(R.id.search_view);
        mSearchView.setFocusable(true);
        mSearchView.setIconified(false);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.requestFocusFromTouch();
        mSearchView.setMaxWidth(100000000);
        mSearchView.setQueryHint("Search for images");
        final LinearLayout searchBar = (LinearLayout) mSearchView.findViewById(R.id.search_bar);
        searchBar.setLayoutTransition(new LayoutTransition());
        mSearchView.setActivated(true);
        mSearchViewEditText = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        changeSearchViewTextColor(searchView);

        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
//            mCursorDrawableRes.set(searchViewEditText, R.drawable.white_cursor); // 0: @null (automatic), or any drawable of yours.
//            mSearchView.setHintTextColor(getResources().getColor(R.color.linkedingray));
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        if (!TextUtils.isEmpty(author)) {
//            searchViewEditText.setText(author);
//            searchViewEditText.setSelection(author.length());
//            currentTerm = "inauthor:" + "\"" + author + "\"";
//            searchWithTerm();
//            searchView.clearFocus();
//        } else if (!TextUtils.isEmpty(isbn)) {
//            searchViewEditText.setText(isbn);
//            searchViewEditText.setSelection(isbn.length());
//            currentTerm = isbn;
//            searchWithTerm();
//            searchView.clearFocus();
//        }

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    public void didClickOnPageImage(View view, int position) {
        //Show wikipedia
    }
}
