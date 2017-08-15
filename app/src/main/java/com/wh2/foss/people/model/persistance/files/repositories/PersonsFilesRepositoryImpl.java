package com.wh2.foss.people.model.persistance.files.repositories;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.wh2.foss.people.exceptions.FilesException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Single;

public class PersonsFilesRepositoryImpl implements PersonsFilesRepository {

    private Context context;

    public static final String IMAGES_FOLDER = "persons";
    public static final String IMAGES_EXTENSION = ".jpg";

    public PersonsFilesRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public Single<Bitmap> loadProfilePhoto(String path) {
        return Single.fromCallable(() -> {
            try {
                File f = new File(path);
                return BitmapFactory.decodeStream(new FileInputStream(f));
            } catch (FileNotFoundException e) {
                throw new FilesException(String.format("Image %1s not found", path), e);
            }
        });
    }

    @Override
    public Single<String> saveProfilePhoto(String newFileName, Bitmap path) {
        return Single.fromCallable(() -> {
            ContextWrapper cw = new ContextWrapper(context);
            File dirImages = cw.getDir(IMAGES_FOLDER, Context.MODE_PRIVATE);
            File myPath = new File(dirImages, newFileName + IMAGES_EXTENSION);

            FileOutputStream fos;
            try{
                fos = new FileOutputStream(myPath);
                path.compress(Bitmap.CompressFormat.JPEG, 60, fos);
                fos.flush();
            } catch (IOException ex){
                throw new FilesException(String.format("Image %1s%2s not saved", newFileName, IMAGES_EXTENSION), ex);
            }
            return myPath.getAbsolutePath();
        });
    }
}
