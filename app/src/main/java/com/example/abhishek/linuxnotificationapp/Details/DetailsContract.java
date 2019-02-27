package com.example.abhishek.linuxnotificationapp.Details;

import android.support.annotation.NonNull;

import com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.DataItem;
import com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.Model;

public interface DetailsContract {

    interface View {

        public void updateUI(@NonNull DataItem data);

        public void showSnackbar(@NonNull String msg);

        public void deleteDialog();

        public void afterItemDeleted();
    }

    interface changeListener {

        public void getDetails(int id);

        public void deleteNotification();
    }

}