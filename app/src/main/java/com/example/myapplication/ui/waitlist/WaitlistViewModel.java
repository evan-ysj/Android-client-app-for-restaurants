package com.example.myapplication.ui.waitlist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.InitDB;
import com.example.myapplication.NetworkUtils;
import com.example.myapplication.service.Repository;
import com.example.myapplication.service.WaitlistBuffer;

import org.jetbrains.annotations.NotNull;

import okhttp3.FormBody;
import okhttp3.Request;

public class WaitlistViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mTextCheckState;
    private final Repository mRepository;

    public WaitlistViewModel(@NonNull Application application) {
        mText = new MutableLiveData<>();
        mText.setValue("Take a Number to join in the waitlist");
        mRepository = InitDB.getRepository(application);

        mTextCheckState = new MutableLiveData<>();
        String number = mRepository.getWaitlist().getValue().getCategory() +
                mRepository.getWaitlist().getValue().getId();
        String result = "Your number is:\n\n" +
                 number + "\n\n" +
                "There are " + mRepository.getWaitlist().getValue().getRank() +
                " more guests in front of you";
        mTextCheckState.setValue(result);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getTextCheckState() {
        return mTextCheckState;
    }

    public int getWaitId() { return mRepository.getWaitlist().getValue().getId(); }

    public String getWaitCategory() { return mRepository.getWaitlist().getValue().getCategory(); }

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
        return NetworkUtils.getResponse(request, WaitlistBuffer.class, mRepository);
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
        return NetworkUtils.getResponse(request, WaitlistBuffer.class, mRepository);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private Application mApplication;

        public Factory(Application application) {
            mApplication = application;
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new WaitlistViewModel(mApplication);
        }
    }
}