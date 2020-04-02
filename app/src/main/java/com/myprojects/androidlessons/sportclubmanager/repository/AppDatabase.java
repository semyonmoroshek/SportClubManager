package com.myprojects.androidlessons.sportclubmanager.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.myprojects.androidlessons.sportclubmanager.model.Member;

@Database(entities = {Member.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "members";

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .build();
        }
        return sInstance;
    }

    public abstract MemberDao getMemberDao();

}