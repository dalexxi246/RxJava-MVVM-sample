package com.wh2.foss.people.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.jakewharton.rxbinding2.view.RxView;
import com.wh2.foss.people.model.User;
import com.wh2.foss.people.R;
import com.wh2.foss.people.databinding.ActivityMainBinding;
import com.wh2.foss.people.ui.NavigationHandler;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    public static final String CURRENT_USER = "current_user";
    private ActivityMainBinding binding;

    CompositeDisposable subscriptions;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        user = getIntent().getParcelableExtra(CURRENT_USER);
        binding.setUser(user);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupUI();
        setupSubscriptions();
    }

    @Override
    protected void onStop() {
        super.onStop();
        subscriptions.clear();
    }

    private void setupUI() {
        binding.txtUsername.setText(String.format(getResources().getString(R.string.label_main_usuario), user.getFullName()));
    }

    private void setupSubscriptions() {
        subscriptions = new CompositeDisposable();
        subscriptions.add(RxView.clicks(binding.btnNewUser)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> NavigationHandler.toCreatePerson(this)));
        subscriptions.add(RxView.clicks(binding.btnEditUser)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> NavigationHandler.toEditPerson(this)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            Snackbar.make(binding.getRoot(), R.string.message_operation_canceled, Snackbar.LENGTH_SHORT).show();
        } else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EditPersonActivity.REQUEST_NEW:
                    Snackbar.make(binding.getRoot(), R.string.message_succesfully_created_person, Snackbar.LENGTH_SHORT).show();
                    break;
                case EditPersonActivity.REQUEST_EDIT:
                    Snackbar.make(binding.getRoot(), R.string.message_succesfully_updated_person, Snackbar.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
