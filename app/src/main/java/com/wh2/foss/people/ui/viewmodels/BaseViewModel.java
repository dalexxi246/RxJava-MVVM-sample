package com.wh2.foss.people.ui.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;

abstract class BaseViewModel extends BaseObservable {

    private Context context;
    final String EMPTY_STRING = "";

    BaseViewModel(Context context) {
        this.context = context;
    }

    String getString(int stringId) {
        return context.getResources().getString(stringId);
    }


}
