package com.myprojects.androidlessons.sportclubmanager.repository;

import android.content.Context;
import androidx.room.Room;

public class DatabaseClient {
    public Context mCtx;
    private static DatabaseClient mInstance;

    public Context getmCtx() {
        return mCtx;
    }

    private AppDatabase appDatabase;

    public DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "members").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}


