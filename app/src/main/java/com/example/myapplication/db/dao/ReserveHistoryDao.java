package com.example.myapplication.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.db.entity.ReserveHistoryEntity;

import java.util.List;

@Dao
public interface ReserveHistoryDao {
    @Query("select * from reservation where username = :username")
    LiveData<List<ReserveHistoryEntity>> loadReserveHistory(String username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ReserveHistoryEntity> reservations);
}
