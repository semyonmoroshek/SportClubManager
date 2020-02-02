package com.myprojects.androidlessons.sportclubmanager.entity;

import java.time.LocalDate;

public class ClubMember {

    private long memberId;
    private String memberName;
    private String memberSurname;
    private String memberPersonalCode;
    private String memberPhoneNumber;
    private String memberDateBirth;
    private String memberPayment;
    private LocalDate memberDatePayment;

    public ClubMember(String memberName, String memberSurname, String memberPhoneNumber, String memberDataBirth) {
        this.memberName = memberName;
        this.memberSurname = memberSurname;
        this.memberPhoneNumber = memberPhoneNumber;
        this.memberDateBirth = memberDataBirth;
    }

    public ClubMember(String memberName, String memberSurname) {
        this.memberName = memberName;
        this.memberSurname = memberSurname;
    }

    public ClubMember() {
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
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

    public String getMemberDateBirth() {
        return memberDateBirth;
    }

    public void setMemberDateBirth(String memberDateBirth) {
        this.memberDateBirth = memberDateBirth;
    }

    public String getMemberPayment() {
        return memberPayment;
    }

    public void setMemberPayment(String memberPayment) {
        this.memberPayment = memberPayment;
    }

    public LocalDate getMemberDatePayment() {
        return memberDatePayment;
    }

    public void setMemberDatePayment(LocalDate memberDatePayment) {
        this.memberDatePayment = memberDatePayment;
    }

    @Override
    public String toString() {
        return memberName + " " + memberSurname + " " + "\n" +
                memberPayment + "\n" + memberPhoneNumber + "\n" +
                memberDateBirth + "\n" + memberId;
    }
}
