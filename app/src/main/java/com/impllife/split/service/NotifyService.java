package com.impllife.split.service;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import com.impllife.split.R;
import com.impllife.split.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.app.PendingIntent.getActivity;
import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.app.NotificationCompat.*;

public class NotifyService {
    private static int createID() {
        return Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(new Date()));
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
    }

    private static void createChannel(Context context, String channelId, String channelName) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);
    }
}
