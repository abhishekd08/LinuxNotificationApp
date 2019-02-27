package com.example.abhishek.linuxnotificationapp.utils.Firebase;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.Model;
import com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.NotificationsDB;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationHandler extends FirebaseMessagingService {

    public final String TAG = "NotificationHandler";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "onMessageReceived: Called");

        String title = remoteMessage.getNotification().getTitle();
        String[] msg = remoteMessage.getNotification().getBody().split("MSG ");
        String body = msg[1];
        String datetime = msg[0];

        Log.d(TAG, "onMessageReceived: Data : title:"+title+", body:"+body+", datetime:"+datetime+" --- END");

        Model item = new Model(title,body,datetime);

        NotificationsDB db = NotificationsDB.getInstance(getApplicationContext());
        db.notificationsDao().addItem(item);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("newNotification",true);
    }
}
