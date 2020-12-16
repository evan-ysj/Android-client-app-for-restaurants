package com.example.myapplication;

import android.app.Application;
import android.util.Log;

import com.example.myapplication.service.DataBuffer;
import com.example.myapplication.service.Repository;
import com.example.myapplication.service.UserBuffer;
import com.example.myapplication.service.WaitlistBuffer;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.Call;
import okhttp3.Callback;
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
    public static String MESSAGE = "empty";
    public static int CODE = 0;
    public static final ReentrantLock lock = new ReentrantLock();

    public static RESPONSE_CODE getResponse(Request request, Class bufferClass, Repository repository) {
        OkHttpClient client = new OkHttpClient();
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
                DataBuffer buffer = new DataBuffer();
                if(bufferClass == UserBuffer.class) {
                    buffer = gson.fromJson(data, UserBuffer.class);
                } else if(bufferClass == WaitlistBuffer.class) {
                    buffer = gson.fromJson(data, WaitlistBuffer.class);
                }
                buffer.update(repository);
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
        // Wait for thread complete; Should add time out later.
        while(responseCode[0] == RESPONSE_CODE.PROCESSING) {
            Log.e("status", "waiting for process");
        }
        return responseCode[0];
    }
}
