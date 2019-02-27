package com.example.abhishek.linuxnotificationapp.Home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhishek.linuxnotificationapp.R;
import com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.Model;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    private List<Model> list;
    private WeakReference<HomeContract.itemClickListener> itemClickListener;

    public NotificationAdapter(@NonNull List<Model> list, WeakReference<HomeContract.itemClickListener> itemClickListener) {
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_notification, viewGroup, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i) {
        notificationViewHolder.titleTextview.setText(list.get(i).getTitle());
        notificationViewHolder.bodyTextview.setText(list.get(i).getBody());
        notificationViewHolder.datetimeTextview.setText(list.get(i).getDatetime());

        notificationViewHolder.setCLickListener(itemClickListener,i, notificationViewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
