package com.wh2.foss.people.ui.fragments;


import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;
import com.wh2.foss.people.exceptions.FilesException;
import com.wh2.foss.people.model.Person;
import com.wh2.foss.people.ui.viewmodels.PersonViewModel;
import com.wh2.foss.people.R;
import com.wh2.foss.people.databinding.FragmentBiometryBinding;
import com.wh2.foss.people.ui.activities.EditPersonActivity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;

public class BiometryFragment extends Fragment implements SearchPersonFragment.OnFragmentInteractionListener {

    private static final int REQUEST_TAKE_PHOTO = 378;

    private PersonViewModel viewModel;
    private FragmentBiometryBinding binding;
    private int mode;

    public BiometryFragment() {}

    public static BiometryFragment newInstance(int mode) {
        BiometryFragment fragment = new BiometryFragment();
        Bundle args = new Bundle();
        args.putInt(EditPersonActivity.EXTRA_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mode = getArguments().getInt(EditPersonActivity.EXTRA_MODE, EditPersonActivity.REQUEST_NEW);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_biometry, container, false);
        viewModel = PersonViewModel.getInstance(getContext());
        binding.setVm(viewModel);
        setupUI();
        loadPictures();
        setupUIListeners();
        return binding.getRoot();
    }

    private void setupUI() {}

    private void setupUIListeners() {

        RxView.clicks(binding.btnTakePhoto).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(o -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK && data.getExtras() != null) {
                    viewModel.setPhotoTaken((Bitmap) data.getExtras().get("data"));
                    binding.imgPhoto.setImageBitmap(viewModel.getPhotoTaken());
                }
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void showErrorAlert(@NonNull Throwable e) {
        if (e instanceof FilesException) {
            if (e.getCause() instanceof IOException) {
                Snackbar.make(binding.getRoot(), "Algunas imagenes no se cargaron", Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(binding.getRoot(), e.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickPerson(Person p) {
        viewModel.setPerson(p);
        loadPictures();
    }

    private void loadPictures() {
        if (!hasPhoto(viewModel.getPerson())) {
            viewModel.performLoadPhoto().subscribe(bitmap -> {
                viewModel.setPhotoTaken(bitmap);
                binding.imgPhoto.setImageBitmap(bitmap);
            }, this::showErrorAlert);
        } else {
            viewModel.setPhotoTaken(null);
            viewModel.setSignatureTaken(null);
        }
    }

    private boolean hasPhoto(Person person) {
        return TextUtils.isEmpty(person.getPhotoPath());
    }
}
