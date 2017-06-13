package com.rajievtimal.wikipediaimagesearch.search;

import android.os.Bundle;
import android.widget.TextView;

import com.rajievtimal.wikipediaimagesearch.R;
import com.rajievtimal.wikipediaimagesearch.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);

    }

}
