package com.example.abhishek.linuxnotificationapp;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.Model;
import com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.NotificationsDB;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NewNotificationService extends FirebaseMessagingService {

    public final String TAG = "NewNotificationService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();

        String body = remoteMessage.getNotification().getBody();

        String datetime = remoteMessage.getData().get("datetime");

//        String[] s = remoteMessage.getNotification().getBody().split("MSG ");
        //s[0] : datetime
        //s[1] : body

        Model item = new Model(title, body, datetime);
        NotificationsDB db = NotificationsDB.getInstance(getApplicationContext());
        db.notificationsDao().addItem(item);

        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("new_notification_intent"));

    }
}
