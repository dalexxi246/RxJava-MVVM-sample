package com.wh2.foss.people.model.interactors;

import android.graphics.Bitmap;

import com.wh2.foss.people.model.Person;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface PersonsInteractor {
    Single<Person> readPerson(int documentType, long documentNumber);
    Observable<Person> readAllPersons();
    Observable<Person> searchByDocumentAndName(long documentNumber, String name);
    Single<Boolean> createPerson(Person person);
    Single<Boolean> updatePerson(Person person);
    Single<Boolean> deletePerson(Person person);
    Single<Bitmap> loadImage(String path);
    Single<String> saveImage(String newFileName, Bitmap bitmap);
}
