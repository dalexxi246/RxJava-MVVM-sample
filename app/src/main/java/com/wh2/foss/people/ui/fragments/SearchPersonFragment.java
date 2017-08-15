package com.wh2.foss.people.ui.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.wh2.foss.people.ui.viewmodels.PersonViewModel;
import com.wh2.foss.people.R;
import com.wh2.foss.people.databinding.FragmentSearchPersonBinding;
import com.wh2.foss.people.model.Person;
import com.wh2.foss.people.ui.adapter.ListPersonSuggestionsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchPersonFragment extends Fragment implements ListPersonSuggestionsAdapter.OnItemClickListener {

    FragmentSearchPersonBinding binding;
    PersonViewModel viewModel;

    ListPersonSuggestionsAdapter adapter;
    RecyclerView recyclerView;

    OnFragmentInteractionListener onFragmentInteractionListener;

    List<PersonViewModel> persons;

    public SearchPersonFragment() {}

    public static SearchPersonFragment newInstance() {
        SearchPersonFragment fragment = new SearchPersonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_person, container, false);
        viewModel = PersonViewModel.getInstance(getContext());
        binding.setVm(viewModel);
        initViews();
        setupUIListeners();
        return binding.getRoot();
    }

    private void initViews() {
        recyclerView = binding.listSuggestedPersons.listSuggestedPersons;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new ListPersonSuggestionsAdapter(new ArrayList<>(), this, getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void setupUIListeners() {
        RxTextView
                .textChanges(binding.txtSearchTerm)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter((str) -> !TextUtils.isEmpty(str))
                .subscribe(charSequence -> {
                    persons = new ArrayList<>();
                    viewModel.performSearchPerson()
                            .subscribe(
                            person -> persons.add(new PersonViewModel(person, getContext())),
                            error -> binding.txtSearchTerm.setTextColor(getResources().getColor(android.R.color.holo_red_dark)),
                            () -> adapter.setItems(persons));
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setOnFragmentInteractionListener(OnFragmentInteractionListener onFragmentInteractionListener) {
        this.onFragmentInteractionListener = onFragmentInteractionListener;
    }

    @Override
    public void onPersonSelected(Person p) {
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.onClickPerson(p);
        }
    }

    public interface OnFragmentInteractionListener {
        void onClickPerson(Person p);
    }

}
