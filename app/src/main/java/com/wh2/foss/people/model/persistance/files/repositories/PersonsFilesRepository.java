package com.wh2.foss.people.model.persistance.files.repositories;

import android.graphics.Bitmap;

import io.reactivex.Single;

public interface PersonsFilesRepository {

    Single<Bitmap> loadProfilePhoto(String path);
    Single<String> saveProfilePhoto(String newFileName, Bitmap path);

}
