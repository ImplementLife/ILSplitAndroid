package com.impllife.split.ui.custom.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.impllife.split.ui.MainActivity;

import java.util.Arrays;
import java.util.List;

public class ListAdapter<T> extends ArrayAdapter<T> {
    protected final List<T> list;
    protected final ViewFabric<T> fabric;

    public ListAdapter(List<T> list, ViewFabric<T> fabric) {
        super(MainActivity.getInstance(), 0);
        this.list = list;
        this.addAll(list);
        this.fabric = fabric;
    }
    public ListAdapter(T[] list, ViewFabric<T> fabric) {
        this(Arrays.asList(list), fabric);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return fabric.createView(list.get(position), parent);
    }

    @FunctionalInterface
    public interface ViewFabric<T> {
        View createView(T data, ViewGroup parent);
    }
}
