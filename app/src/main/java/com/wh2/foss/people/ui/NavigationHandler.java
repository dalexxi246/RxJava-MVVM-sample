package com.wh2.foss.people.ui;

import android.app.Activity;
import android.content.Intent;

import com.wh2.foss.people.model.User;
import com.wh2.foss.people.ui.activities.LoginActivity;
import com.wh2.foss.people.ui.activities.MainActivity;
import com.wh2.foss.people.ui.activities.EditPersonActivity;

public class NavigationHandler {

    public static void toLogin(Activity activity) {
        Intent starter = new Intent(activity, LoginActivity.class);
        activity.startActivity(starter);
    }

    public static void toMain(Activity activity, User user) {
        Intent starter = new Intent(activity, MainActivity.class);
        starter.putExtra(MainActivity.CURRENT_USER, user);
        activity.startActivity(starter);
    }

    public static void toCreatePerson(Activity activity) {
        Intent starter = new Intent(activity, EditPersonActivity.class);
        starter.putExtra(EditPersonActivity.EXTRA_MODE, EditPersonActivity.REQUEST_NEW);
        activity.startActivityForResult(starter, EditPersonActivity.REQUEST_NEW);
    }

    public static void toEditPerson(Activity activity) {
        Intent starter = new Intent(activity, EditPersonActivity.class);
        starter.putExtra(EditPersonActivity.EXTRA_MODE, EditPersonActivity.REQUEST_EDIT);
        activity.startActivityForResult(starter, EditPersonActivity.REQUEST_EDIT);
    }
}
