package com.example.abhishek.linuxnotificationapp.SignUp;

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

public class SignUpPresenter implements SignUpContract.ChangeListener, ResponseReceiveListener {

    public static final String TAG = "SignUpPresenter";

    private SignUpContract.View signUpView;
    private Context context;
    private ServerResponse serverResponse;
    private MyWebService webService;

    public SignUpPresenter(SignUpContract.View signUpView, Context context) {
        this.signUpView = signUpView;
        webService = MyWebService.getInstance(context);
        serverResponse = MyWebService.getServerResponseInstance();
        serverResponse.setResponseReceiveListener(this);
        this.context = context;
    }

    @Override
    public void checkAppState() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        boolean isSignedIn = sharedPreferences.getBoolean("isSignedIn", false);
        if (isSignedIn) {
            signUpView.gotoHome();
        }
    }

    @Override
    public void saveUserDetails(@NonNull String mail, @NonNull String pass) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
        editor.putString("mail", mail);
        editor.putString("pass", pass);
        editor.commit();
    }

    @Override
    public void setAppStatus(boolean isSignedIn) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
        editor.putBoolean("isSignedIn", isSignedIn);

        editor.commit();
    }


    @Override
    public void processServerResponse(JSONObject responseObject) throws JSONException {
        Log.d(TAG, "SignUpPresenter: GOT RESPONSE IN SIGN UP PRESENTER");
        String from = responseObject.getString("from");
        if (from.equals("registerUser")) {

            String result = responseObject.getString("result");

            if (result.equals("success")) {

                setAppStatus(true);
                String[] s = signUpView.getUserData();
                saveUserDetails(s[0], s[1]);
                signUpView.showProgressbar(false);
                signUpView.gotoHome();

            } else if (result.equals("fail")) {

                String reason = responseObject.getString("reason");
                Log.e(TAG, "SignUpPresenter: Response Failed Reason : " + reason);

                if (reason.equals("user already registered")) {

                    signUpView.showSnackbar("Username Already Registered !");
                    signUpView.setEdittextEmpty();
                    signUpView.showProgressbar(false);

                } else {

                    signUpView.showSnackbar("Error in Sign Up. Try Again Later !");
                    signUpView.showProgressbar(false);

                }
            }
        }
    }


    @Override
    public void destroyServerResponseListenerInstance() {
        serverResponse = null;
    }

    @Override
    public void doSignUp(Editable mail, Editable pass) {
        if (mail.toString().isEmpty() || pass.toString().isEmpty()) {
            signUpView.showSnackbar("Enter Email and Password !");
        } else {
            FirebaseUtils.fetchToken(context);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            String token = sharedPreferences.getString("token", null);
            if (token != null) {
                webService.signUp(mail.toString(), pass.toString(), token);
                signUpView.showProgressbar(true);
            } else {
                Log.d(TAG, "doSignUp: ");
            }
        }
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
        Log.d(TAG, "onErrorReceived: Called");
        signUpView.showProgressbar(false);
        signUpView.showSnackbar("Some Error Occured !");
    }
}
