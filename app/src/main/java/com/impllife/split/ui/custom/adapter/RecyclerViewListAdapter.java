package com.impllife.split.ui.custom.adapter;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.impllife.split.ui.custom.component.BaseView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;

import static com.impllife.split.ui.custom.adapter.RecyclerViewListAdapter.CustomViewHolder;

public class RecyclerViewListAdapter<D> extends RecyclerView.Adapter<CustomViewHolder> {
    private final List<ViewData<D>> data;
    private final BiConsumer<D, BaseView> binder;

    public RecyclerViewListAdapter(BiConsumer<D, BaseView> binder) {
        this(new ArrayList<>(), binder);
    }
    public RecyclerViewListAdapter(List<ViewData<D>> data, BiConsumer<D, BaseView> binder) {
        this.data = data;
        this.binder = binder;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getViewId();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(viewType, parent);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        ViewData<D> t = data.get(position);
        if (!t.bind(holder.view)) binder.accept(t.getData(), holder.view);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }
    public void add(ViewData<D> item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }
    public void addAll(List<ViewData<D>> all) {
        int size = data.size();
        data.addAll(all);
        notifyItemRangeInserted(size - 1, all.size());
    }
    public void replaceAll(List<ViewData<D>> replace) {
        clear();
        addAll(replace);
    }
    public void clear() {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }
    public void sort(Comparator<? super ViewData<D>> comparator) {
        data.sort(comparator);
        notifyDataSetChanged();
    }

    public List<ViewData<D>> getData() {
        return data;
    }
    public ViewData<D> get(int position) {
        return data.get(position);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private final BaseView view;

        public CustomViewHolder(BaseView view) {
            super(view.getRoot());
            this.view = view;
        }
        public CustomViewHolder(int viewId, ViewGroup rootForThis) {
            this(new BaseView(viewId, rootForThis));
        }
    }

    public static class ViewData<T> {
        private final int viewId;
        private final T data;
        private BiConsumer<T, BaseView> binder;

        public ViewData(int viewId, T data) {
            this.viewId = viewId;
            this.data = data;
        }
        public ViewData(int viewId, T data, BiConsumer<T, BaseView> binder) {
            this(viewId, data);
            this.binder = binder;
        }

        public boolean bind(BaseView view) {
            if (binder != null) {
                binder.accept(data, view);
                return true;
            }
            return false;
        }

        public int getViewId() {
            return viewId;
        }
        public T getData() {
            return data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ViewData<?> viewData = (ViewData<?>) o;

            return getData() != null ? getData().equals(viewData.getData()) : viewData.getData() == null;
        }

        @Override
        public int hashCode() {
            return getData() != null ? getData().hashCode() : 0;
        }
    }
}