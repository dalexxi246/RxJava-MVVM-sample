package com.wh2.foss.people.model.persistance.database.repositories;

import com.wh2.foss.people.model.User;

import io.reactivex.Single;

public interface UsersDBRepository {

    Single<User> readUser(int UUID);
    Single<User> readUser(String username, String password);
    Single<Boolean> createUser(User user);
    Single<Boolean> updateUser(User user);
    Single<Boolean> deleteUser(int UUID);

}
