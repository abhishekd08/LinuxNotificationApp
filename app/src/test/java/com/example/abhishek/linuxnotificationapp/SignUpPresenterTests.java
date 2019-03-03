package com.example.abhishek.linuxnotificationapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;

import com.example.abhishek.linuxnotificationapp.SignUp.SignUpContract;
import com.example.abhishek.linuxnotificationapp.SignUp.SignUpPresenter;

import org.junit.Before;
import org.junit.Test;

public class SignUpPresenterTests {

    private SignUpContract.View view;
    private SignUpPresenter presenter;

    @Before
    public void doBeforeStuff(){
        presenter = new SignUpPresenter(view, InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void checkAppStateTest(){
        presenter.checkAppState();

        if (PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getTargetContext().getApplicationContext()).getBoolean("isSignedIn",false)){

        }else {

        }
    }
}
