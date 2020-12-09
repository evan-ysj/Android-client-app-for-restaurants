package com.example.myapplication.ui.personal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.User;

public class PersonalViewModel extends ViewModel {

    private final MutableLiveData<String> mText = new MutableLiveData<>("Personal Profile");
    private final MutableLiveData<String> usernameKey = new MutableLiveData<>("Username");
    private final MutableLiveData<String> firstnameKey = new MutableLiveData<>("Firstname");
    private final MutableLiveData<String> lastnameKey = new MutableLiveData<>("Lastname");
    private final MutableLiveData<String> emailKey = new MutableLiveData<>("Email");
    private MutableLiveData<String> usernameValue;
    private MutableLiveData<String> firstnameValue;
    private MutableLiveData<String> lastnameValue;
    private MutableLiveData<String> emailValue;

    public PersonalViewModel() {
        usernameValue = new MutableLiveData<>();
        firstnameValue = new MutableLiveData<>();
        lastnameValue = new MutableLiveData<>();
        emailValue = new MutableLiveData<>();
        setValues();
    }

    public void setValues() {
        usernameValue.setValue(User.getInstance().getUsername());
        firstnameValue.setValue(User.getInstance().getFirstname());
        lastnameValue.setValue(User.getInstance().getLastname());
        emailValue.setValue(User.getInstance().getEmail());
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getUsernameKey() {
        return usernameKey;
    }

    public LiveData<String> getUsernameValue() {
        return usernameValue;
    }

    public LiveData<String> getFirstnameKey() {
        return firstnameKey;
    }

    public LiveData<String> getFirstnameValue() {
        return firstnameValue;
    }

    public LiveData<String> getLastnameKey() {
        return lastnameKey;
    }

    public LiveData<String> getLastnameValue() {
        return lastnameValue;
    }

    public LiveData<String> getEmailKey() {
        return emailKey;
    }

    public LiveData<String> getEmailValue() {
        return emailValue;
    }
}