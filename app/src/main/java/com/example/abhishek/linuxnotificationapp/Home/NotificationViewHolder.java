package com.example.abhishek.linuxnotificationapp.Home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.abhishek.linuxnotificationapp.R;

import java.lang.ref.WeakReference;

public class NotificationViewHolder extends RecyclerView.ViewHolder {

    public final String TAG = "NotificationViewHolder";

    TextView titleTextview, bodyTextview, datetimeTextview;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);

        titleTextview = itemView.findViewById(R.id.notificationcard_title_textview);
        bodyTextview = itemView.findViewById(R.id.notificationcard_body_textview);
        datetimeTextview = itemView.findViewById(R.id.notificationcard_datetime_textview);
    }

    public void setCLickListener(@NonNull WeakReference<HomeContract.itemClickListener> itemClickListener, int p, View itemView) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.get().onRowItemClick(p);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemClickListener.get().onRowItemLongClick(p);
                return true;
            }
        });
    }

}
