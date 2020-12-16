package com.example.myapplication.service;

import android.util.Log;

import com.example.myapplication.NetworkUtils;
import com.example.myapplication.model.Waitlist;

public class WaitlistBuffer extends DataBuffer {
    private int waitId;
    private String waitCategory;
    private int waitRank;

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String code) {
        this.message = code;
    }

    @Override
    public int get_$StatusCode185() {
        return this._$StatusCode185;
    }

    public void set_$StatusCode185(int _$StatusCode185) {
        this._$StatusCode185 = _$StatusCode185;
    }

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

    @Override
    public void update(Repository repository) {
        repository.updateWaitlist(waitId, waitCategory, waitRank);
        NetworkUtils.MESSAGE = getMessage();
        NetworkUtils.CODE = get_$StatusCode185();
    }
}
