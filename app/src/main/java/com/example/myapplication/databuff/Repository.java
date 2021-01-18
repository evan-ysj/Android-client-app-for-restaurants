package com.example.myapplication.databuff;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.db.AppDataBase;
import com.example.myapplication.db.entity.ReserveHistoryEntity;
import com.example.myapplication.model.User;
import com.example.myapplication.model.Waitlist;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Repository {

    private MutableLiveData<User> mUser;
    private MutableLiveData<Waitlist> mWaitlist;
    private final AppDataBase mDatabase;
    private MediatorLiveData<List<ReserveHistoryEntity>> mObservableReservations;
    private static Repository instance;

    private Repository(AppDataBase dataBase) {
        mUser = new MutableLiveData<>(User.getInstance());
        mWaitlist = new MutableLiveData<>(Waitlist.getInstance());
        mDatabase = dataBase;
        mObservableReservations = new MediatorLiveData<>();
        mObservableReservations.addSource(mDatabase.reserveHistoryDao().loadReserveHistory(),
                reservations -> {
                    if(mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableReservations.postValue(reservations);
                    }
                });
    }

    public static Repository getInstance(AppDataBase dataBase) {
        if(instance == null) {
            synchronized (Repository.class) {
                if(instance == null) {
                    if(dataBase == null) return null;
                    instance = new Repository(dataBase);
                }
            }
        }
        return instance;
    }

    public void updateUser(DataBuffer userBuffer) {
        UserBuffer buffer = (UserBuffer) userBuffer;
        User.getInstance().update(buffer.getUserid(),
                                  buffer.getUsername(),
                                  buffer.getFirstname(),
                                  buffer.getLastname(),
                                  buffer.getEmail());
        mUser.postValue(User.getInstance());
    }

    public void updateWaitlist(DataBuffer waitlistBuffer) {
        WaitlistBuffer buffer = (WaitlistBuffer) waitlistBuffer;
        Waitlist.getInstance().update(buffer.getWaitId(),
                                      buffer.getWaitCategory(),
                                      buffer.getWaitRank());
        mWaitlist.postValue(Waitlist.getInstance());
    }

    public void updateReservation(DataBuffer reservationBuffer) {
        ReservationBuffer buffer = (ReservationBuffer) reservationBuffer;
        Type collectionType = new TypeToken<List<ReserveHistoryEntity>>(){}.getType();
        Gson gson = new Gson();
        try {
            List<ReserveHistoryEntity> reservations = gson.fromJson(buffer.getReservationList(), collectionType);
            mDatabase.reserveHistoryDao().insertAll(reservations);
        } catch (Exception exception) {
            Log.e("repo-exception: ", exception.toString());
        }
    }

    public MutableLiveData<User> getUser() {
        return mUser;
    }

    public MutableLiveData<Waitlist> getWaitlist() {
        return mWaitlist;
    }

    public LiveData<List<ReserveHistoryEntity>> getReservations() {
        return mObservableReservations;
    }

    public void clearUser() {
        mUser.getValue().clear();
    }

    public void clearReservation() {
        mDatabase.reserveHistoryDao().clearAll();
    }
}
