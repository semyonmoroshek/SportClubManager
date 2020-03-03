package com.myprojects.androidlessons.sportclubmanager.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.myprojects.androidlessons.sportclubmanager.model.Member;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface MemberDao {

    @Insert
    Completable addMember(Member member);

    @Insert Completable addMembers(Member[] members);

    @Query("SELECT * FROM members")
    Observable<List<Member>> getAllMembers();

    @Query("SELECT * FROM members WHERE memberId = :id")
    Observable<Member> getMemberById(int id);

    @Update Completable updateMember(Member member);

    @Delete Completable deleteMember(Member nenber);

}
