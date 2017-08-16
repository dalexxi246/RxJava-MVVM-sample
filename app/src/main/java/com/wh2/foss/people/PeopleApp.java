package com.wh2.foss.people;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.wh2.foss.people.model.persistance.database.PeopleDatabase;
import com.wh2.foss.people.model.persistance.database.tables.PersonTable;
import com.wh2.foss.people.model.persistance.database.tables.UserTable;

public class PeopleApp extends Application {

    private static final String TAG = "PeopleApp.java";

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
        populateDatabase();
    }

    private void populateDatabase() {

        UserTable[] users = {
                new UserTable(1, "walex6hh@gmail.com", "themixer246", "Wilmer Alexis Hurtado"),
                new UserTable(2, "aborraez@gmail.com", "aborraez", "Alfredo Borraez"),
                new UserTable(3, "android123@gmail.com", "android", "Alfredo Borraez"),
                new UserTable(4, "a@b.co", "c", "Dummy")
        };
        FlowManager.getDatabase(PeopleDatabase.class)
                .beginTransactionAsync(
                        new ProcessModelTransaction.Builder<>((ProcessModelTransaction.ProcessModel<UserTable>) (userTable, wrapper) -> userTable.save()).addAll(users).build())
                .error((transaction, error) -> error.printStackTrace())
                .success(transaction -> Log.i(TAG, "Users table initialized"))
                .build().execute();


        PersonTable[] persons= {
                new PersonTable(1, 112233, "Pepo", "Martinez", "Calle 75", "998837", "astro@lobo.com", ""),
                new PersonTable(3, 444555666, "Roberto", "Takeshi", "Cra. 101", "998837", "moni@dosi.com", ""),
                new PersonTable(4, 778884499, "Marina", "Perdegoni", "Calle 98", "0310029939", "perde@goni.com", "")

        };
        FlowManager.getDatabase(PeopleDatabase.class)
                .beginTransactionAsync(
                        new ProcessModelTransaction.Builder<>((ProcessModelTransaction.ProcessModel<PersonTable>) (personTable, wrapper) -> personTable.save()).addAll(persons).build())
                .error((transaction, error) -> error.printStackTrace())
                .success(transaction -> Log.i(TAG, "Persons table initialized"))
                .build().execute();
    }

    public Context getContext() {
        return getApplicationContext();
    }
}
