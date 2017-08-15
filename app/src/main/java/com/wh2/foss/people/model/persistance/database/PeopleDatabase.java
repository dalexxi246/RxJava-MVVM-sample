package com.wh2.foss.people.model.persistance.database;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = PeopleDatabase.NAME, version = PeopleDatabase.VERSION)
public class PeopleDatabase {

    public static final int VERSION = 1;
    public static final String NAME = "database";

}
