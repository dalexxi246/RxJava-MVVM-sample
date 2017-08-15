package com.wh2.foss.people.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.wh2.foss.people.R;
import com.wh2.foss.people.databinding.ActivityLoginBinding;
import com.wh2.foss.people.exceptions.NotFoundException;
import com.wh2.foss.people.ui.NavigationHandler;
import com.wh2.foss.people.ui.viewmodels.LoginViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    LoginViewModel viewModel;

    CompositeDisposable subscriptions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        viewModel = new LoginViewModel(this);
        binding.setVm(viewModel);

        subscriptions = new CompositeDisposable();
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
        if (!subscriptions.isDisposed()) {
            subscriptions.dispose();
        }
    }

    private void setupUI() {
        Glide.with(this).load(R.drawable.splash).into(binding.imgArt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void setupSubscriptions() {
        subscriptions.add(
                RxTextView.textChanges(binding.txtUsername)
                        .skipInitialValue()
                        .doOnNext(charSequence -> viewModel.username.set(charSequence.toString()))
                        .map(charSequence -> viewModel.validateLogin(charSequence))
                        .subscribe(usernameIsValid -> {
                            binding.edtxUsername.setError(usernameIsValid);
                            activateLoginButton(viewModel.validate());
                        }));

        subscriptions.add(
                RxTextView.textChanges(binding.txtPassword)
                        .skipInitialValue()
                        .doOnNext(charSequence -> viewModel.password.set(charSequence.toString()))
                        .map(charSequence -> viewModel.validatePassword(charSequence))
                        .subscribe(usernameIsValid -> {
                            binding.edtxPassword.setError(usernameIsValid);
                            activateLoginButton(viewModel.validate());
                        }));

        subscriptions.add(
                RxView.clicks(binding.btnLogin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> viewModel.makeLogin().subscribe(user -> {
                    NavigationHandler.toMain(LoginActivity.this, user);
                    finish();
                }, e -> {
                    if (e instanceof NotFoundException)
                        Snackbar.make(binding.getRoot(), R.string.message_credentials_invalid, Snackbar.LENGTH_SHORT).show();
                })));

        if (binding.exitButton != null) {
            subscriptions.add(RxView.clicks(binding.exitButton).subscribe(o -> finish()));
        }
    }

    private void activateLoginButton(boolean isValidForm) {
        binding.btnLogin.setEnabled(isValidForm);
        if (!isValidForm) {
            viewModel.setMessage(getString(R.string.message_login_form_error_1));
        } else {
            viewModel.setMessage("");
        }
    }
}
