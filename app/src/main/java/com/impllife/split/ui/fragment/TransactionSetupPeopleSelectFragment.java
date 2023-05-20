package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.view.BaseView;

import java.util.List;

public class TransactionSetupPeopleSelectFragment extends BaseFragment {
    private final DataService dataService = DataService.getInstance();

    private View peopleView;
    private View search;
    private RecyclerView listItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createView(R.layout.fragment_transaction_setup_people_select, inflater, container);
        init();
        initPeopleSelect();
        return view;
    }

    private void init() {
        search = findViewById(R.id.search);
        peopleView = findViewById(R.id.people_view);
        listItems = findViewById(R.id.people_view_list);
    }

    private void bundleProcessing() {
        Bundle arguments = getArguments();
        if (arguments != null) {

        }
    }

    private void initPeopleSelect() {
        peopleView.setVisibility(View.GONE);
        listItems.setVisibility(View.GONE);
        search.setOnClickListener(v -> {
            listItems.setVisibility(View.VISIBLE);
        });
        runAsync(() -> {
            List<People> peoples = dataService.getAllPeoples();
            ListAdapter listAdapter = new ListAdapter(peoples);

            listItems.setAdapter(listAdapter);
        });
    }
    private static class Holder extends RecyclerView.ViewHolder {
        private BaseView item;
        public Holder(BaseView item) {
            super(item.getRoot());
            this.item = item;
        }
        public void setData(People people) {
            item.setTextViewById(R.id.tv_name, people.getPseudonym());
        }
    }
    private class ListAdapter extends RecyclerView.Adapter<Holder> {
        private List<People> list;
        public ListAdapter(List<People> list) {
            this.list = list;
        }
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            BaseView baseView = new BaseView(inflater, R.layout.view_people, listItems);
            return new Holder(baseView);
        }
        public void onBindViewHolder(Holder holder, int position) {
            holder.setData(list.get(position));
        }
        public int getItemCount() {
            return list.size();
        }
    }
}