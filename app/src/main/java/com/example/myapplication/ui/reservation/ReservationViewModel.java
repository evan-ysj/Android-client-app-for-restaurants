package com.example.myapplication.ui.reservation;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.InitDB;
import com.example.myapplication.NetworkUtils;
import com.example.myapplication.db.entity.ReserveHistoryEntity;
import com.example.myapplication.model.User;
import com.example.myapplication.service.Repository;
import com.example.myapplication.service.ReservationBuffer;
import com.example.myapplication.service.UserBuffer;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;

public class ReservationViewModel extends ViewModel {

    private final MutableLiveData<String> mTextReserveRecord;
    private final MutableLiveData<String> mTextReserveTable;
    private final Repository mRepository;
    private final LiveData<List<ReserveHistoryEntity>> mReservations;

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