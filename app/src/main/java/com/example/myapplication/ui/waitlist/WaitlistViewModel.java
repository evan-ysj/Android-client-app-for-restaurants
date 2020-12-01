package com.example.myapplication.ui.waitlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WaitlistViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mTextCheckState;

    public WaitlistViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Take a Number to join in the waitlist");

        mTextCheckState = new MutableLiveData<>();
        mTextCheckState.setValue("Your number is:\n\n" +
                "a17\n\n" +
                "There are 5 more guests in front of you");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getTextCheckState() {
        return mTextCheckState;
    }
}