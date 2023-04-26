package com.impllife.split.service;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import com.impllife.split.R;
import com.impllife.split.ui.MainActivity;

import static android.app.NotificationManager.*;
import static android.app.PendingIntent.*;
import static android.content.Context.*;
import static com.impllife.split.service.NotifyListener.Command.STOP;

public class NotifyService {
    public static final String CHANNEL_ID = "il_split";
    public static void sendNotification(String text, Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Split", IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
        Intent intent = new Intent(context, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("act", "notify");
        intent.putExtras(bundle);
        PendingIntent pendingIntent = getActivity(context, 0, intent, FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_svg_split_ico)
            .setContentTitle("Split")
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, notificationBuilder.build());
    }

    public static void startWithNotify(Service service) {
        String channelId = "il_split_listener";
        createChannel(service, channelId, "Listener \uD83D\uDC40");
        // Create Pending Intents.
        PendingIntent piLaunchMainActivity = getActivity(service, 0, new Intent(service, MainActivity.class), FLAG_UPDATE_CURRENT);
        Intent stopIntent = new Intent(service, NotifyListener.class);
        stopIntent.putExtra("com", STOP.toString());
        PendingIntent piStopService = getActivity(service, 0, stopIntent, FLAG_UPDATE_CURRENT);

        // Action to stop the service.
        Notification.Action btnStop = new Notification.Action.Builder(R.drawable.ic_svg_close, "stop", piStopService).build();

        // Create a notification.
        Notification notify = new Notification.Builder(service, channelId)
            .setContentTitle("Listening notifications \uD83D\uDC40")
            .setSmallIcon(R.drawable.ic_svg_split_ico)
            .setContentIntent(piLaunchMainActivity)
            .setActions(btnStop)
            .setStyle(new Notification.BigTextStyle())
            .build();

        service.startForeground(1, notify);
    }

    private static void createChannel(Context context, String channelId, String channelName) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);
    }
}
