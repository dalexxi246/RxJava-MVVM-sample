package com.wh2.foss.people.model.interactors;

import android.content.Context;
import android.graphics.Bitmap;

import com.wh2.foss.people.model.persistance.files.repositories.PersonsFilesRepository;
import com.wh2.foss.people.model.Person;
import com.wh2.foss.people.model.persistance.database.repositories.PersonsDBRepositoryImpl;
import com.wh2.foss.people.model.persistance.database.repositories.PersonsDBRepository;
import com.wh2.foss.people.model.persistance.files.repositories.PersonsFilesRepositoryImpl;

import io.reactivex.Observable;
import io.reactivex.Single;

public class PersonsInteractorImpl implements PersonsInteractor {

    private PersonsDBRepository personsDBRepository;
    private PersonsFilesRepository personsFilesRepository;

    public PersonsInteractorImpl(Context context) {
        this.personsDBRepository = new PersonsDBRepositoryImpl();
        this.personsFilesRepository = new PersonsFilesRepositoryImpl(context);
    }

    @Override
    public Single<Person> readPerson(int documentType, long documentNumber) {
        return personsDBRepository.readPerson(documentType, documentNumber);
    }

    @Override
    public Observable<Person> readAllPersons() {
        return personsDBRepository.readAllPersons();
    }

    @Override
    public Observable<Person> searchByDocumentAndName(long documentNumber, String name) {
        return documentNumber == 0L ?
                personsDBRepository.readAllPersons().filter(person -> person.getFirstName().toUpperCase().concat(person.getLastName().toUpperCase()).contains(name.toUpperCase())):
                personsDBRepository.readAllPersons().filter(person -> (person.getDocumentNumber() + "").contains(documentNumber + ""));
    }

    @Override
    public Single<Boolean> createPerson(Person person) {
        return personsDBRepository.createPerson(person);
    }

    @Override
    public Single<Boolean> updatePerson(Person person) {
        return personsDBRepository.updatePerson(person);
    }

    @Override
    public Single<Boolean> deletePerson(Person person) {
        return personsDBRepository.deletePerson(person);
    }

    @Override
    public Single<Bitmap> loadImage(String path) {
        return personsFilesRepository.loadProfilePhoto(path);
    }

    @Override
    public Single<String> saveImage(String newFileName, Bitmap bitmap) {
        return personsFilesRepository.saveProfilePhoto(newFileName, bitmap);
    }
}
