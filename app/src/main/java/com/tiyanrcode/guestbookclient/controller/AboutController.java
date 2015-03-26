package com.tiyanrcode.guestbookclient.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.tiyanrcode.guestbookclient.R;

/**
 * Created by sulistiyanto on 3/23/2015.
 */
public class AboutController extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        getSupportActionBar().hide();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
