package com.example.myapplication.ui.personal;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.InitDB;
import com.example.myapplication.model.User;
import com.example.myapplication.service.Repository;

import org.jetbrains.annotations.NotNull;

public class PersonalViewModel extends ViewModel {

    private final MutableLiveData<String> mText = new MutableLiveData<>("Personal Profile");
    private final MutableLiveData<String> usernameKey = new MutableLiveData<>("Username");
    private final MutableLiveData<String> firstnameKey = new MutableLiveData<>("Firstname");
    private final MutableLiveData<String> lastnameKey = new MutableLiveData<>("Lastname");
    private final MutableLiveData<String> emailKey = new MutableLiveData<>("Email");
    private final Repository mRepository;

    public PersonalViewModel(@NonNull Application application) {
        mRepository = InitDB.getRepository(application);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getUsernameKey() {
        return usernameKey;
    }

    public LiveData<String> getFirstnameKey() {
        return firstnameKey;
    }

    public LiveData<String> getLastnameKey() {
        return lastnameKey;
    }

    public LiveData<String> getEmailKey() {
        return emailKey;
    }

    public LiveData<User> getUser() { return mRepository.getUser(); }

    public void clearData() {
        mRepository.clearUser();
        mRepository.clearReservation();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private Application mApplication;

        public Factory(Application application) {
            mApplication = application;
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new PersonalViewModel(mApplication);
        }
    }
}