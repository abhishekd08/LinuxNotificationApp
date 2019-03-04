package com.example.abhishek.linuxnotificationapp.utils.Callbacks.ServerResponseCallback;

import org.json.JSONObject;

public interface ResponseReceiveListener {
    public void onResponseReceive(JSONObject responseObject);
    public void onErrorReceived();
}
