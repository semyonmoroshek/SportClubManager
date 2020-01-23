package com.myprojects.androidlessons.sportclubmanager.entity;

import android.icu.util.LocaleData;

import java.util.UUID;

public class ClubMember {

    private String MemberId = UUID.randomUUID().toString();
    private String memberName;
    private String memberSurname;
    private String memberPersonalCode;
    private String memberPhoneNumber;
    private LocaleData memberDataBirth;
    private LocaleData memberPayment;

    public ClubMember(String uniqueID, String memberName, String memberSurname, String memberPhoneNumber, LocaleData memberDataBirth) {
        this.MemberId = uniqueID;
        this.memberName = memberName;
        this.memberSurname = memberSurname;
        this.memberPhoneNumber = memberPhoneNumber;
        this.memberDataBirth = memberDataBirth;
    }

    public ClubMember() {
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        this.MemberId = memberId;
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

    public String getMemberPersonalCode() {
        return memberPersonalCode;
    }

    public void setMemberPersonalCode(String memberPersonalCode) {
        this.memberPersonalCode = memberPersonalCode;
    }

    public String getMemberPhoneNumber() {
        return memberPhoneNumber;
    }

    public void setMemberPhoneNumber(String memberPhoneNumber) {
        this.memberPhoneNumber = memberPhoneNumber;
    }

    public LocaleData getMemberDataBirth() {
        return memberDataBirth;
    }

    public void setMemberDataBirth(LocaleData memberDataBirth) {
        this.memberDataBirth = memberDataBirth;
    }

    public LocaleData getMemberPayment() {
        return memberPayment;
    }

    public void setMemberPayment(LocaleData memberPayment) {
        this.memberPayment = memberPayment;
    }

    @Override
    public String toString() {
        return memberName + " " + memberSurname + " " + "\n" +
                memberPayment + "\n" + memberPhoneNumber + "\n" +
                memberDataBirth + "\n" + MemberId;
    }
}
