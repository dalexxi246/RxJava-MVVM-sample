package com.wh2.foss.people.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.wh2.foss.people.model.Person;
import com.wh2.foss.people.ui.fragments.BiometryFragment;
import com.wh2.foss.people.ui.fragments.EditPersonFragment;
import com.wh2.foss.people.ui.fragments.SearchPersonFragment;
import com.wh2.foss.people.ui.viewmodels.PersonViewModel;
import com.wh2.foss.people.R;
import com.wh2.foss.people.databinding.ActivityEditPersonBinding;

import io.reactivex.annotations.NonNull;

public class EditPersonActivity extends AppCompatActivity implements SearchPersonFragment.OnFragmentInteractionListener, Toolbar.OnMenuItemClickListener {

    public static final String EXTRA_MODE = "mode";

    public static final String ACTION = "com.wh2.biometry.EDIT_PERSON";
    public static final int REQUEST_EDIT = 1;
    public static final int REQUEST_NEW = 2;

    public static final String RESULT_MESSAGE = "message";

    private int mode;

    private ActivityEditPersonBinding binding;
    private PersonViewModel viewModel;

    private BiometryFragment biometryFragment;
    private SearchPersonFragment searchPersonFragment;

    FragmentTransaction transaction;
    private AlertDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);
        mode = getIntent().getIntExtra(EXTRA_MODE, REQUEST_NEW);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_person);
        viewModel = PersonViewModel.getInstance(this);
        viewModel.setPerson(new Person());
        setupUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupUI() {
        dialog = new AlertDialog.Builder(this).create();
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        biometryFragment = BiometryFragment.newInstance(mode);
        searchPersonFragment = SearchPersonFragment.newInstance();
        searchPersonFragment.setOnFragmentInteractionListener(this);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_left, EditPersonFragment.newInstance(mode));
        switch (mode) {
            case REQUEST_NEW:
                transaction.add(R.id.container_right, biometryFragment);
                break;
            case REQUEST_EDIT:
                transaction.add(R.id.container_right, searchPersonFragment);
                break;
        }
        transaction.commit();
        setupUIListeners();
    }

    private void setupUIListeners() {
        binding.toolbar.setOnMenuItemClickListener(this);
        binding.toolbar.setNavigationOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void showErrorAlert(@NonNull Throwable e) {
        if (e instanceof SQLiteConstraintException) {
            Snackbar.make(binding.getRoot(), "Persona ya registrada en la base de datos", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(binding.getRoot(), e.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void backToMainActivity(@NonNull String s) {
        Intent result = new Intent(ACTION, Uri.EMPTY);
        result.putExtra(RESULT_MESSAGE, s);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_user, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void confirmUpdate() {
        String s = viewModel.validate();
        if (TextUtils.isEmpty(s)) {
            switch (mode) {
                case REQUEST_NEW:
                    dialog.setTitle("¿Confirma Actualizacion?");
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "GUARDAR", (dialog1, which) -> viewModel.performCreatePerson().subscribe(this::backToMainActivity, this::showErrorAlert));
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCELAR", (dialog1, which) -> dialog1.dismiss());
                    dialog.show();
                    break;
                case REQUEST_EDIT:
                    dialog.setTitle("¿Actualizar Datos?");
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "GUARDAR", (dialog1, which) -> viewModel.performEditPerson().subscribe(this::backToMainActivity, this::showErrorAlert));
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCELAR", (dialog1, which) -> dialog1.dismiss());
                    dialog.show();
                    break;
            }
        } else {
            Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickPerson(Person p) {
        viewModel.setPerson(p);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_left, EditPersonFragment.newInstance(REQUEST_EDIT));
        transaction.replace(R.id.container_right, biometryFragment);
        transaction.commit();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        transaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.action_new_person:
                mode = REQUEST_NEW;
                transaction.replace(R.id.container_left, EditPersonFragment.newInstance(REQUEST_NEW));
                transaction.replace(R.id.container_right, biometryFragment);
                transaction.commit();
                return true;
            case R.id.action_save_person:
                confirmUpdate();
                return true;
            case R.id.action_search_person:
                mode = REQUEST_EDIT;
                transaction.replace(R.id.container_left, EditPersonFragment.newInstance(REQUEST_EDIT));
                transaction.replace(R.id.container_right, searchPersonFragment);
                transaction.commit();
                return true;
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
        }
        transaction.commit();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
