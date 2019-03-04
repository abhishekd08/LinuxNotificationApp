package com.example.abhishek.linuxnotificationapp.utils.NetworkUtils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abhishek.linuxnotificationapp.utils.Callbacks.ServerResponseCallback.ServerResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class MyWebService {

    public static final String TAG = "MyWebService";
    public static MyWebService instance;
    public static ServerResponse serverResponse;

    private RequestQueue requestQueue;
    private String baseURL = "https://ipsemet.serveo.net";

    public static MyWebService getInstance(Context context) {
        if (instance == null) {
            instance = new MyWebService(context);
        }
        return instance;
    }

    private MyWebService(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        serverResponse = new ServerResponse();
    }

    public static ServerResponse getServerResponseInstance() {
        return serverResponse;
    }

//    public void emitResponse() {
//        JSONObject object = new JSONObject();
//        try {
//            object.put("object", "this is json");
//            serverResponse.responseReceived(object);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    public void getAllNotifications(@NonNull String mail, @NonNull String pass) {
        try {

            JSONObject headers = new JSONObject();
            headers.put("Content-Type", "application/json");

            JSONObject body = new JSONObject();
            body.put("mail", mail);
            body.put("pass", pass);

            String method = "POST";
            String path = "/getAllNotifications";

            sendRequest(method, path, headers, body);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteNotification(@NonNull String title, @NonNull String body, @NonNull String datetime) {
        try {

            JSONObject headers = new JSONObject();
            headers.put("Content-Type", "application/json");

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("title", title);
            jsonBody.put("body", body);
            jsonBody.put("datetime", datetime);

            String method = "POST";
            String path = "/deleteNotification";

            sendRequest(method, path, headers, jsonBody);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signUp(@NonNull String mail, @NonNull String pass, @NonNull String token) {
        try {

            JSONObject headers = new JSONObject();
            headers.put("Content-Type", "application/json");

            JSONObject body = new JSONObject();
            body.put("mail", mail);
            body.put("pass", pass);
            body.put("token", token);

            String method = "POST";
            String path = "/registerUser";

            sendRequest(method, path, headers, body);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateToken(@NonNull String mail, @NonNull String pass, @NonNull String token) {
        try {
            JSONObject headers = new JSONObject();
            headers.put("Content-Type", "application/json");

            JSONObject body = new JSONObject();
            body.put("mail", mail);
            body.put("pass", pass);
            body.put("token", token);

            String method = "POST";
            String path = "/updateToken";

            sendRequest(method, path, headers, body);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signIn(@NonNull String mail, @NonNull String pass, @NonNull String token) {
        try {
            JSONObject headers = new JSONObject();
            headers.put("Content-Type", "application/json");

            JSONObject body = new JSONObject();
            body.put("mail", mail);
            body.put("pass", pass);
            body.put("token", token);

            String method = "POST";
            String path = "/signIn";

            sendRequest(method, path, headers, body);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(final String methodString, String path, JSONObject headers, final JSONObject bodyJson) {
        String url = baseURL + path;
        int method = 1;
        if (methodString.equals("POST")) {
            method = Request.Method.POST;
        } else if (methodString.equals("GET")) {
            method = Request.Method.GET;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, headers
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response.toString());
                serverResponse.responseReceived(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error);
                serverResponse.errorReceived();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return bodyJson == null ? null : bodyJson.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
}
