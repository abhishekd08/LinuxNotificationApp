package com.example.abhishek.linuxnotificationapp.SignUp;

import android.support.annotation.NonNull;
import android.text.Editable;

import org.json.JSONException;
import org.json.JSONObject;

public interface SignUpContract {

    interface View {
        public void showSnackbar(String msg);

        public void showProgressbar(boolean isVisible);

        public void gotoHome();

        public void setEdittextEmpty();

        public void blockUI(boolean block);

        public String[] getUserData();
    }

    interface ChangeListener {

        public void destroyServerResponseListenerInstance();

        public void doSignUp(Editable mail, Editable pass);

        public void processServerResponse(JSONObject responseObject) throws JSONException;

        public void checkAppState();

        public void setAppStatus(boolean isSignedIn);

        public void saveUserDetails(@NonNull String mail, @NonNull String pass);
    }
}
