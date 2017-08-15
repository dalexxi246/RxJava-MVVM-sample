package com.wh2.foss.people.model.persistance.database.tables;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.wh2.foss.people.model.Person;
import com.wh2.foss.people.model.persistance.database.PeopleDatabase;
import com.wh2.foss.people.model.persistance.database.tables.base.DefaultBaseModel;

@Table(database = PeopleDatabase.class)
public class PersonTable extends DefaultBaseModel<Person> {

    @Column @PrimaryKey
    private int documentType;
    @Column @PrimaryKey
    private long documentNumber;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String homeAddress;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private String photoPath;

    public PersonTable() {
    }

    public PersonTable(int documentType, int documentNumber, String firstName, String lastName, String homeAddress, String phone, String email, String photoPath) {
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeAddress = homeAddress;
        this.phone = phone;
        this.email = email;
        this.photoPath = photoPath;
    }

    public PersonTable(Person copy) {
        this.documentType = copy.getDocumentType();
        this.documentNumber = copy.getDocumentNumber();
        this.firstName = copy.getFirstName();
        this.lastName = copy.getLastName();
        this.homeAddress = copy.getHomeAddress();
        this.phone = copy.getPhone();
        this.email = copy.getEmail();
        this.photoPath = copy.getPhotoPath();
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(long documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public Person convertToModel() {
        return new Person(documentType, documentNumber, firstName, lastName, homeAddress, phone, email, photoPath);
    }
}
