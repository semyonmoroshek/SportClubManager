package com.myprojects.androidlessons.sportclubmanager.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import androidx.room.Room;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.entity.Member;
import com.myprojects.androidlessons.sportclubmanager.service.ViewAllMembersActivity;

import java.util.List;

public class DatabaseClient {

    public Context mCtx;
    private static DatabaseClient mInstance;

    public Context getmCtx() {
        return mCtx;
    }

    //our app database object
    private AppDatabase appDatabase;

    public DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
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


