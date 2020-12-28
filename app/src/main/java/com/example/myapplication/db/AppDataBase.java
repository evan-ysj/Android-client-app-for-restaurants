package com.example.myapplication.db;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.DataGenerator;
import com.example.myapplication.db.converter.DateConverter;
import com.example.myapplication.db.dao.ReserveHistoryDao;
import com.example.myapplication.db.entity.ReserveHistoryEntity;
import com.example.myapplication.model.User;

import java.util.List;

@Database(entities = {ReserveHistoryEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase instance;

    public static final String DATABASE_NAME = "app-db";

    public abstract ReserveHistoryDao reserveHistoryDao();

    private  final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDataBase getInstance(final Context context) {
        if(instance == null) {
            synchronized (AppDataBase.class) {
                if(instance == null) {
                    instance = buildDatabase(context.getApplicationContext());
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private static AppDataBase buildDatabase(final Context context) {
        instance = Room.databaseBuilder(context, AppDataBase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
        //generateData();
        return instance;
    }

    private static void generateData() {
        Thread t = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                List<ReserveHistoryEntity> reservations = DataGenerator.generateReservations();
                Log.e("adapter: ", "size of list " + reservations.size());
                insertData(instance, reservations);
                instance.setDatabaseCreated();
            }
        });
        t.start();
    }

    private void updateDatabaseCreated(final Context context) {
        if(context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    private static void insertData(final AppDataBase dataBase, final List<ReserveHistoryEntity> reservations) {
        dataBase.runInTransaction(() -> {
            dataBase.reserveHistoryDao().insertAll(reservations);
        });
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
