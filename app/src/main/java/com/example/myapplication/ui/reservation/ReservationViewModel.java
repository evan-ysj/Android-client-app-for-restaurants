package com.example.myapplication.ui.reservation;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.InitDB;
import com.example.myapplication.NetworkUtils;
import com.example.myapplication.db.entity.ReserveHistoryEntity;
import com.example.myapplication.model.User;
import com.example.myapplication.databuff.DataBuffer;
import com.example.myapplication.databuff.Repository;
import com.example.myapplication.databuff.ReservationBuffer;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;

public class ReservationViewModel extends ViewModel {

    private final MutableLiveData<String> mTextReserveRecord;
    private final MutableLiveData<String> mTextReserveTable;
    private final Repository mRepository;
    private LiveData<List<ReserveHistoryEntity>> mReservations;

    public ReservationViewModel(@NonNull Application application) {
        mTextReserveRecord = new MutableLiveData<>();
        mTextReserveRecord.setValue("My Reservations");

        mTextReserveTable = new MutableLiveData<>();
        mTextReserveTable.setValue("Reserve a Table by Date");

        mRepository = InitDB.getRepository(application);
        mReservations = mRepository.getReservations();
    }

    public LiveData<String> getTextReserveRecord() {
        return mTextReserveRecord;
    }

    public LiveData<String> getTextReserveTable() {
        return mTextReserveTable;
    }

    public LiveData<List<ReserveHistoryEntity>> getReservations() {
        return mReservations;
    }

    public LiveData<User> getUser() { return mRepository.getUser(); }

    public NetworkUtils.RESPONSE_CODE loadData() {
        FormBody body = new FormBody.Builder()
                .add("username", mRepository.getUser().getValue().getUsername())
                .build();
        final Request request = new Request.Builder()
                .url(NetworkUtils.SERVER_URL + "/mobile_checkrev/")
                .post(body)
                .build();
        Log.e("status: ", "request sent");
        return NetworkUtils.getResponse(request, ReservationBuffer.class, mRepository);
    }

    public NetworkUtils.RESPONSE_CODE bookingServer(int numberOfGuests, Date diningDate) {
        String username = mRepository.getUser().getValue().getUsername();
        if(username == null || username.isEmpty())
            return NetworkUtils.RESPONSE_CODE.FAIL;
        @SuppressLint("SimpleDateFormat") FormBody body = new FormBody.Builder()
                .add("username", username)
                .add("no_of_guests", String.valueOf(numberOfGuests))
                .add("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(diningDate))
                .build();
        final Request request = new Request.Builder()
                .url(NetworkUtils.SERVER_URL + "/mobile_booktable/")
                .post(body)
                .build();
        Log.e("status: ", "request sent");
        return NetworkUtils.getResponse(request, DataBuffer.class, mRepository);
    }

    public void invalidateCache() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                mRepository.clearReservation();
            }
        });
        t.start();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Application mApplication;

        public Factory(Application application) {
            mApplication = application;
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            return (T) new ReservationViewModel(mApplication);
        }
    }
}