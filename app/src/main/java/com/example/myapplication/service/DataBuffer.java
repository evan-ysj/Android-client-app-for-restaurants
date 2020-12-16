package com.example.myapplication.service;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.myapplication.NetworkUtils;
import com.google.gson.annotations.SerializedName;

public class DataBuffer {

    protected String message;
    @SerializedName("code")
    protected int _$StatusCode185;

    public String getMessage() {
        return this.message;
    }

    public int get_$StatusCode185() {
        return this._$StatusCode185;
    }

    public void update(Repository repository) {}
}
