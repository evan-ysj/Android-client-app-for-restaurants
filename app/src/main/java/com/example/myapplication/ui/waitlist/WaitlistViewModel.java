package com.example.myapplication.ui.waitlist;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.NetworkUtils;
import com.example.myapplication.model.Waitlist;
import com.example.myapplication.service.Repository;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WaitlistViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mTextCheckState;

    public WaitlistViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Take a Number to join in the waitlist");

        mTextCheckState = new MutableLiveData<>();
        String number = Waitlist.getInstance().getCategory() + Waitlist.getInstance().getId();
        String result = "Your number is:\n\n" +
                 number + "\n\n" +
                "There are " + Waitlist.getInstance().getRank() +
                " more guests in front of you";
        mTextCheckState.setValue(result);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getTextCheckState() {
        return mTextCheckState;
    }

    public NetworkUtils.RESPONSE_CODE waitlistServer(String guestNumber, String lastname) {
        FormBody body = new FormBody.Builder()
                .add("no_of_guests", guestNumber)
                .add("name", lastname)
                .build();
        final Request request = new Request.Builder()
                .url(NetworkUtils.SERVER_URL + "/mobile_takeno/")
                .post(body)
                .build();
        Log.e("status: ", "request sent");
        return NetworkUtils.getResponse(request, Waitlist.class);
    }

    public NetworkUtils.RESPONSE_CODE waitStateServer(int waitId, String waitCategory) {
        FormBody body = new FormBody.Builder()
                .add("waitId", String.valueOf(waitId))
                .add("waitCategory", waitCategory)
                .build();
        final Request request = new Request.Builder()
                .url(NetworkUtils.SERVER_URL + "/mobile_waitstate/")
                .post(body)
                .build();
        Log.e("status: ", "request sent");
        return NetworkUtils.getResponse(request, Waitlist.class);
    }
}