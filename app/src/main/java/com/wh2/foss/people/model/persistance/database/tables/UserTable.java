package com.wh2.foss.people.model.persistance.database.tables;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.wh2.foss.people.model.User;
import com.wh2.foss.people.model.persistance.database.PeopleDatabase;
import com.wh2.foss.people.model.persistance.database.tables.base.DefaultBaseModel;

@Table(database = PeopleDatabase.class)
public class UserTable extends DefaultBaseModel<User> {

    @Column
    @PrimaryKey
    private int UUID;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String fullName;

    public UserTable() {
    }

    public UserTable(User user) {
        this.UUID = user.getUUID();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.fullName = user.getFullName();
    }

    public UserTable(int UUID, String username, String password, String fullName) {
        this.UUID = UUID;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUUID() {
        return UUID;
    }

    public void setUUID(int UUID) {
        this.UUID = UUID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public User convertToModel() {
        return new User(UUID, username, password, fullName);
    }
}
