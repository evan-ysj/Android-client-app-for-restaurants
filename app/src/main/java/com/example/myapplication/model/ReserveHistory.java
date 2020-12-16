package com.example.myapplication.model;

import java.util.Date;

public interface ReserveHistory {
    int getReserveId();
    int getNumberOfGuest();
    Date getDiningDate();
    Boolean getIsExpired();
    String getUsername();
}
