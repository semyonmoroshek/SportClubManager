package com.myprojects.androidlessons.sportclubmanager.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.myprojects.androidlessons.sportclubmanager.entity.Member;

@Database(entities = {Member.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MemberDao getMemberDao();
}