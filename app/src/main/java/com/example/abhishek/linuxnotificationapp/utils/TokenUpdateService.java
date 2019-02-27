package com.example.abhishek.linuxnotificationapp.utils;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.abhishek.linuxnotificationapp.utils.Firebase.FirebaseUtils;
import com.example.abhishek.linuxnotificationapp.utils.NetworkUtils.MyWebService;

public class TokenUpdateService extends IntentService {

    public static final String TAG = "TokenUpdateService";

    public TokenUpdateService(String name) {
        super(name);
    }

    public TokenUpdateService(){
        super("TokenUpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: Called");
        FirebaseUtils.fetchToken(getApplicationContext());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sharedPreferences.getString("token", null);
        String mail = sharedPreferences.getString("mail", null);
        String pass = sharedPreferences.getString("pass", null);
        if (token != null && mail != null && pass != null) {
            MyWebService myWebService = MyWebService.getInstance(getApplicationContext());
            myWebService.updateToken(mail, pass, token);

//            Add this code if needed in future
//            ServerResponse serverResponse = MyWebService.getServerResponseInstance();
//            serverResponse.setResponseReceiveListener(new ResponseReceiveListener() {start
//                @Override
//                public void onResponseReceive(JSONObject responseObject) {
//                    try {
//                        String from = responseObject.getString("from");
//                        if (from.equals("updateToken")){
//                            if (responseObject.getString("result").equals("success")){
//                                //TOKEN UPDATED SUCCESSFULLY
//                            }else{
//                                //TOKEN NOT UPDATED
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

        } else {
            if (token == null) {
                Log.e(TAG, "onHandleIntent: token is null !");
                FirebaseUtils.fetchToken(getApplicationContext());
            } else if (mail == null || pass == null) {
                Log.e(TAG, "onHandleIntent: Mail or Pass is null !");
                //TODO This condition will never occur
            }
        }
    }
}
