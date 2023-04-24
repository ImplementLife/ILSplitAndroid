package com.impllife.split.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.NotificationInfo;
import com.impllife.split.data.jpa.provide.AppDatabase;
import com.impllife.split.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.impllife.split.BuildConfig.APPLICATION_ID;
import static java.util.concurrent.CompletableFuture.runAsync;

public class NotifyListener extends NotificationListenerService {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat();
    private static final String CHANNEL_ID = "il_split";
    private static final List<String> ignoreApp = Arrays.asList(APPLICATION_ID, "android");

    private AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = AppDatabase.init(getApplicationContext());
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.i("NotifyListener", "onNotificationPosted : " + dateFormat.format(new Date()));
        String pack = sbn.getPackageName();
        if (ignoreApp.contains(pack)) return;

        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = (String) extras.getCharSequence("android.text");

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
        String finalAppName = appName;
        runAsync(() -> {
            NotificationInfo info = new NotificationInfo();
            info.setAppPackage(pack);
            info.setAppName(finalAppName);
            info.setTitle(title);
            info.setText(text);

            db.getNotificationDao().insert(info);
        });
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("NotifyListener", "onNotificationRemoved : " + dateFormat.format(new Date()));
    }

    private void sendNotification(String text) {
        sendNotification(text, this);
    }
    public static void sendNotification(String text, Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Split", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
        Intent intent = new Intent(context, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("act", "notify");
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_svg_split_ico)
            .setContentTitle("Split")
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, notificationBuilder.build());
    }

}
