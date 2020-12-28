package com.example.myapplication.model;

import java.util.Date;

public interface ReserveHistory {
    int getRsv_number();
    int getNo_of_guests();
    Date getDate();
    Boolean getExpired();
}
