// Copyright 2020 ADTIMING TECHNOLOGY COMPANY LIMITED
// Licensed under the GNU Lesser General Public License Version 3

package com.light.cleaner.zeros;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.nbmediation.sdk.InitCallback;
import com.nbmediation.sdk.NmAds;
import com.nbmediation.sdk.splash.SplashAd;
import com.nbmediation.sdk.splash.SplashAdListener;
import com.nbmediation.sdk.utils.error.Error;


public class SplashAdActivity extends Activity implements SplashAdListener {

    ViewGroup mSplashContainer;

    private boolean isClick = false;

    public static final String APPKEY = "kXDlKvOwFYf0inXBd65Pzo0vpF2utBim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_splash);
        mSplashContainer = findViewById(R.id.splash_container);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isClick) {
            startActivity(new Intent(this, UnityPlayerActivity.class));
            finish();
        }
    }

    public void init() {
        NmAds.init(this, APPKEY, new InitCallback() {
            @Override
            public void onSuccess() {
                Log.d("SplashAdActivity", "init success");
                loadSplash();
            }

            @Override
            public void onError(Error result) {
                Log.d("SplashAdActivity", "init failed");
                toMainPage();
            }
        });
    }

    public void loadSplash() {
        SplashAd.setSplashAdListener(this);
        int width = mSplashContainer.getWidth();
        int height = mSplashContainer.getHeight();
        SplashAd.setSize(width, height);
        SplashAd.setLoadTimeout(3000);
        SplashAd.loadAd();
    }

    @Override
    public void onSplashAdLoad() {
        Log.e("SplashAdActivity", "----------- onSplashAdLoad ----------");
        SplashAd.showAd(mSplashContainer);
    }

    @Override
    public void onSplashAdFailed(String error) {
        Log.e("SplashAdActivity", "----------- onSplashAdFailed ----------" + error);
        toMainPage();
    }

    @Override
    public void onSplashAdClicked() {
        isClick = true;
        Log.e("SplashAdActivity", "----------- onSplashAdClicked ----------");
    }

    @Override
    public void onSplashAdShowed() {
        Log.e("SplashAdActivity", "----------- onSplashAdShowed ----------");
    }

    @Override
    public void onSplashAdShowFailed(String error) {
        Log.e("SplashAdActivity", "----------- onSplashAdShowFailed ----------" + error);
        toMainPage();
    }

    @Override
    public void onSplashAdTick(long millisUntilFinished) {
        Log.e("SplashAdActivity", "----------- onSplashAdTick ----------" + millisUntilFinished);
        if(millisUntilFinished <= 0){
            if (!isClick) {
                toMainPage();
            }
        }
    }

    @Override
    public void onSplashAdDismissed() {
        Log.e("SplashAdActivity", "----------- onSplashAdDismissed ----------");
        toMainPage();
    }

    public void toMainPage() {
        Log.e("SplashAdActivity", "----------- toMainPage ----------");
        startActivity(new Intent(this, UnityPlayerActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        SplashAd.setSplashAdListener(null);
        super.onDestroy();
    }
}
