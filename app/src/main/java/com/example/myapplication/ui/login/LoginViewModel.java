package com.example.myapplication.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> mTextLogin;
    private MutableLiveData<String> mTextRegister;

    public LoginViewModel() {
        mTextLogin = new MutableLiveData<>();
        mTextLogin.setValue("Please Login with Email Address");

        mTextRegister = new MutableLiveData<>();
        mTextRegister.setValue("Sign in with Following Information");
    }

    public LiveData<String> getTextLogin() {
        return mTextLogin;
    }

    public LiveData<String> getTextRegister() {
        return mTextRegister;
    }
}