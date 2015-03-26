package com.tiyanrcode.guestbookclient;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tiyanrcode.guestbookclient.controller.LoginController;


public class MainActivity extends ActionBarActivity {

    private final int SPLAH_TIMEOUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        startAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginController.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.move);
            }
        },SPLAH_TIMEOUT);
    }

    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    private void startAnimation(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animation.reset();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.clearAnimation();
        linearLayout.startAnimation(animation);

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.translate);
        animation1.reset();
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.clearAnimation();
        imageView.startAnimation(animation1);
    }
}
