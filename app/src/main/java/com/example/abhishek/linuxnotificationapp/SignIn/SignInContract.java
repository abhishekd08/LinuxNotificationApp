package com.example.abhishek.linuxnotificationapp.SignIn;

import android.support.annotation.NonNull;
import android.text.Editable;

import org.json.JSONException;
import org.json.JSONObject;

public interface SignInContract {

    interface View {
        public void showSnackbar(String msg);

        public void gotoHome();

        public void showProgressBar(boolean isVisible);

        public void setEdittextEmpty(boolean mailEdittext, boolean passEdittext);

        public void blockUI(boolean block);

        public String[] getUserData();
    }

    interface ChangeListener {
        public void doSignIn(Editable mail, Editable pass);

        public void processServerResponse(JSONObject responseObject) throws JSONException;

        public void setAppStatus(boolean isSignedIn);

        public void saveUserDetails(@NonNull String mail, @NonNull String pass);
    }

}
