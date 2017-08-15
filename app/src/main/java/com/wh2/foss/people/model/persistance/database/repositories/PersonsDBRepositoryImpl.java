package com.wh2.foss.people.model.persistance.database.repositories;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wh2.foss.people.exceptions.NotFoundException;
import com.wh2.foss.people.exceptions.UpdateDatabaseException;
import com.wh2.foss.people.model.Person;
import com.wh2.foss.people.model.persistance.database.tables.PersonTable;
import com.wh2.foss.people.model.persistance.database.tables.PersonTable_Table;

import io.reactivex.Observable;
import io.reactivex.Single;

public class PersonsDBRepositoryImpl implements PersonsDBRepository {

    @Override
    public Single<Person> readPerson(int documentType, Long documentNumber) {
        return Single.fromCallable(() -> {
            PersonTable row =
                    SQLite.select()
                            .from(PersonTable.class)
                            .where(PersonTable_Table.documentType.eq(documentType))
                            .and(PersonTable_Table.documentNumber.eq(documentNumber))
                            .querySingle();
            if (row == null) {
                throw new NotFoundException("Person doesn't exists");
            }
            return row.convertToModel();
        });
    }

    @Override
    public Observable<Person> readAllPersons() {
        return Observable.fromIterable(
                SQLite.select()
                        .from(PersonTable.class)
                        .queryList()
        ).map(PersonTable::convertToModel);
    }

    @Override
    public Single<Boolean> createPerson(Person person) {
        return Single.fromCallable(() -> {
            PersonTable row = new PersonTable(person);
            if (row.insert() == -1) {
                throw new UpdateDatabaseException("Person exists");
            }
            return true;
        });
    }

    @Override
    public Single<Boolean> updatePerson(Person person) {
        return Single.fromCallable(() -> {
            PersonTable row = new PersonTable(person);
            if (!row.update()) {
                throw new UpdateDatabaseException("Can't update. Person exists");
            }
            return row.update();
        });
    }

    @Override
    public Single<Boolean> deletePerson(Person person) {
        return Single.fromCallable(() -> {
            PersonTable row = new PersonTable(person);
            if (!row.delete()) {
                throw new NotFoundException("Person doesn't exists");
            }
            return row.delete();
        });
    }
}
