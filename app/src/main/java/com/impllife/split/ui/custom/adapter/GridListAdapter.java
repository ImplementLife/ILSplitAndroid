package com.impllife.split.ui.custom.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.impllife.split.ui.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridListAdapter<T> extends ArrayAdapter<T> {
    protected final List<T> list;
    protected final ViewFabric<T> fabric;

    public GridListAdapter(List<T> list, ViewFabric<T> fabric) {
        super(MainActivity.getInstance(), 0);
        this.list = list;
        this.addAll(list);
        this.fabric = fabric;
    }
    public GridListAdapter(T[] list, ViewFabric<T> fabric) {
        this(Arrays.asList(list), fabric);
    }
    public GridListAdapter(ViewFabric<T> fabric) {
        this(new ArrayList<>(), fabric);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return fabric.createView(list.get(position), parent);
    }

    public void remove(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    public void add(T item, int position) {
        list.add(position, item);
    }

    @FunctionalInterface
    public interface ViewFabric<T> {
        View createView(T data, ViewGroup parent);
    }
}
