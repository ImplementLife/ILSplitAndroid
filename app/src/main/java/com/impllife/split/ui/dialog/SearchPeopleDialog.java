package com.impllife.split.ui.dialog;

import android.os.Bundle;
import android.text.Editable;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.EditText;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.ui.MainActivity;
import com.impllife.split.ui.custom.CustomDialog;
import com.impllife.split.ui.custom.TextWatcherAdapter;
import com.impllife.split.ui.custom.adapter.UniversalRVListAdapter;
import com.impllife.split.ui.custom.component.CustomRecyclerView;
import com.impllife.split.ui.view.SearchContactListItem;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.impllife.split.ui.custom.adapter.UniversalRVListAdapter.ModelViewData;

public class SearchPeopleDialog extends CustomDialog {
    private EditText fieldQuery;

    private CustomRecyclerView list;
    private UniversalRVListAdapter adapter;
    private Consumer<People> callback;

    private final List<People> dataForSearch;
    private People result;

    public SearchPeopleDialog(List<People> dataForSearch) {
        this.dataForSearch = dataForSearch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_search_people);
        getWindow().setLayout(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        fieldQuery = findViewById(R.id.field_query);
        fieldQuery.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().toLowerCase();
                Comparator<ModelViewData<?>> comparator = Comparator
                    .comparing((ModelViewData<?> p) -> {
                        Object data = p.getData();
                        if (data instanceof People) {
                            return !((People) data).getPseudonym().toLowerCase().startsWith(query);
                        }
                        return false;
                    })
                    .thenComparing((ModelViewData<?> p) -> {
                        Object data = p.getData();
                        if (data instanceof People) {
                            return ((People) data).getPseudonym().toLowerCase();
                        }
                        return null;
                    });
                adapter.sort(comparator);
            }
        });

        list = findViewById(R.id.list);
        adapter = new UniversalRVListAdapter();
        list.setAdapter(adapter);
        adapter.replaceAll(dataForSearch.stream().map(e -> new SearchContactListItem(e, callback)).collect(Collectors.toList()));
    }

    public void setCallback(Consumer<People> callback) {
        this.callback = (response) -> {
            callback.accept(response);
            dismiss();
        };
    }

    public void showKeyboard() {
        MainActivity.getInstance().showKeyboard(fieldQuery);
    }
}
