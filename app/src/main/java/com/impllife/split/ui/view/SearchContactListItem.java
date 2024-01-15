package com.impllife.split.ui.view;

import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.ui.custom.adapter.UniversalRVListAdapter;
import com.impllife.split.ui.custom.component.BaseView;

import java.util.function.Consumer;

public class SearchContactListItem extends UniversalRVListAdapter.ModelViewData<People> {
    private Consumer<People> callback;
    public SearchContactListItem(People data, Consumer<People> callback) {
        super(R.layout.view_people_list_item, data);
        this.callback = callback;
    }

    @Override
    public void bindData(BaseView view) {
        view.setTextViewById(R.id.tv_name, data.getPseudonym());
        view.setOnClickListener(v -> callback.accept(data));
    }
}
