package com.myprojects.androidlessons.sportclubmanager.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.model.Member;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MembersOperationsActivity extends AppCompatActivity {

    ArrayList<Member> memberList;

    @BindView(R.id.btn_add_member) Button btnSaveMember;
    @BindView(R.id.btn_view_all_members) Button btnViewAllMembers;
    @BindView(R.id.btn_find_member) Button btnFindMember;
    @BindView(R.id.btn_edit_member) Button btnEditMember;
    @BindView(R.id.btn_delete_member) Button btnDeleteMember;
    @BindView(R.id.et_name) EditText editUserName;
    @BindView(R.id.et_surname) EditText editUserSurname;
    @BindView(R.id.et_phone_number) EditText editUserPhoneNumber;
    @BindView(R.id.picker_bithday) DatePicker picker;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_operations);
        ButterKnife.bind(this);
    }

    @Override protected void onStart() {
        super.onStart();

        memberList = new ArrayList<>();

        btnSaveMember.setOnClickListener((View v) -> addNewMemberToRoom());
        btnViewAllMembers.setOnClickListener(v -> viewAllMembersList());
        btnFindMember.setOnClickListener(v -> findMember());
        btnEditMember.setOnClickListener(v -> editMember());
        btnDeleteMember.setOnClickListener(v -> deleteMember());
    }

    public void addNewMemberToRoom(){
        String name = editUserName.getText().toString();
        String surname = editUserSurname.getText().toString();
        String phoneNumber = editUserPhoneNumber.getText().toString();
        String dateOfBirth = picker.getDayOfMonth() + "/" + picker.getMonth() + "/" + picker.getYear();
        if (TextUtils.isEmpty(name)) {
            editUserName.setError("This field must not be empty");
            return;
        }
        if (TextUtils.isEmpty(surname)) {
            editUserSurname.setError("This field must not be empty");
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            editUserPhoneNumber.setError("This field must not be empty");
            return;
        }
        final Member member = new Member(name, surname, phoneNumber, dateOfBirth);
    }



    public void addNewMember() {

        String name = editUserName.getText().toString();
        String surname = editUserSurname.getText().toString();
        String phoneNumber = editUserPhoneNumber.getText().toString();
        String dateOfBirth = picker.getDayOfMonth() + "/" + picker.getMonth() + "/" + picker.getYear();
        if (TextUtils.isEmpty(name)) {
            editUserName.setError("This field must not be empty");
            return;
        }
        if (TextUtils.isEmpty(surname)) {
            editUserSurname.setError("This field must not be empty");
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            editUserPhoneNumber.setError("This field must not be empty");
            return;
        }
        Member member = new Member(name, surname, phoneNumber, dateOfBirth);


        Toast.makeText(this, "Member added successfully", Toast.LENGTH_LONG).show();
    }

    public void viewAllMembersList() {
        Intent intent = new Intent(MembersOperationsActivity.this, ViewAllMemberActivity.class);
        startActivity(intent);
    }

    public void findMember() {
        Intent intent = new Intent(MembersOperationsActivity.this, MemberInfoActivity.class);
        startActivity(intent);
    }

    public void editMember() {
        Toast.makeText(this, "Button pressed", Toast.LENGTH_LONG).show();
    }

    public void deleteMember() {
        Toast.makeText(this, "Button pressed", Toast.LENGTH_LONG).show();
    }
}
