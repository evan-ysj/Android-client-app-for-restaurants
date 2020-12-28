package com.example.myapplication.db.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.myapplication.model.ReserveHistory;

import java.util.Date;

@Entity(tableName = "reservation",
        indices = {@Index("rsv_number")})
public class ReserveHistoryEntity implements ReserveHistory {
    @PrimaryKey
    private int rsv_number;
    private int no_of_guests;
    private Date date;
    private Boolean expired;

    public int getRsv_number() {
        return rsv_number;
    }

    public void setRsv_number(int reserveId) {
        this.rsv_number = reserveId;
    }

    public int getNo_of_guests() {
        return no_of_guests;
    }

    public void setNo_of_guests(int numberOfGuest) {
        this.no_of_guests = numberOfGuest;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date diningDate) {
        this.date = diningDate;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean isExpired) {
        this.expired = isExpired;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(date.toString()).append("\n");
        sb.append(no_of_guests).append("\n");
        String expire = expired ? "Expired" : "Valid";
        sb.append(expire);
        return sb.toString();
    }
}
