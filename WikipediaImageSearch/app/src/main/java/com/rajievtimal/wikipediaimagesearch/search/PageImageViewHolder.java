package com.rajievtimal.wikipediaimagesearch.search;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rajievtimal.wikipediaimagesearch.R;
import com.rajievtimal.wikipediaimagesearch.databinding.ItemPageImageBinding;
import com.rajievtimal.wikipediaimagesearch.entities.Page;

import java.lang.ref.WeakReference;

class PageImageViewHolder extends RecyclerView.ViewHolder {

    private final ItemPageImageBinding binding;
    private View mView;
    private ImageView mPageImageView;
    private ProgressBar mProgressBar;
    private Page mPage;
    private Integer position;
    private WeakReference<PageImageTapResponder> mResponder;

    PageImageViewHolder(final View view, final ItemPageImageBinding binding, WeakReference<PageImageTapResponder> responder) {
        super(view);
        this.mView = view;
        this.binding = binding;
        mPageImageView = (ImageView) view.findViewById(R.id.page_image_view);
        this.mResponder = responder;
        this.mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
        mPageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResponder.get().didClickOnPageImage(mPage);
            }
        });
    }

    @UiThread
    void bind(final Page page, Integer position) {
        mPage = page;
        GlideApp.with(this.itemView.getContext())
                .load(page.getImageURL())
                .centerCrop()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(mPageImageView);

        //Can also use data binding to set other properties if necessary for future implementations
        // this.binding.setPage(page);
        this.position = position;
    }
}
