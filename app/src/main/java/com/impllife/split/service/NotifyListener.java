package com.impllife.split.service;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.impllife.split.data.jpa.entity.NotificationInfo;
import com.impllife.split.data.jpa.provide.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.impllife.split.BuildConfig.APPLICATION_ID;
import static com.impllife.split.service.NotifyService.startWithNotify;
import static com.impllife.split.service.Util.date;
import static java.util.concurrent.CompletableFuture.runAsync;

public class NotifyListener extends NotificationListenerService {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat();
    private static final List<String> ignoreApp = Arrays.asList(APPLICATION_ID, "android");
    private static NotifyListener instance;

    public enum Command {
        STOP(l -> l.stopCommand()),
        ;

        private final Consumer<NotifyListener> command;

        Command(Consumer<NotifyListener> command) {
            this.command = command;
        }

        void run(NotifyListener instance) {
            command.accept(instance);
        }

        public static void run(NotifyListener instance, String command) {
            if (command == null) return;
            valueOf(command).run(instance);
        }
    }

    private AppDatabase db;
    private boolean isWork;

    public static boolean isWork() {
        return instance != null && instance.isWork;
    }

    public static void stop() {
        if (instance != null) instance.stopCommand();
    }

    private void stopCommand() {
        stopForeground(STOP_FOREGROUND_REMOVE);
        stopSelf();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("NotifyListener.onCreate", date());
        startWithNotify(this);

        db = AppDatabase.init(getApplicationContext());
        isWork = true;
        instance = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("NotifyListener.onDestroy", date());
        isWork = false;
        instance = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Command.run(this, intent.getStringExtra("command"));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.i("NotifyListener.onNotificationPosted", date("start"));
        String pack = sbn.getPackageName();
        long postTime = sbn.getPostTime();
        int id = sbn.getId();
        Log.i("NotifyListener.onNotificationPosted", "sbn " + dateFormat.format(new Date(postTime)) + " " + id);
        if (ignoreApp.contains(pack)) return;

        NotificationInfo info = new NotificationInfo();
        Bundle extras = sbn.getNotification().extras;
        info.setTitle(extras.getString("android.title"));
        info.setText((String) extras.getCharSequence("android.text"));
        String appName;
        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(pack, 0);
            appName = (String) pm.getApplicationLabel(ai);
        } catch (PackageManager.NameNotFoundException e) {
            appName = Arrays.stream(pack.split("\\."))
                .filter(p -> !p.equals("com")
                    && !p.equals("apps")
                    && !p.equals("android")
                )
                .collect(Collectors.joining("."));
        }
        info.setAppName(appName);
        info.setAppPackage(pack);

        runAsync(() -> db.getNotificationDao().insert(info));
        Log.i("NotifyListener.onNotificationPosted", date("end"));
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("NotifyListener.onNotificationRemoved", date());
    }
}
