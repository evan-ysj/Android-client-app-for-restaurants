package com.example.myapplication.service;

import android.util.Log;

import com.example.myapplication.NetworkUtils;
import com.example.myapplication.model.Waitlist;

public class WaitlistBuffer extends DataBuffer {
    private int waitId;
    private String waitCategory;
    private int waitRank;

    public int getWaitId() {
        return this.waitId;
    }

    public void setWaitId(int waitId) {
        this.waitId = waitId;
    }

    public String getWaitCategory() {
        return this.waitCategory;
    }

    public void setWaitCategory(String waitCategory) {
        this.waitCategory = waitCategory;
    }

    public int getWaitRank() {
        return this.waitRank;
    }

    public void setWaitRank(int waitRank) {
        this.waitRank = waitRank;
    }
}
