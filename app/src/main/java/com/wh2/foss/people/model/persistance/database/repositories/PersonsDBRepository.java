package com.wh2.foss.people.model.persistance.database.repositories;

import com.wh2.foss.people.model.Person;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface PersonsDBRepository {

    Single<Person> readPerson(int documentType, Long documentNumber);
    Observable<Person> readAllPersons();
    Single<Boolean> createPerson(Person person);
    Single<Boolean> updatePerson(Person person);
    Single<Boolean> deletePerson(Person person);

}
