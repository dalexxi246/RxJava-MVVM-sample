package com.wh2.foss.people.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    private int documentType;
    private long documentNumber;
    private String firstName;
    private String lastName;
    private String homeAddress;
    private String phone;
    private String email;
    private String photoPath;

    /*<string-array name="list_person_tipdoc">
        <item>Tipo de Documento</item>
        <item>C.C. (Cedula de ciudadania)</item>
        <item>T.I. (Tarjeta de identidad)</item>
        <item>R.C. (Registro civil)</item>
        <item>P. (Pasaporte)</item>
        <item>Otro</item>
    </string-array>*/

    public Person(int documentType, long documentNumber, String firstName, String lastName, String homeAddress, String phone, String email, String photoPath) {
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeAddress = homeAddress;
        this.phone = phone;
        this.email = email;
        this.photoPath = photoPath;
    }

    protected Person(Parcel in) {
        documentType = in.readInt();
        documentNumber = in.readLong();
        firstName = in.readString();
        lastName = in.readString();
        homeAddress = in.readString();
        phone = in.readString();
        email = in.readString();
        photoPath = in.readString();
    }

    public Person() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(documentType);
        dest.writeLong(documentNumber);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(homeAddress);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(photoPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

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

}
