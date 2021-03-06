package com.rajievtimal.wikipediaimagesearch.search;

import android.databinding.DataBindingUtil;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rajievtimal.wikipediaimagesearch.R;
import com.rajievtimal.wikipediaimagesearch.databinding.ItemPageImageBinding;
import com.rajievtimal.wikipediaimagesearch.entities.Page;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

class PageImagesAdapter extends RecyclerView.Adapter<PageImageViewHolder> {

    private List<Page> mPages = new ArrayList<>();
    private WeakReference<PageImageTapResponder> mResponder;

    PageImagesAdapter(WeakReference<PageImageTapResponder> responder) {
        this.mResponder = responder;
    }

    @UiThread
    void addItems(final List<Page> pages) {
        this.mPages.addAll(pages);
        this.notifyItemRangeInserted(0, pages.size());
    }

    @UiThread
    void clearItems() {
        int previousSuze = 0;

        if (mPages != null) {
            previousSuze = mPages.size();
        }

        assert this.mPages != null;
        this.mPages.clear();

        if (mPages != null) {
            this.notifyItemRangeRemoved(0, previousSuze);
        }
    }

    public PageImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemPageImageBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_page_image, parent, false);
        return new PageImageViewHolder(binding.getRoot(), binding, mResponder);
    }

    @Override
    public void onBindViewHolder(PageImageViewHolder holder, int position) {
        final Page page = this.mPages.get(position);
        holder.bind(page, position);
    }

    @Override
    public int getItemCount() {
        return mPages.size();
    }
}
