package com.wh2.foss.people.ui.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.wh2.foss.people.R;
import com.wh2.foss.people.model.User;
import com.wh2.foss.people.model.interactors.UsersInteractor;
import com.wh2.foss.people.model.interactors.UsersInteractorImpl;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {

    public final ObservableField<String> username = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");

    private String message;

    private UsersInteractor usersInteractor;

    public LoginViewModel(Context context) {
        super(context);
        usersInteractor = new UsersInteractorImpl();
    }

    public String validateLogin(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) return getString(R.string.message_campo_obligatorio);
        if (!charSequence.toString().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") && !charSequence.toString().matches("^\\d+$")) return getString(R.string.message_formato_invalido);
        return EMPTY_STRING;
    }

    public String validatePassword(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) return getString(R.string.message_campo_obligatorio);
        return EMPTY_STRING;
    }

    public boolean validate() {
        return TextUtils.isEmpty(validateLogin(username.get())) && TextUtils.isEmpty(validatePassword(password.get()));
    }

    public Single<User> makeLogin() {
        return usersInteractor.login(username.get(), password.get()).compose(applySchedulers());
    }

    private SingleTransformer<User, User> applySchedulers() {
        return observable ->    observable.observeOn(Schedulers.io()).
                                subscribeOn(AndroidSchedulers.mainThread());
    }

    /** Accesors **/

    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        notifyPropertyChanged(com.wh2.foss.people.BR.message);
    }
}
