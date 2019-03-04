package com.example.abhishek.linuxnotificationapp.Home;

import android.support.annotation.NonNull;

public interface HomeContract {

    interface View {

        public void callTokenUpdateService();

        public void showNoNotificationUI(boolean isTrue);

        public void showSnackBar(@NonNull String msg);

        public void showDialogAuthError();

        public void showProgressbar(boolean isVisible);

        public void scrollRecyclerviewToTop(int position);
    }

    interface changeListener {

        public void updateToken();

        public void refreshNotifications();

        public void logout(boolean isCrashed);

        public NotificationAdapter getAdapter();

        public void refreshNotificationsLocally();

        public int getItemId(int position);
    }

    interface itemClickListener{
        public void onRowItemClick(int position);
        public void onRowItemLongClick(int position);
    }
}
