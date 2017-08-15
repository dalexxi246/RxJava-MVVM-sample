package com.wh2.foss.people.model.interactors;

import com.wh2.foss.people.model.User;

import io.reactivex.Single;

public interface UsersInteractor {
    Single<User> readUser(int UUID);
    Single<User> login(String user, String password);
    Single<Boolean> createUser(User user);
    Single<Boolean> updateUser(User user);
    Single<Boolean> deleteUser(int UUID);
}
