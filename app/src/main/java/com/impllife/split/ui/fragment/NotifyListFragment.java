package com.impllife.split.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Switch;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.snackbar.Snackbar;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.NotificationInfo;
import com.impllife.split.data.jpa.entity.NotifyAppInfo;
import com.impllife.split.data.jpa.provide.NotifyAppInfoDao;
import com.impllife.split.data.jpa.provide.NotifyInfoDao;
import com.impllife.split.service.DataService;
import com.impllife.split.service.NotifyListener;
import com.impllife.split.ui.MainActivity;
import com.impllife.split.ui.custom.SwipeToDeleteCallback;
import com.impllife.split.ui.custom.adapter.RecyclerViewListAdapter;
import com.impllife.split.ui.custom.adapter.RecyclerViewListAdapter.ViewData;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.dialog.NotifyProcessingDialog;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static androidx.core.app.NotificationManagerCompat.getEnabledListenerPackages;
import static com.impllife.split.data.constant.Constant.*;
import static com.impllife.split.service.Util.bundle;

public class NotifyListFragment extends NavFragment {
    private DataService dataService = DataService.getInstance();
    private NotifyInfoDao notifyInfoDao = DataService.getInstance().getDb().getNotifyInfoDao();
    private MainActivity mainActivity = MainActivity.getInstance();
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH.mm.ss");

    private SwipeRefreshLayout refreshLayout;
    private Switch btnWork;
    private boolean notifyListenPermit;

    private RecyclerViewListAdapter<NotificationInfo> adapter;
    private RecyclerView recyclerView;

    public NotifyListFragment() {
        super(R.layout.fragment_notify_list, "Notifications");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("NotificationFragment", "onResume");
        notifyListenPermit = getEnabledListenerPackages(mainActivity).contains(mainActivity.getPackageName());

        if (notifyListenPermit && /*btnWork.isChecked() != */NotifyListener.isWork()) {
            mainActivity.startForegroundService(new Intent(mainActivity, NotifyListener.class));
        } else if (!notifyListenPermit) {
            NotifyListener.stop();
        }
        btnWork.setChecked(notifyListenPermit && NotifyListener.isWork());
    }

    @Override
    protected void init() {
        notifyListenPermit = getEnabledListenerPackages(mainActivity).contains(mainActivity.getPackageName());

        btnWork = findViewById(R.id.btn_work);
        btnWork.setOnClickListener(v -> {
            if (btnWork.isChecked()) {
                if (!notifyListenPermit) {
                    mainActivity.startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
                } else {
                    mainActivity.startForegroundService(new Intent(mainActivity, NotifyListener.class));
                }
            } else {
                Log.i("NotificationFragment", "off listener service");
                NotifyListener.stop();
                mainActivity.stopService(new Intent(mainActivity, NotifyListener.class));
            }
        });
        btnWork.setChecked(notifyListenPermit && NotifyListener.isWork());

        findViewById(R.id.btn_notify).setOnClickListener(v -> navController.navigate(R.id.fragment_notify_app_info_list));

        refreshLayout = findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(this::updateListContent);

        recyclerView = findViewById(R.id.list);
        adapter = new RecyclerViewListAdapter<>((data, view) -> {
            NotifyProcessingDialog dialog = new NotifyProcessingDialog(data, c -> {
                Bundle bundle = bundle(NOTIFY_TO_TRN_SUM, c);
                bundle.putString(NOTIFY_TO_TRN_DSCR, data.getText());
                bundle.putInt(NOTIFY_ID, data.getId());
                navigate(R.id.fragment_transaction_setup, bundle);
            });
            view.getRoot().setOnClickListener(v -> dialog.show());
            view.getRoot().setLongClickable(true);
            view.getRoot().setOnLongClickListener(v -> {
                new IgnoreDialog(this, data).show(this.getParentFragmentManager(), "ignore_dialog");
                return true;
            });
            ImageView icon = view.findViewById(R.id.img_app_icon);
            dataService.loadAppIcon(data.getAppPackage()).ifPresent(icon::setImageDrawable);
            view.setTextViewById(R.id.tv_app_name, data.getAppName() + " at " + dateFormat.format(data.getPostDate()));
            view.setTextViewById(R.id.tv_dscr, data.getTitle() + ": " + data.getText());
        });
        recyclerView.setAdapter(adapter);
        updateListContent();
        enableSwipeToDeleteAndUndo();
        getParentFragmentManager().setFragmentResultListener(FRAGMENT_RESULT_KEY, this, (key, bundle) -> {
            postDelayed(() -> {
                if (ACTION_TRN_CREATED_FRAGMENT.equals(bundle.getString(ACTION))) {
                    int id = bundle.getInt(NOTIFY_ID, -1);
                    if (id != -1) {
                        runAsync(() -> {
                            notifyInfoDao.deleteById(id);
                            post(() -> {
                                adapter.getData().stream()
                                    .filter(e -> e.getData().getId() == id)
                                    .findFirst().ifPresent(e -> {
                                        int position = adapter.getData().indexOf(e);
                                        adapter.remove(position);
                                    });
                            });
                        });
                    }
                }
            }, 700);
        });
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                RecyclerViewListAdapter.ViewData<NotificationInfo> item = adapter.get(position);
                runAsync(() -> {
                    notifyInfoDao.delete(item.getData());
                    updateView(() -> {
                        adapter.remove(position);

                        Snackbar snackbar = Snackbar.make(root, "Item removed.", Snackbar.LENGTH_LONG);
                        snackbar.setAction("UNDO", view -> {
                            runAsync(() -> {
                                notifyInfoDao.insert(item.getData());
                                updateView(() -> {
                                    adapter.add(item, position);
                                    recyclerView.scrollToPosition(position);
                                });
                            });
                        });
                        snackbar.setActionTextColor(Color.BLUE);
                        snackbar.show();
                    });
                });
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    @SuppressLint("SetTextI18n")
    private void updateListContent() {
        runAsync(() -> {
            List<NotificationInfo> allNotifyInfo = dataService.getAllNotifyInfo();
            updateView(() -> {
                List<ViewData<NotificationInfo>> collect = allNotifyInfo.stream()
                    .map(e -> new ViewData<>(R.layout.view_notify_info_list_item, e))
                    .collect(Collectors.toList());
                adapter.replaceAll(collect);
                recyclerView.scrollToPosition(0);

                refreshLayout.setRefreshing(false);
            });
        });
    }

    public static class IgnoreDialog extends DialogFragment {
        private final NotifyAppInfoDao notifyAppInfoDao = DataService.getInstance().getDb().getNotifyAppInfoDao();
        private final NotifyInfoDao notifyInfoDao = DataService.getInstance().getDb().getNotifyInfoDao();
        private final NotifyListFragment fragment;
        private final NotificationInfo info;

        public IgnoreDialog(NotifyListFragment fragment, NotificationInfo info) {
            this.fragment = fragment;
            this.info = info;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
            builder.setMessage("Ignore notifications from this app?")
                .setPositiveButton("Yes", (dialog, id) -> {
                    CompletableFuture.runAsync(() -> {
                        NotifyAppInfo appInfo = notifyAppInfoDao.findByPackage(info.getAppPackage());
                        boolean alreadyExist = appInfo != null;
                        if (!alreadyExist) {
                            appInfo = new NotifyAppInfo(info);
                        }
                        appInfo.setIgnore(true);
                        if (alreadyExist) {
                            notifyAppInfoDao.update(appInfo);
                        } else {
                            notifyAppInfoDao.insert(appInfo);
                        }

                        List<NotificationInfo> allByAppPackage = notifyInfoDao.findAllByAppPackage(info.getAppPackage());
                        if (allByAppPackage.size() > 1) {
                            new DropAnalogDialog(fragment, info).show(getParentFragmentManager(), "drop_analog_dialog");
                        }
                        CompletableFuture.runAsync(() -> {
                            notifyInfoDao.delete(info);
                            fragment.updateListContent();
                        });
                    });
                })
                .setNegativeButton("Cancel", (dialog, id) -> {});
            return builder.create();
        }
    }

    public static class DropAnalogDialog extends DialogFragment {
        private final NotifyInfoDao notifyInfoDao = DataService.getInstance().getDb().getNotifyInfoDao();
        private NotificationInfo info;
        private NotifyListFragment fragment;

        public DropAnalogDialog(NotifyListFragment fragment, NotificationInfo info) {
            this.info = info;
            this.fragment = fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
            builder.setMessage("Drop other info about notifications of this app?")
                .setPositiveButton("Yes", (dialog, id) -> {
                    CompletableFuture.runAsync(() -> {
                        notifyInfoDao.deleteByAppPackage(info.getAppPackage());
                        fragment.updateListContent();
                    });
                })
                .setNegativeButton("No", (dialog, id) -> {});
            return builder.create();
        }
    }
}