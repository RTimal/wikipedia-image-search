package com.rajievtimal.wikipediaimagesearch.search;

import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajievtimal.wikipediaimagesearch.R;
import com.rajievtimal.wikipediaimagesearch.base.BaseActivity;
import com.rajievtimal.wikipediaimagesearch.base.ServiceCallback;
import com.rajievtimal.wikipediaimagesearch.entities.Page;

import java.lang.ref.WeakReference;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends BaseActivity implements ImageResponder {

    private TextView mTextMessage;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private PageImagesAdapter mPageImagesAdapter;

    //TODO: Use this
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
    }

    @Override
    protected void onResume() {
        super.onResume();

//        SearchService.getInstance().searchForImagesWithTerm("hello", new ServiceCallback<List<Page>>() {
//            @Override
//            public void finishedLoading(List<Page> pages, String error) {
//                mPageImagesAdapter.clearItems();
//                mPageImagesAdapter.addItems(pages);
//        }
//        });

        SearchService.getInstance().searchForRandomImages(new ServiceCallback<List<Page>>() {
            @Override
            public void finishedLoading(List<Page> pages, String error) {
                mPageImagesAdapter.clearItems();
                mPageImagesAdapter.addItems(pages);
            }
        });
    }

    @Override
    public void didClickOnPageImage(View view, int position) {

    }
}
