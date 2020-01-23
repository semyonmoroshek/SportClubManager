package com.myprojects.androidlessons.sportclubmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MembersDatabaseActivity extends AppCompatActivity {

    Button btnAddMember, btnViewAllMembers, btnFindMember, btnEditMember, btnDeleteMember;
    EditText editUserName, editUserSurname, editUserDateBirth, editUserPhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_database);

        btnAddMember = findViewById(R.id.btnAddMember);
        btnViewAllMembers = findViewById(R.id.btnViewAllMembers);
        btnFindMember = findViewById(R.id.btnFindMember);
        btnEditMember = findViewById(R.id.btnEditMember);
        btnDeleteMember = findViewById(R.id.btnDeleteMember);

        editUserName = findViewById(R.id.edTxtName);
        editUserSurname = findViewById(R.id.edTxtSurname);
        editUserDateBirth = findViewById(R.id.edTxtDataBirth);
        editUserPhoneNumber = findViewById(R.id.edTxtPhoneNumber);
    }
}
