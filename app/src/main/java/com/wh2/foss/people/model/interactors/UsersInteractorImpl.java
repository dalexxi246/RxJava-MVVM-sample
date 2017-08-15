package com.wh2.foss.people.model.interactors;

import com.wh2.foss.people.model.User;
import com.wh2.foss.people.model.persistance.database.repositories.UsersDBRepositoryImpl;
import com.wh2.foss.people.model.persistance.database.repositories.UsersDBRepository;

import io.reactivex.Single;

public class UsersInteractorImpl implements UsersInteractor {

    private UsersDBRepository usersRepository;

    public UsersInteractorImpl() {
        this.usersRepository = new UsersDBRepositoryImpl();
    }

    @Override
    public Single<User> readUser(int UUID) {
        return usersRepository.readUser(UUID);
    }

    @Override
    public Single<User> login(String user, String password) {
        return usersRepository.readUser(user, password);
    }

    @Override
    public Single<Boolean> createUser(User user) {
        return usersRepository.createUser(user);
    }

    @Override
    public Single<Boolean> updateUser(User user) {
        return usersRepository.updateUser(user);
    }

    @Override
    public Single<Boolean> deleteUser(int UUID) {
        return usersRepository.deleteUser(UUID);
    }
}
