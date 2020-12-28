package com.example.myapplication.service;

import com.example.myapplication.db.entity.ReserveHistoryEntity;

import java.util.List;

public class ReservationBuffer extends DataBuffer {
    private String reservationList;

    public String getReservationList() {
        return reservationList;
    }

    public void setReservationList(String reservationList) {
        this.reservationList = reservationList;
    }
}
