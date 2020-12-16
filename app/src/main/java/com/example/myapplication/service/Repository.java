package com.example.myapplication.service;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.db.AppDataBase;
import com.example.myapplication.db.entity.ReserveHistoryEntity;
import com.example.myapplication.model.ReserveHistory;
import com.example.myapplication.model.User;
import com.example.myapplication.model.Waitlist;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Repository {

    private MutableLiveData<User> mUser;
    private MutableLiveData<Waitlist> mWaitlist;
    private final AppDataBase mDatabase;
    private MediatorLiveData<List<ReserveHistoryEntity>> mObervableReservations;
    private static Repository instance;

    private Repository(AppDataBase dataBase) {
        mUser = new MutableLiveData<>(User.getInstance());
        mWaitlist = new MutableLiveData<>(Waitlist.getInstance());
        mDatabase = dataBase;
        mObervableReservations = new MediatorLiveData<>();
        mObervableReservations.addSource(mDatabase.reserveHistoryDao().loadReserveHistory(getUser().getValue().getUsername()),
                reservations -> {
                    if(mDatabase.getDatabaseCreated().getValue() != null) {
                        mObervableReservations.postValue(reservations);
                    }
                });
    }

    public static Repository getInstance(AppDataBase dataBase) {
        if(instance == null) {
            synchronized (Repository.class) {
                if(instance == null) {
                    instance = new Repository(dataBase);
                }
            }
        }
        return instance;
    }

    public void updateUser(int userid, String username, String firstname, String lastname, String email) {
        User.getInstance().update(userid, username, firstname, lastname, email);
        mUser.postValue(User.getInstance());
    }

    public void updateWaitlist(int waitId, String waitCategory, int waitRank) {
        Waitlist.getInstance().update(waitId, waitCategory, waitRank);
        mWaitlist.postValue(Waitlist.getInstance());
    }

    public MutableLiveData<User> getUser() {
        mUser.postValue(User.getInstance());
        return mUser;
    }

    public MutableLiveData<Waitlist> getWaitlist() {
        mWaitlist.postValue(Waitlist.getInstance());
        return mWaitlist;
    }

    public LiveData<List<ReserveHistoryEntity>> getReservations() {
        return mObervableReservations;
    }

    public void clearUser() {
        mUser.getValue().clear();
    }
}
