package com.example.abhishek.linuxnotificationapp.SignIn;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abhishek.linuxnotificationapp.Home.HomeActivity;
import com.example.abhishek.linuxnotificationapp.R;

public class SignInActivity extends AppCompatActivity implements SignInContract.View {

    public static final String TAG = "SignInActivity";

    private ConstraintLayout parentLayout;
    private EditText mailEdittext, passEdittext;
    private ProgressBar progressBar;

    private SignInPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        overridePendingTransition(R.anim.enter_sign_in, R.anim.exit_sign_in);

//        presenter = new SignInPresenter(this, this);

        parentLayout = findViewById(R.id.signinactivity_parent_layout);
        mailEdittext = findViewById(R.id.signinactivity_mail_edittext);
        passEdittext = findViewById(R.id.signinactivity_password_edittext);
        progressBar = findViewById(R.id.signinactivity_progress_bar);

        passEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                presenter.doSignIn(mailEdittext.getText(), passEdittext.getText());
                hideKeyboard();

                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        presenter = new SignInPresenter(this, this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: Called");
//        presenter.destroyServerResponseListenerInstance();
        presenter = null;
        showProgressBar(false);
        super.onStop();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showSnackbar(String msg) {
        final Snackbar snackbar = Snackbar.make(parentLayout, msg, BaseTransientBottomBar.LENGTH_LONG);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        }).setActionTextColor(ContextCompat.getColor(SignInActivity.this, R.color.colorWhite));
        snackbar.show();
    }

    @Override
    public void gotoHome() {
        Intent homeIntent = new Intent(SignInActivity.this, HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
    }

    @Override
    public void showProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setEdittextEmpty(boolean mailEdittext, boolean passEdittext) {
        if (mailEdittext) {
            this.mailEdittext.setText("");
        }
        if (passEdittext) {
            this.passEdittext.setText("");
        }
    }

    @Override
    public void blockUI(boolean block) {
        mailEdittext.setFocusable(false);
        passEdittext.setFocusable(false);
    }

    @Override
    public String[] getUserData() {
        String mail = mailEdittext.getText().toString();
        String pass = passEdittext.getText().toString();
        String[] s = {mail, pass};
        return s;
    }

    public void startSignUpScreen(View view) {
        finish();
        //TODO finit activity
        //TODO launch sign up activity
        //TODO do transition animation --> swipe left
    }

    public void login(View view) {
        presenter.doSignIn(mailEdittext.getText(), passEdittext.getText());
    }

    @Override
    public void onBackPressed() {
        startSignUpScreen(null);
    }
}
