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

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PageImageViewHolder extends RecyclerView.ViewHolder {

    private final ItemPageImageBinding binding;
    ImageView pageImageView;
    ProgressBar mProgressBar;
    Page mPage;
    View view;
    private Integer position;
    private WeakReference<ImageResponder> mResponder;

    public PageImageViewHolder(final View view, final ItemPageImageBinding binding, WeakReference<ImageResponder> responder) {
        super(view);
        this.binding = binding;
        pageImageView = (ImageView) view.findViewById(R.id.page_image_view);
        this.mResponder = responder;
        this.mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
        // pageImageView.setOnClickListener(view1 -> mResponder.get().didClickOnView(view1, position));
    }

    @UiThread
    public void bind(final Page page, Integer position) {
        mPage = page;
        GlideApp.with(this.itemView.getContext())
                .load(page.getImageURL())
//                .placeholder(R.drawable.page_image_placeholder)
                .transition(withCrossFade(500))
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
                .into(pageImageView);

        //Can also use data binding to set other properties if necessary for future implementations
        // this.binding.setPage(page);
        this.position = position;
    }
}
