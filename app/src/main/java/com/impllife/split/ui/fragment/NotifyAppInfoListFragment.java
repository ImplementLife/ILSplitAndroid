package com.impllife.split.ui.fragment;

import com.impllife.split.R;
import com.impllife.split.data.jpa.provide.NotifyAppInfoDao;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.custom.adapter.UniversalRVListAdapter;
import com.impllife.split.ui.custom.component.CustomRecyclerView;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.view.NotifyAppInfoListItem;

import java.util.List;
import java.util.stream.Collectors;

public class NotifyAppInfoListFragment extends NavFragment {
    private final NotifyAppInfoDao notifyAppInfoDao = DataService.getInstance().getDb().getNotifyAppInfoDao();

    private CustomRecyclerView list;
    private UniversalRVListAdapter adapter;

    public NotifyAppInfoListFragment() {
        super(R.layout.fragment_notify_app_info_list, "Notifiers");
    }

    @Override
    protected void init() {
        list = findViewById(R.id.list);
        adapter = new UniversalRVListAdapter();
        list.setAdapter(adapter);

        updateListContent();
    }

    private void updateListContent() {
        runAsync(() -> {
            List<UniversalRVListAdapter.ModelViewData<?>> collect = notifyAppInfoDao.getAllOrderByIgnore()
                .stream().map(NotifyAppInfoListItem::new).collect(Collectors.toList());
            updateView(() -> {
                list.removeAllViews();
                adapter.addAll(collect);
            });
        });
    }
}
