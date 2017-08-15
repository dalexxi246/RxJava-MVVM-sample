package com.wh2.foss.people.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.wh2.foss.people.BR;

public class User extends BaseObservable implements Parcelable {

    private int UUID;
    private String username;
    private String password;
    private String fullName;

    public User(int UUID, String username, String password, String fullName) {
        this.UUID = UUID;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    protected User(Parcel in) {
        UUID = in.readInt();
        username = in.readString();
        password = in.readString();
        fullName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(UUID);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(fullName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public int getUUID() {
        return UUID;
    }

    public void setUUID(int UUID) {
        this.UUID = UUID;
        notifyPropertyChanged(BR.uUID);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        notifyPropertyChanged(BR.fullName);
    }
}
