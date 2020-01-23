package com.myprojects.androidlessons.sportclubmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.LocaleData;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.snapshot.ChildKey;
import com.myprojects.androidlessons.sportclubmanager.entity.ClubMember;

import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MembersDatabaseActivity extends AppCompatActivity {

    Button btnAddMember, btnViewAllMembers, btnFindMember, btnEditMember, btnDeleteMember;
    EditText editUserName, editUserSurname, editUserDateBirth, editUserPhoneNumber;

    FirebaseDatabase database;
    DatabaseReference myRef;

    SimpleDateFormat dateFormat;


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

        database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    public void addNewMember(View view) {

        String name = editUserName.getText().toString();
        String surname = editUserSurname.getText().toString();
        String phoneNumber = editUserPhoneNumber.getText().toString();


        ClubMember member = new ClubMember(name, surname);
        myRef.push().setValue(member);
    }


    public void viewAllMembersList(View view) {
        Toast.makeText(this, "Button pressed", Toast.LENGTH_LONG).show();
    }

    public void findMember(View view) {
        Toast.makeText(this, "Button pressed", Toast.LENGTH_LONG).show();
    }


    public void editMember(View view) {
        Toast.makeText(this, "Button pressed", Toast.LENGTH_LONG).show();
    }


    public void deleteMember(View view) {
        Toast.makeText(this, "Button pressed", Toast.LENGTH_LONG).show();
    }
}
