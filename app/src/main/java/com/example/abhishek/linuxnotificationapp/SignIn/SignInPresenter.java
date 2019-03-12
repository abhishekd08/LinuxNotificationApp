package com.example.abhishek.linuxnotificationapp.SignIn;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.util.Log;

import com.example.abhishek.linuxnotificationapp.utils.Callbacks.ServerResponseCallback.ResponseReceiveListener;
import com.example.abhishek.linuxnotificationapp.utils.Callbacks.ServerResponseCallback.ServerResponse;
import com.example.abhishek.linuxnotificationapp.utils.Firebase.FirebaseUtils;
import com.example.abhishek.linuxnotificationapp.utils.NetworkUtils.MyWebService;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInPresenter implements SignInContract.ChangeListener, ResponseReceiveListener {

    public static final String TAG = "SignInPresenter";

    private SignInContract.View signInView;
    private MyWebService webService;
    private ServerResponse serverResponse;
    private Context context;

    public SignInPresenter(SignInContract.View signInView, Context context) {
        this.context = context;
        this.signInView = signInView;
        webService = MyWebService.getInstance(context);
        serverResponse = MyWebService.getServerResponseInstance();
        serverResponse.setResponseReceiveListener(this);
        FirebaseUtils.fetchToken(context);
    }

    @Override
    public void destroyServerResponseListenerInstance() {
        serverResponse = null;
    }

    @Override
    public void doSignIn(Editable mail, Editable pass) {
        if (mail.toString().isEmpty() || pass.toString().isEmpty()) {
            signInView.showSnackbar("Enter E-mail and Password");
        } else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            String token = sharedPreferences.getString("token", null);
            if (token != null) {
                webService.signIn(mail.toString(), pass.toString(), token);
                signInView.showProgressBar(true);
            } else {
//                Log.d(TAG, "doSignIn: Token is Null !");
            }
        }
    }

    @Override
    public void processServerResponse(JSONObject responseObject) throws JSONException {
        String from = responseObject.getString("from");

        if (from.equals("signIn")) {
            String result = responseObject.getString("result");

            if (result.equals("success")) {

                setAppStatus(true);
                String[] s = signInView.getUserData();
                saveUserDetails(s[0],s[1]);
                signInView.showProgressBar(false);
                signInView.gotoHome();
            } else {

                String reason = responseObject.getString("reason");
                if (reason.equals("wrong password")) {
                    signInView.showSnackbar("Wrong Password !");
                    signInView.setEdittextEmpty(false, true);
                    signInView.showProgressBar(false);

                } else if (reason.equals("user not registered")) {
                    signInView.showSnackbar("Username is not registered !");
                    signInView.setEdittextEmpty(true, true);
                    signInView.showProgressBar(false);

                } else if (reason.equals("token update error")) {
                    signInView.showSnackbar("Some error occured. Try again !");
                    signInView.showProgressBar(false);

                } else {
                    signInView.showProgressBar(false);
                    signInView.showSnackbar("Error in login. Try again later !");
                }
            }
        }
    }

    @Override
    public void setAppStatus(boolean isSignedIn) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
        editor.putBoolean("isSignedIn", isSignedIn);
        editor.commit();
    }

    @Override
    public void saveUserDetails(@NonNull String mail, @NonNull String pass) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
        editor.putString("mail", mail);
        editor.putString("pass", pass);
        editor.commit();
    }

    @Override
    public void onResponseReceive(JSONObject responseObject) {
        try {
            processServerResponse(responseObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorReceived() {
        signInView.showProgressBar(false);
        signInView.showSnackbar("Some error occured !");
    }
}
