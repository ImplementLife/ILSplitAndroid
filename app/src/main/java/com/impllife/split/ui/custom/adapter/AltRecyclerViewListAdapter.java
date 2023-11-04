package com.impllife.split.ui.custom.adapter;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.impllife.split.ui.custom.component.BaseView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter.Holder;

public class AltRecyclerViewListAdapter extends RecyclerView.Adapter<Holder> {
    private final List<Data> data;

    public AltRecyclerViewListAdapter() {
        this(new ArrayList<>());
    }
    public AltRecyclerViewListAdapter(List<Data> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getViewId();
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewId) {
        return new Holder(new BaseView(viewId, parent), viewId);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        data.get(position).bindData(holder.getView());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }
    public void add(Data item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }
    public void addAll(List<Data> all) {
        int size = data.size();
        data.addAll(all);
        notifyItemRangeInserted(size - 1, all.size());
    }
    public void replaceAll(List<Data> replace) {
        clear();
        addAll(replace);
    }
    public void clear() {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }
    public void sort(Comparator<? super Data> comparator) {
        data.sort(comparator);
        notifyDataSetChanged();
    }

    public List<Data> getData() {
        return data;
    }
    public Data get(int position) {
        return data.get(position);
    }

    public static abstract class Data<D> {
        protected int viewId;

        public Data(int viewId) {
            this.viewId = viewId;
        }

        public int getViewId() {
            return viewId;
        }
        public abstract void bindData(BaseView view);
        public abstract D getData();
    }
    public static class Holder extends RecyclerView.ViewHolder {
        protected int viewId;
        private final BaseView view;

        public Holder(BaseView view, int viewId) {
            super(view.getRoot());
            this.viewId = viewId;
            this.view = view;
        }

        public BaseView getView() {
            return view;
        }

        public int getViewId() {
            return viewId;
        }
    }
}