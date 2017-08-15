package com.wh2.foss.people.ui.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.wh2.foss.people.R;
import com.wh2.foss.people.databinding.FragmentEditPersonBinding;
import com.wh2.foss.people.model.Person;
import com.wh2.foss.people.ui.activities.EditPersonActivity;
import com.wh2.foss.people.ui.viewmodels.PersonViewModel;

public class EditPersonFragment extends Fragment implements SearchPersonFragment.OnFragmentInteractionListener{

    private PersonViewModel viewModel;
    private FragmentEditPersonBinding binding;
    private int mode;

    public EditPersonFragment() {}

    public static EditPersonFragment newInstance(int mode) {
        EditPersonFragment fragment = new EditPersonFragment();
        Bundle args = new Bundle();
        args.putInt(EditPersonActivity.EXTRA_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mode = getArguments().getInt(EditPersonActivity.EXTRA_MODE);
        }
    }

    public Person getPersonData(){
        return viewModel.getPerson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_person, container, false);
        viewModel = PersonViewModel.getInstance(getContext());
        if (mode == EditPersonActivity.REQUEST_NEW) {
            viewModel.setPerson(new Person());
        }
        binding.setVm(viewModel);
        setupUI();
        return binding.getRoot();
    }

    private void setupUI() {
        binding.spinTipdoc.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.list_person_tipdoc)));
        switch (mode) {
            case EditPersonActivity.REQUEST_NEW:
                binding.txtNrodoc.setEnabled(true);
                binding.spinTipdoc.setEnabled(true);
                break;
            case EditPersonActivity.REQUEST_EDIT:
                binding.txtNrodoc.setEnabled(false);
                binding.spinTipdoc.setEnabled(false);
                break;
        }
        binding.spinTipdoc.setSelection(getPersonData().getDocumentType());
        setupUIListeners();
    }

    private void setupUIListeners() {
        RxAdapterView.itemSelections(binding.spinTipdoc).skipInitialValue().subscribe(integer -> viewModel.setDocumentType(integer));
//        binding.spinTipdoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                viewModel.setDocumentType(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        RxTextView.textChanges(binding.txtNrodoc)
                .skipInitialValue()
                .doOnNext(charSequence -> viewModel.setDocumentNumber(charSequence.toString()))
                .map(charSequence -> viewModel.checkEmpty(charSequence))
                .subscribe(
                        nroDocIsValid -> binding.edtxNrodoc.setError(nroDocIsValid),
                        throwable -> binding.edtxNrodoc.setError(throwable.getLocalizedMessage()));

        RxTextView.textChanges(binding.txtNombres)
                .skipInitialValue()
                .doOnNext(charSequence -> viewModel.setFirstName(charSequence.toString()))
                .map(charSequence -> viewModel.checkEmpty(charSequence))
                .subscribe(message -> binding.edtxNombres.setError(message));

        RxTextView.textChanges(binding.txtApellidos)
                .skipInitialValue()
                .doOnNext(charSequence -> viewModel.setLastName(charSequence.toString()))
                .map(charSequence -> viewModel.checkEmpty(charSequence))
                .subscribe(message -> binding.edtxApellidos.setError(message));

        RxTextView.textChanges(binding.txtDireccionResidencia)
                .skipInitialValue()
                .doOnNext(charSequence -> viewModel.setHomeAddress(charSequence.toString()))
                .map(charSequence -> viewModel.checkEmpty(charSequence))
                .subscribe(message -> binding.edtxDireccionResidencia.setError(message));

        RxTextView.textChanges(binding.txtEmail)
                .skipInitialValue()
                .doOnNext(charSequence -> viewModel.setEmail(charSequence.toString()))
                .map(charSequence -> viewModel.checkEmail(charSequence))
                .subscribe(message -> binding.edtxEmail.setError(message));

        RxTextView.textChanges(binding.txtTelefono)
                .skipInitialValue()
                .doOnNext(charSequence -> viewModel.setPhone(charSequence.toString()))
                .map(charSequence -> viewModel.checkEmpty(charSequence))
                .subscribe(message -> binding.edtxTelefono.setError(message));
    }

    @Override
    public void onClickPerson(Person p) {
        viewModel.setPerson(p);
        binding.txtNrodoc.setEnabled(false);
        binding.spinTipdoc.setEnabled(false);
    }
}
