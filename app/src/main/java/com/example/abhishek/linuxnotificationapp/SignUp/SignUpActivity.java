package com.example.abhishek.linuxnotificationapp.SignUp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abhishek.linuxnotificationapp.Home.HomeActivity;
import com.example.abhishek.linuxnotificationapp.R;
import com.example.abhishek.linuxnotificationapp.SignIn.SignInActivity;
import com.example.abhishek.linuxnotificationapp.utils.Firebase.FirebaseUtils;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {

    private SignUpPresenter presenter;

    private ConstraintLayout parentLayout;
    private EditText mailEdittext, passEdittext;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new SignUpPresenter(this, this);
        presenter.checkAppState();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        parentLayout = findViewById(R.id.signupactivity_parent_layout);
        mailEdittext = findViewById(R.id.signupactivity_mail_edittext);
        passEdittext = findViewById(R.id.signupactivity_password_edittext);
        progressBar = findViewById(R.id.signupactivity_progress_bar);

        passEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                hideKeyboard();
                presenter.doSignUp(mailEdittext.getText(), passEdittext.getText());

                return true;
            }
        });

        FirebaseUtils.fetchToken(this);
    }

    public void signUp(View view) {
        presenter.doSignUp(mailEdittext.getText(), passEdittext.getText());
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void startLoginScreen(View view) {
        //TODO show activity transition animation --> swipe right
        Intent loginIntent = new Intent(SignUpActivity.this, SignInActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(loginIntent);
    }

    @Override
    public void showSnackbar(String msg) {
        final Snackbar snackbar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
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
    public void gotoHome() {
        Intent homeIntent = new Intent(SignUpActivity.this, HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
    }

    @Override
    public void setEdittextEmpty() {
        mailEdittext.setText("");
        passEdittext.setText("");
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
}
