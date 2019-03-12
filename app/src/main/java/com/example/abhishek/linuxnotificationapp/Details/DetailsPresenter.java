package com.example.abhishek.linuxnotificationapp.Details;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.DataItem;
import com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.Model;
import com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.NotificationsDB;
import com.example.abhishek.linuxnotificationapp.utils.NetworkUtils.MyWebService;

import java.util.List;

public class DetailsPresenter implements DetailsContract.changeListener {

    public final String TAG = "DetailsPresenter";

    private Context context;
    private DetailsContract.View detailsViewUi;
    private NotificationsDB db;
    private DataItem item;

    public DetailsPresenter(@NonNull Context context, @NonNull DetailsContract.View detailsViewUi) {
        this.context = context;
        this.detailsViewUi = detailsViewUi;

        db = NotificationsDB.getInstance(context);
    }

    @Override
    public void getDetails(int id) {
        if (id < 0) {
            Log.e(TAG, "getDetails: id ==== 0");
            detailsViewUi.showSnackbar("Some Error Occured !");
        } else {
            item = db.notificationsDao().getFromId(id);
            detailsViewUi.updateUI(item);
//            Log.d(TAG, "getDetails: " + item.toString());
        }
    }

    @Override
    public void deleteNotification() {
        db.notificationsDao().deleteItem(item.getId());
        if (db.notificationsDao().getFromId(item.getId()) == null) {
//            Log.d(TAG, "deleteNotification: Deleted");
            String mail = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getString("mail","");
            MyWebService.getInstance(context).deleteNotification(mail, item.getTitle(), item.getBody(), item.getDatetime());
            detailsViewUi.afterItemDeleted();
        } else {
//            Log.d(TAG, "deleteNotification: Not Deleted");
        }
    }
}
