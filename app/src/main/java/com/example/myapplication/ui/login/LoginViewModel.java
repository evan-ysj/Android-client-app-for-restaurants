package com.example.myapplication.ui.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.NetworkUtils;
import com.example.myapplication.model.User;
import com.example.myapplication.service.Repository;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<String> mTextLogin;
    private final MutableLiveData<String> mTextRegister;

    public LoginViewModel() {
        mTextLogin = new MutableLiveData<>();
        mTextLogin.setValue("Please Sign in with Username or Email");
        mTextRegister = new MutableLiveData<>();
        mTextRegister.setValue("Sign up with Following Information");
    }

    public LiveData<String> getTextLogin() {
        return mTextLogin;
    }

    public LiveData<String> getTextRegister() {
        return mTextRegister;
    }

    public NetworkUtils.RESPONSE_CODE loginServer(String username, String password) {
        FormBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        final Request request = new Request.Builder()
                .url(NetworkUtils.SERVER_URL + "/mobile_login/")
                .post(body)
                .build();
        Log.e("status: ", "request sent");
        return NetworkUtils.getResponse(request, User.class);
    }

    public NetworkUtils.RESPONSE_CODE registerServer(List<String> params) {
        FormBody body = new FormBody.Builder()
                .add("username", params.get(0))
                .add("firstname", params.get(1))
                .add("lastname", params.get(2))
                .add("password1", params.get(3))
                .add("password2", params.get(4))
                .add("email", params.get(5))
                .build();
        final Request request = new Request.Builder()
                .url(NetworkUtils.SERVER_URL + "/mobile_register/")
                .post(body)
                .build();
        Log.e("status: ", "request sent");
        return NetworkUtils.getResponse(request, User.class);
    }
}