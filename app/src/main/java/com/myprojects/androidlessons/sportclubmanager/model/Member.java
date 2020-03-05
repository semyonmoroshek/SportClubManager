package com.myprojects.androidlessons.sportclubmanager.model;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "members")
public class Member implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "memberId")
    private int memberId;

    @ColumnInfo(name = "memberName")
    private String memberName;

    @ColumnInfo(name = "memberSurname")
    private String memberSurname;

    @ColumnInfo(name = "memberPhoneNumber")
    private String memberPhoneNumber;

    @ColumnInfo(name = "memberDateBirth")
    private String memberDateBirth;

    @ColumnInfo(name = "memberPaymentDate")
    private String memberPaymentDate;

    public Member(String memberName, String memberSurname, String memberPhoneNumber, String memberDataBirth) {
        this.memberName = memberName;
        this.memberSurname = memberSurname;
        this.memberPhoneNumber = memberPhoneNumber;
        this.memberDateBirth = memberDataBirth;
    }

    public Member() {
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberSurname() {
        return memberSurname;
    }

    public void setMemberSurname(String memberSurname) {
        this.memberSurname = memberSurname;
    }

    public String getMemberPhoneNumber() {
        return memberPhoneNumber;
    }

    public void setMemberPhoneNumber(String memberPhoneNumber) {
        this.memberPhoneNumber = memberPhoneNumber;
    }

    public String getMemberDateBirth() {
        return memberDateBirth;
    }

    public void setMemberDateBirth(String memberDateBirth) {
        this.memberDateBirth = memberDateBirth;
    }

    public String getMemberPaymentDate() {
        return memberPaymentDate;
    }

    public void setMemberPaymentDate(String memberPaymentDate) {
        this.memberPaymentDate = memberPaymentDate;
    }

    @NonNull
    @Override
    public String toString() {
        return memberName + " " + memberSurname + " " + "\n" +
                memberPaymentDate + "\n" + memberPhoneNumber + "\n" +
                memberDateBirth + "\n" + memberId;
    }
}