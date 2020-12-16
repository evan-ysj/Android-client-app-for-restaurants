package com.example.myapplication.db;

import android.content.Context;
import android.os.Build;

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

import java.util.List;

@Database(entities = {ReserveHistoryEntity.class}, version = 2)
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
        return Room.databaseBuilder(context, AppDataBase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        AppDataBase dataBase = AppDataBase.getInstance(context);
                        List<ReserveHistoryEntity> reservations = DataGenerator.generateReservations();
                        insertData(dataBase, reservations);
                        dataBase.setDatabaseCreated();
                    }
                })
                .addMigrations()
                .build();
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
