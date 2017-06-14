package com.rajievtimal.wikipediaimagesearch.imagedetails;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajievtimal.wikipediaimagesearch.R;
import com.rajievtimal.wikipediaimagesearch.entities.Page;
import com.rajievtimal.wikipediaimagesearch.search.GlideApp;

public class ImageDetailsFragment extends android.support.v4.app.DialogFragment {

    private static final String PAGE_PARAM = "com.rajievtima.wikipediaimagesearch.entities.page:Param";
    Page mCurrentPage;
    private TextView mAnswer;
    private Button mGotoWebsite;

    public static ImageDetailsFragment create(Page page) {
        ImageDetailsFragment fragment = new ImageDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(PAGE_PARAM, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentPage = (Page) savedInstanceState.getSerializable(PAGE_PARAM);
        } else if (getArguments() != null) {
            mCurrentPage = (Page) getArguments().getSerializable(PAGE_PARAM);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(PAGE_PARAM, mCurrentPage);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View customDialogView = inflater.inflate(R.layout.dialog_image_details, null);


        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        builder.setView(customDialogView);

        ImageView imageView = (ImageView) customDialogView.findViewById(R.id.image_view);
        Button revealbutton = (Button) customDialogView.findViewById(R.id.reveal_button);
        mAnswer = (TextView) customDialogView.findViewById(R.id.answer);
        mGotoWebsite = (Button) customDialogView.findViewById(R.id.go_to_website_button);

        if (mCurrentPage != null) {
            GlideApp.with(this.getContext())
                    .load(mCurrentPage.getImageURL())
                    .fallback(R.drawable.page_image_placeholder)
                    .centerCrop()
                    .into(imageView);

            mAnswer.setText(mCurrentPage.getTitle());
        }

        revealbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswer.setVisibility(View.VISIBLE);
                mGotoWebsite.setVisibility(View.VISIBLE);
            }
        });

        mGotoWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPage != null) {
                    dismiss();
                    showURL(mCurrentPage.getPageUrl());
                }
            }
        });

        return builder.create();
    }

    public void showURL(String url) {
        if (url == null) {
            return;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

}
