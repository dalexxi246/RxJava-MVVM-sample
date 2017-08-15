package com.wh2.foss.people.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wh2.foss.people.ui.NavigationHandler;
import com.wh2.foss.people.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        NavigationHandler.toLogin(SplashActivity.this);
        finish();
    }

}
