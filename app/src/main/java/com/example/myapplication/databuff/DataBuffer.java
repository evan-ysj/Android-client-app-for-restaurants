package com.example.myapplication.databuff;

import com.example.myapplication.NetworkUtils;
import com.google.gson.annotations.SerializedName;

public class DataBuffer {

    protected String message;
    @SerializedName("code")
    protected int _$StatusCode185;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String code) {
        this.message = code;
    }

    public int get_$StatusCode185() {
        return this._$StatusCode185;
    }

    public void set_$StatusCode185(int _$StatusCode185) {
        this._$StatusCode185 = _$StatusCode185;
    }

    public void updateNetworkState() {
        NetworkUtils.MESSAGE = getMessage();
        NetworkUtils.CODE = get_$StatusCode185();
    }
}
