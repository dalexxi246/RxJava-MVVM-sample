package com.wh2.foss.people.model.persistance.database.repositories;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wh2.foss.people.model.User;
import com.wh2.foss.people.exceptions.NotFoundException;
import com.wh2.foss.people.model.persistance.database.tables.UserTable;
import com.wh2.foss.people.model.persistance.database.tables.UserTable_Table;

import io.reactivex.Single;

public class UsersDBRepositoryImpl implements UsersDBRepository {

    @Override
    public Single<User> readUser(int UUID) {
        return Single.fromCallable(() -> {
            UserTable row = SQLite.select().from(UserTable.class).where(UserTable_Table.UUID.eq(UUID)).querySingle();
            if (row == null) {
                throw new NotFoundException("User don't exists");
            } else {
                return row.convertToModel();
            }
        });
    }

    @Override
    public Single<User> readUser(String username, String password) {
        return Single.fromCallable(() -> {
            UserTable row = SQLite.select().from(UserTable.class).where(UserTable_Table.username.eq(username)).and(UserTable_Table.password.eq(password)).querySingle();
            if (row == null) {
                throw new NotFoundException("User don't exists");
            } else {
                return row.convertToModel();
            }
        });
    }

    @Override
    public Single<Boolean> createUser(User user) {
        return Single.fromCallable(() -> {
            UserTable row = new UserTable(user);
            long rowsAffected = row.insert();
            return rowsAffected != -1;
        });
    }

    @Override
    public Single<Boolean> updateUser(User user) {
        return Single.fromCallable(() -> {
            UserTable row = new UserTable(user);
            return row.update();
        });
    }

    @Override
    public Single<Boolean> deleteUser(int UUID) {
        return Single.fromCallable(() -> {
            UserTable row = new UserTable(UUID, null, null, null);
            return row.delete();
        });
    }
}
