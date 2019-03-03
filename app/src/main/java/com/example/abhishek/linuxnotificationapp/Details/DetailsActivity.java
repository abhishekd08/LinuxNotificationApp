package com.example.abhishek.linuxnotificationapp.Details;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.abhishek.linuxnotificationapp.R;
import com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.DataItem;
import com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.Model;

public class DetailsActivity extends AppCompatActivity implements DetailsContract.View {

    private DetailsPresenter presenter;

    private ConstraintLayout parentLayout;
    private TextView titleTextview, bodyTextview, datetimeTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        presenter = new DetailsPresenter(this, this);

        parentLayout = findViewById(R.id.detailsactivity_parent_layout);
        titleTextview = findViewById(R.id.detailsactivity_title_textview);
        bodyTextview = findViewById(R.id.detailsactivity_body_textview);
        datetimeTextview = findViewById(R.id.detailsactivity_datetime_textview);

        int id = getIntent().getIntExtra("id", -1);
        presenter.getDetails(id);
    }

    @Override
    public void updateUI(@NonNull DataItem data) {
        titleTextview.setText(data.getTitle());
        bodyTextview.setText(data.getBody());
        datetimeTextview.setText(data.getDatetime());
    }

    @Override
    public void showSnackbar(@NonNull String msg) {
        Snackbar snackbar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_SHORT);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        }).setActionTextColor(ContextCompat.getColor(DetailsActivity.this, R.color.colorWhite));
        snackbar.show();
    }

    @Override
    public void deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteNotification();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void afterItemDeleted() {
        finish();
    }

    public void onDeleteFabClicked(View view) {
        deleteDialog();
    }
}
