package com.example.myapplication;

import android.app.Application;
import android.content.Context;

import com.example.myapplication.db.AppDataBase;
import com.example.myapplication.service.Repository;

public class InitDB {

    public static AppDataBase getDatabase(final Context context) {
        return AppDataBase.getInstance(context);
    }

    public static Repository getRepository(final Context context) {
        return Repository.getInstance(getDatabase(context));
    }
}
