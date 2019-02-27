package com.example.abhishek.linuxnotificationapp.Home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;


import com.example.abhishek.linuxnotificationapp.Details.DetailsActivity;
import com.example.abhishek.linuxnotificationapp.Model.DataItem;
import com.example.abhishek.linuxnotificationapp.R;
import com.example.abhishek.linuxnotificationapp.SignUp.SignUpActivity;
import com.example.abhishek.linuxnotificationapp.utils.TokenUpdateService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity implements
        HomeContract.View, HomeContract.itemClickListener {

    public static final String TAG = "HomeActivity";

    private ConstraintLayout parentLayout;
    private FrameLayout noNotificationLayout;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        presenter = new HomePresenter(this, this, new WeakReference<HomeContract.itemClickListener>(this));

        parentLayout = findViewById(R.id.homeactivity_parent_layout);
        noNotificationLayout = findViewById(R.id.homeactivity_no_notification_layout);
        toolbar = findViewById(R.id.homeactivity_toolbar);
        recyclerView = findViewById(R.id.homeactivity_recyclerview);
        progressBar = findViewById(R.id.homeactivity_progressbar);
        showProgressbar(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(presenter.getAdapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        setSupportActionBar(toolbar);


        callTokenUpdateService();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.refreshNotifications();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.homemenu_refresh:
                presenter.refreshNotifications();
                return true;

            case R.id.homemenu_logout:
                presenter.logout(false);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void callTokenUpdateService() {
        Intent serviceIntent = new Intent(HomeActivity.this, TokenUpdateService.class);
        startService(serviceIntent);
    }

    @Override
    public void showNoNotificationUI(boolean isTrue) {
        showProgressbar(false);
        if (isTrue) {
            recyclerView.setVisibility(View.INVISIBLE);
            noNotificationLayout.setVisibility(View.VISIBLE);
        } else {
            noNotificationLayout.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showSnackBar(@NonNull String msg) {
        Snackbar snackbar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_SHORT);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void showDialogAuthError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Some error occured. You need to login again !");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.logout(false);
            }
        });
        builder.setCancelable(false);
    }

    @Override
    public void showProgressbar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onRowItemClick(int position) {
        Log.d(TAG, "onRowItemClick: position : " + position);

        int itemId = presenter.getItemId(position);
        Intent detailsIntent = new Intent(HomeActivity.this, DetailsActivity.class);
        detailsIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        detailsIntent.putExtra("id", itemId);
        startActivity(detailsIntent);
    }

    @Override
    public void onRowItemLongClick(int position) {
        //TODO implement On Long Click
    }
}
