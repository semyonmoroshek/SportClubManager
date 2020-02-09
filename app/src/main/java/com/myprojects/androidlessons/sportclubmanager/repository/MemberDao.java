package com.myprojects.androidlessons.sportclubmanager.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.myprojects.androidlessons.sportclubmanager.entity.Member;
import java.util.List;

@Dao
public interface MemberDao {

    @Query("SELECT * FROM members ")
    List<Member> getAll();

    @Insert
    void insert(Member member);

    @Delete
    void delete(Member member);

    @Update
    void update(Member member);

    @Query("SELECT * FROM members WHERE memberId= :id ")
    Member findUserById(int id);
    
}
