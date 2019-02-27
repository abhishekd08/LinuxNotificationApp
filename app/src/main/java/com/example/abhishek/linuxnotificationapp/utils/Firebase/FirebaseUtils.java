package com.example.abhishek.linuxnotificationapp.utils.Firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;

public class FirebaseUtils {

    public static final String TAG = "FirebaseUtils";

    public static String fetchToken(final Context context) {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {

                            String token = task.getResult().getToken();
                            Log.d(TAG, "onComplete: New Token : " + token);
                            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
                            edit.putString("token", token);
                            edit.commit();

                        } else {
                            Log.e(TAG, "onComplete: ", task.getException());
                        }
                    }
                });

        return "";
    }

}
