package com.wh2.foss.people.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wh2.foss.people.model.Person;
import com.wh2.foss.people.ui.viewmodels.PersonViewModel;
import com.wh2.foss.people.R;
import com.wh2.foss.people.databinding.ItemSuggestedPersonBinding;

import java.util.List;

public class ListPersonSuggestionsAdapter extends RecyclerView.Adapter<ListPersonSuggestionsAdapter.PersonViewHolder>{

    private List<PersonViewModel> items;
    private OnItemClickListener listener;
    private Context context;

    public ListPersonSuggestionsAdapter(List<PersonViewModel> items, OnItemClickListener listener, Context context) {
        this.items = items;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggested_person, parent, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        PersonViewModel p = items.get(position);
        holder.bind(p);
        holder.getBinding().labelNameNumber.setText(String.format(context.getString(R.string.label_person_inline_nombre_completo_tipo_numero), p.getLastName(), p.getFirstName(), p.getDocumentTypeShort(), p.getDocumentNumber()));
        holder.getBinding().getRoot().setOnClickListener(v -> listener.onPersonSelected(p.getPerson()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<PersonViewModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        private ItemSuggestedPersonBinding itemBinding;

        public PersonViewHolder(View itemView) {
            super(itemView);
            itemBinding = DataBindingUtil.bind(itemView);
        }

        public ItemSuggestedPersonBinding getBinding() {
            return itemBinding;
        }

        public void bind(PersonViewModel p) {
            itemBinding.setVm(p);
            itemBinding.executePendingBindings();
        }
    }

    public interface OnItemClickListener {
        void onPersonSelected(Person p);
    }

}
