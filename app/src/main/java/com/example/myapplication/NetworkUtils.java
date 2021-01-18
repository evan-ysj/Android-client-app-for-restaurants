package com.example.myapplication;

import android.util.Log;

import com.example.myapplication.databuff.DataBuffer;
import com.example.myapplication.databuff.Repository;
import com.example.myapplication.databuff.ReservationBuffer;
import com.example.myapplication.databuff.UserBuffer;
import com.example.myapplication.databuff.WaitlistBuffer;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {
    public static final String SERVER_URL = "http://192.168.2.94:8000";
    public enum RESPONSE_CODE {
        PROCESSING,
        NO_RESPONSE,
        FAIL,
        SUCCESS
    }
    public static String MESSAGE = "Network Error!";
    public static int CODE = 0;
    private static final List<Cookie> cookieStore = new ArrayList();

    public static RESPONSE_CODE getResponse(Request request, Class bufferClass, Repository repository) {
        OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(@NotNull HttpUrl url, @NotNull List<Cookie> cookies) {
                cookieStore.addAll(cookies);
                Log.e("cookie: ", cookies.toString());
            }

            @Override
            public List<Cookie> loadForRequest(@NotNull HttpUrl url) {
                List<Cookie> invalid = new ArrayList();
                List<Cookie> valid = new ArrayList();
                for(Cookie cookie: cookieStore) {
                    if(cookie.expiresAt() < System.currentTimeMillis()) {
                        invalid.add(cookie);
                    } else if(cookie.matches(url)) {
                        valid.add(cookie);
                    }
                }
                cookieStore.removeAll(invalid);
                Log.e("cookie: ", cookieStore.toString());
                return cookieStore;
            }
        }).build();
        final RESPONSE_CODE[] responseCode = {RESPONSE_CODE.PROCESSING};
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("status: ", e.toString());
                responseCode[0] = RESPONSE_CODE.NO_RESPONSE;
                Log.e("status: ", responseCode[0].toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                Log.e("status: ", data);
                Gson gson = new Gson();
                DataBuffer dataBuffer = null;
                if(bufferClass == UserBuffer.class) {
                    dataBuffer = gson.fromJson(data, UserBuffer.class);
                    repository.updateUser(dataBuffer);
                } else if(bufferClass == WaitlistBuffer.class) {
                    dataBuffer = gson.fromJson(data, WaitlistBuffer.class);
                    repository.updateWaitlist(dataBuffer);
                } else if(bufferClass == ReservationBuffer.class) {
                    dataBuffer = gson.fromJson(data, ReservationBuffer.class);
                    repository.updateReservation(dataBuffer);
                }
                else {
                    dataBuffer = gson.fromJson(data, DataBuffer.class);
                }
                if(dataBuffer != null) dataBuffer.updateNetworkState();
                if(CODE == 200) {
                    Log.e("status: ", MESSAGE);
                    responseCode[0] = RESPONSE_CODE.SUCCESS;
                }
                else {
                    Log.e("status: ", MESSAGE);
                    responseCode[0] = RESPONSE_CODE.FAIL;
                }
            }
        });
        // Set timeout to 1 second.
        long start = new Date().getTime();
        while(responseCode[0] == RESPONSE_CODE.PROCESSING) {
            long end = new Date().getTime();
            if(end - start > 1000) break;
            Log.e("status", "waiting for process");
        }
        return responseCode[0];
    }
}
