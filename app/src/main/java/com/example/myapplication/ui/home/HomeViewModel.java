package com.example.myapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Welcome\n \n WE COOKED YOUR DESIRED RECIPE\n \n We serve simple and delicious,\nhandcrafted food.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}