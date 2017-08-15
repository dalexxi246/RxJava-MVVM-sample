package com.wh2.foss.people.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupSubscriptions();
        setupUIListeners();
    }

    protected abstract void setupUIListeners();

    protected abstract void setupSubscriptions();

}
