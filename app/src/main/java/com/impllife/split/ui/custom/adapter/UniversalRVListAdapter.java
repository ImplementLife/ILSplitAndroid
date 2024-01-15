package com.impllife.split.ui.custom.adapter;

import android.annotation.SuppressLint;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.impllife.split.ui.custom.component.BaseView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.impllife.split.ui.custom.adapter.UniversalRVListAdapter.Holder;

public class UniversalRVListAdapter extends RecyclerView.Adapter<Holder> {
    private final List<ModelViewData<?>> data;
    public UniversalRVListAdapter() {
        this(new ArrayList<>());
    }
    public UniversalRVListAdapter(List<ModelViewData<?>> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getViewId();
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewId) {
        return new Holder(new BaseView(viewId, parent));
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
    public void add(ModelViewData<?> item) {
        data.add(item);
        notifyItemInserted(data.size());
    }
    public void add(ModelViewData<?> item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }
    public void addAll(List<ModelViewData<?>> all) {
        int size = data.size();
        data.addAll(all);
        notifyItemRangeInserted(size - 1, all.size());
    }
    public void replaceAll(List<ModelViewData<?>> replace) {
        clear();
        addAll(replace);
    }
    public void clear() {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void sort(Comparator<? super ModelViewData<?>> comparator) {
        data.sort(comparator);
        notifyDataSetChanged();
    }

    public List<ModelViewData<?>> getData() {
        return data;
    }
    public ModelViewData<?> get(int position) {
        return data.get(position);
    }

    public static abstract class ModelViewData<D> {
        protected final int viewId;
        protected D data;

        public ModelViewData(int viewId) {
            this.viewId = viewId;
        }
        public ModelViewData(int viewId, D data) {
            this(viewId);
            this.data = data;
        }

        public int getViewId() {
            return viewId;
        }
        public void setData(D data) {
            this.data = data;
        }
        public D getData() {
            return data;
        }
        public abstract void bindData(BaseView view);
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private final BaseView view;
        public Holder(BaseView view) {
            super(view.getRoot());
            this.view = view;
        }
        public BaseView getView() {
            return view;
        }
    }
}