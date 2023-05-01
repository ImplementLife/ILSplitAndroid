package com.impllife.split.service;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.function.Consumer;

import static com.impllife.split.service.NotifyService.processNotify;
import static com.impllife.split.service.NotifyService.startWithNotify;

public class NotifyListener extends NotificationListenerService {
    private static NotifyListener instance;
    public static boolean isWork() {
        return instance != null && instance.isWork;
    }
    public static void stop() {
        if (instance != null) instance.stopCommand();
    }

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
    private boolean isWork;

    private void stopCommand() {
        stopForeground(STOP_FOREGROUND_REMOVE);
        stopSelf();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("NotifyListener.onCreate", "start");
        startWithNotify(this);

        isWork = true;
        instance = this;
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        StatusBarNotification[] notifications = getActiveNotifications();
        Log.i("NotifyListener.onListenerConnected", "notifications count: " + notifications.length);
        for (StatusBarNotification notify : notifications) {
            processNotify(this, notify);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("NotifyListener.onDestroy", "start");
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
        Log.i("NotifyListener.onNotificationPosted", "start");
        try {
            processNotify(this, sbn);
        } catch (Throwable e) {
            Log.e("NotifyListener.onNotificationPosted", "error", e);
        }
        Log.i("NotifyListener.onNotificationPosted", "end");
    }
}
