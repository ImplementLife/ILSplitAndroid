package com.impllife.split.service;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.NotificationInfo;
import com.impllife.split.data.jpa.entity.NotifyAppInfo;
import com.impllife.split.data.jpa.provide.AppDatabase;
import com.impllife.split.data.jpa.provide.NotifyAppInfoDao;
import com.impllife.split.data.jpa.provide.NotifyInfoDao;
import com.impllife.split.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.app.PendingIntent.getActivity;
import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.app.NotificationCompat.*;
import static com.impllife.split.BuildConfig.APPLICATION_ID;
import static java.util.concurrent.CompletableFuture.runAsync;

public class NotifyService {
    private static final List<String> defaultIgnoreApps = Arrays.asList(APPLICATION_ID, "android");
    private static int createID() {
        return Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(new Date()));
    }
    private static AppDatabase db;
    private static AppDatabase getDB(Context context) {
        if (db == null || !db.isOpen()) {
            db = AppDatabase.init(context);
        }
        return db;
    }

    private static boolean isIgnore(Context context, String pack) {
        NotifyAppInfoDao notifyAppInfoDao = getDB(context).getNotifyAppInfoDao();
        List<String> ignoreApps = notifyAppInfoDao.findAllByIgnore(true).stream()
            .map(NotifyAppInfo::getPack)
            .collect(Collectors.toList());
        return defaultIgnoreApps.contains(pack) || ignoreApps.contains(pack);
    }

    public static void processNotify(Context context, StatusBarNotification sbn) {
        Log.i("NotifyService.processNotify", "start");
        runAsync(() -> {
            String pack = sbn.getPackageName();
            if (isIgnore(context, pack)) return;
            Log.i("NotifyService.processNotify", "process package: " + pack + " id:" + sbn.getId());

            Bundle extras = sbn.getNotification().extras;
            NotificationInfo info = new NotificationInfo();
            info.setTitle((String) extras.getCharSequence("android.title", "err collect"));
            info.setText((String) extras.getCharSequence("android.text", sbn.getNotification().tickerText));
            info.setPostDate(new Date(sbn.getPostTime()));
            info.setAppPackage(pack);
            info.setAppName(getAppName(context, pack));

            NotifyInfoDao notifyInfoDao = getDB(context).getNotifyInfoDao();
            if (!notifyInfoDao.findAllByAppPackage(pack).contains(info)) {
                notifyInfoDao.insert(info);
            }
        });
        Log.i("NotifyService.processNotify", "end");
    }

    private static String getAppName(Context context, String pack) {
        String appName;
        try {
            PackageManager pm = context.getPackageManager();
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
        return appName;
    }

    public static void sendNotification(String text, Context context) {
        String channelId = "il_split";
        createChannel(context, channelId, "Notify's");

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("act", "notify");
        PendingIntent pendingIntent = getActivity(context, 0, intent, FLAG_UPDATE_CURRENT);

        Builder notificationBuilder = new Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_svg_split_ico)
            .setContentTitle("Split")
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(createID(), notificationBuilder.build());
    }

    public static void startWithNotify(Service service) {
        Log.i("NotifyService.startWithNotify", "start");
        String channelId = "il_split_listener";
        createChannel(service, channelId, "Listener \uD83D\uDC40");
        // Create Pending Intents.
        Intent intent = new Intent(service, MainActivity.class);
        intent.putExtra("act", "notify");
        PendingIntent piLaunchMainActivity = getActivity(service, 0, intent, FLAG_UPDATE_CURRENT);
        // Create a notification.
        Notification notify = new Builder(service, channelId)
            .setContentTitle("Listening notifications \uD83D\uDC40")
            .setSmallIcon(R.drawable.ic_svg_split_ico)
            .setContentIntent(piLaunchMainActivity)
            .setStyle(new BigTextStyle())
            .setPriority(PRIORITY_MAX)
            .build();

        service.startForeground(1, notify);
        Log.i("NotifyService.startWithNotify", "end");
    }

    private static void createChannel(Context context, String channelId, String channelName) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);
    }
}
