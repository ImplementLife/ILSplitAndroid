package com.impllife.split.ui.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.NotificationInfo;
import com.impllife.split.service.DataService;
import com.impllife.split.service.util.Formatters;
import com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter;
import com.impllife.split.ui.custom.component.BaseView;
import com.impllife.split.ui.dialog.NotifyProcessingDialog;
import com.impllife.split.ui.fragment.NotifyListFragment;
import com.impllife.split.ui.fragment.SettingsFragment;

import static com.impllife.split.data.constant.Constant.*;
import static com.impllife.split.service.util.Util.bundle;

public class NotifyInfoListItem extends AltRecyclerViewListAdapter.Data<NotificationInfo> {
    private final DataService dataService = DataService.getInstance();
    private final NotifyListFragment ownerFragment;
    private NotificationInfo data;

    public NotifyInfoListItem(NotifyListFragment ownerFragment, NotificationInfo data) {
        super(R.layout.view_notify_info_list_item);
        this.ownerFragment = ownerFragment;
        setData(data);
    }

    public void setData(NotificationInfo data) {
        this.data = data;
    }

    public void bindData(BaseView view) {
        if (!data.isProcessed()) {
            NotifyProcessingDialog dialog = new NotifyProcessingDialog(data, c -> {
                Bundle bundle = bundle(NOTIFY_TO_TRN_SUM, c);
                bundle.putString(NOTIFY_TO_TRN_DSCR, data.getTitle() + ": " + data.getText());
                bundle.putInt(NOTIFY_ID, data.getId());
                bundle.putLong(NOTIFY_TO_TRN_DATE, data.getPostDate().getTime());
                ownerFragment.navigate(R.id.fragment_transaction_setup, bundle);
            });
            view.setOnClickListener(v -> dialog.show());
        } else {
            ImageView imgDone = view.findViewById(R.id.img_processed);
            imgDone.setImageResource(R.drawable.ic_svg_done);
        }
        view.getRoot().setLongClickable(true);
        view.getRoot().setOnLongClickListener(v -> {
            new NotifyListFragment.IgnoreDialog(ownerFragment, data).show(ownerFragment.getParentFragmentManager(), "ignore_dialog");
            return true;
        });
        ImageView icon = view.findViewById(R.id.img_app_icon);
        dataService.loadAppIcon(data.getAppPackage()).ifPresent(icon::setImageDrawable);
        view.setTextViewById(R.id.tv_app_name, data.getAppName() + " at " + Formatters.formatHHMM(data.getPostDate()));
        view.setTextViewById(R.id.tv_dscr, data.getTitle() + ": " + data.getText());
    }

    @Override
    public NotificationInfo getData() {
        return data;
    }

}
