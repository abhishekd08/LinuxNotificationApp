package com.example.abhishek.linuxnotificationapp.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.abhishek.linuxnotificationapp.SignUp.SignUpActivity;
import com.example.abhishek.linuxnotificationapp.utils.Callbacks.ResponseReceiveListener;
import com.example.abhishek.linuxnotificationapp.utils.Callbacks.ServerResponse;
import com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.Model;
import com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.NotificationsDB;
import com.example.abhishek.linuxnotificationapp.utils.NetworkUtils.MyWebService;
import com.example.abhishek.linuxnotificationapp.utils.TokenUpdateService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomePresenter implements HomeContract.changeListener, ResponseReceiveListener {

    public final String TAG = "HomePresenter";

    private Context context;
    private HomeContract.View homeViewUi;
    private NotificationAdapter adapter;
    private List<Model> list;
    private NotificationsDB db;
    private MyWebService webService;
    private ServerResponse serverResponse;

    public HomePresenter(@NonNull HomeContract.View homeViewUi, @NonNull Context context, @NonNull WeakReference<HomeContract.itemClickListener> itemClickListener) {
        this.context = context;
        this.homeViewUi = homeViewUi;

        db = NotificationsDB.getInstance(context);
        webService = MyWebService.getInstance(context);
        serverResponse = MyWebService.getServerResponseInstance();
        serverResponse.setResponseReceiveListener(this);

        list = new ArrayList<>();
        //setFakeData();
        //list = db.notificationsDao().getAll();
        if (list.size() == 0) {
            refreshNotifications();
        }

        adapter = new NotificationAdapter(list, itemClickListener);
        }

    //TEMP
    public void setFakeData() {

        ArrayList<Model> list = new ArrayList<>();
        Date date = new Date();
        for (int i = 0; i < 10; i++) {
            Model item = new Model("title " + i, "This is body " + i, "2-12-2018 16:43:56");
            list.add(item);
        }
        //db.notificationsDao().addAll(list);
    }

    @Override
    public void updateToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                            if (!sharedPreferences.getString("token", "").equals(task.getResult().getToken())) {
                                homeViewUi.callTokenUpdateService();
                            }
                        } else {
                            Log.e(TAG, "UpdateToken Error: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void refreshNotifications() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String mail = sharedPreferences.getString("mail", null);
        String pass = sharedPreferences.getString("pass", null);
        if (mail != null || pass != null) {
            webService.getAllNotifications(mail, pass);
        } else {
            logout(true);
        }
    }

    @Override
    public void logout(boolean isCrashed) {
        if (isCrashed) {
            homeViewUi.showDialogAuthError();
        } else {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
            editor.clear().commit();
            db.notificationsDao().deleteAll();
            Intent intent = new Intent(context, SignUpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }

    @Override
    public NotificationAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void refreshNotificationsLocally() {
        list.clear();
        list.addAll(db.notificationsDao().getAll());
        if (list.size() < 1) {
            homeViewUi.showNoNotificationUI(true);
        } else {
            homeViewUi.showNoNotificationUI(false);
        }
        homeViewUi.showProgressbar(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemId(int position) {
        int itemId = list.get(position).getId();
        return itemId;
    }

    @Override
    public void onResponseReceive(JSONObject responseObject) {
        try {
            String from = responseObject.getString("from");
            if (from.equals("getAllNotifications")) {
                String result = responseObject.getString("result");
                if (result.equals("success")) {
                    JSONArray data = responseObject.getJSONArray("data");
                    ArrayList<Model> tempList = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        String title = item.getString("title");
                        String body = item.getString("body");
                        String datetime = item.getString("datetime");
                        tempList.add(new Model(title, body, datetime));
                    }
                    db.notificationsDao().deleteAll();
                    db.notificationsDao().addAll(tempList);
                    refreshNotificationsLocally();

                } else {
                    String reason = responseObject.getString("reason");
                    if (reason.equals("database error")) {
                        homeViewUi.showSnackBar("Some error in fetching notifications !");
                    } else if (reason.equals("no notifications")) {
                        homeViewUi.showNoNotificationUI(true);
                    } else if (reason.equals("user not registered")) {
                        logout(true);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
