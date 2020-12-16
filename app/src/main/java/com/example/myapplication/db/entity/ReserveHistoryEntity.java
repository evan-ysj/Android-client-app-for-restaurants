package com.example.myapplication.db.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.myapplication.model.ReserveHistory;

import java.util.Date;

@Entity(tableName = "reservation",
        indices = {@Index("reserveId")})
public class ReserveHistoryEntity implements ReserveHistory {
    @PrimaryKey
    private int reserveId;
    private int numberOfGuest;
    private Date diningDate;
    private Boolean isExpired;
    private String username;

    @Override
    public int getReserveId() {
        return reserveId;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }

    @Override
    public int getNumberOfGuest() {
        return numberOfGuest;
    }

    public void setNumberOfGuest(int numberOfGuest) {
        this.numberOfGuest = numberOfGuest;
    }

    @Override
    public Date getDiningDate() {
        return diningDate;
    }

    public void setDiningDate(Date diningDate) {
        this.diningDate = diningDate;
    }

    @Override
    public Boolean getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    @Override
    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }
}
