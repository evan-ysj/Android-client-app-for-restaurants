package com.example.myapplication.ui.reservation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReservationViewModel extends ViewModel {

    private MutableLiveData<String> mTextReserveRecord;
    private MutableLiveData<String> mTextReserveTable;

    public ReservationViewModel() {
        mTextReserveRecord = new MutableLiveData<>();
        mTextReserveRecord.setValue("My Reservations");

        mTextReserveTable = new MutableLiveData<>();
        mTextReserveTable.setValue("Reserve a Table by Date");
    }

    public LiveData<String> getTextReserveRecord() {
        return mTextReserveRecord;
    }

    public LiveData<String> getTextReserveTable() {
        return mTextReserveTable;
    }
}