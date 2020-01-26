package com.myprojects.androidlessons.sportclubmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myprojects.androidlessons.sportclubmanager.entity.ClubMember;

import java.util.ArrayList;


public class MembersDatabaseActivity extends AppCompatActivity {

    Button btnAddMember, btnViewAllMembers, btnFindMember, btnEditMember, btnDeleteMember;
    EditText editUserName, editUserSurname, editUserPhoneNumber;
    DatePicker picker;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ClubMember clubMember;
    ArrayList<ClubMember> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_database);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnAddMember = findViewById(R.id.btn_add_member);
        btnViewAllMembers = findViewById(R.id.btnViewAllMembers);
        btnFindMember = findViewById(R.id.btn_find_member);
        btnEditMember = findViewById(R.id.btn_edit_member);
        btnDeleteMember = findViewById(R.id.btn_delete_member);
        editUserName = findViewById(R.id.et_name);
        editUserSurname = findViewById(R.id.et_surname);
        editUserPhoneNumber = findViewById(R.id.et_phone_number);
        picker = findViewById(R.id.picker_bithday);
        database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        memberList = new ArrayList<>();

        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMember();
            }
        });

        btnViewAllMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllMembersList();
            }
        });

        btnFindMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findMember();
            }
        });

        btnEditMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMember();
            }
        });

        btnDeleteMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMember();
            }
        });

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
        ClubMember member = new ClubMember(name, surname, phoneNumber, dateOfBirth);
        myRef.push().setValue(member);
        Toast.makeText(this, "Member added successfully", Toast.LENGTH_LONG).show();
    }

    public void viewAllMembersList() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    clubMember = ds.getValue(ClubMember.class);
                    memberList.add(clubMember);
                }
                for (ClubMember member : memberList) {
                    Log.i("Info", member.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void findMember() {
        Intent intent = new Intent(MembersDatabaseActivity.this, MemberActivity.class);
        startActivity(intent);
    }

    public void editMember() {
        Toast.makeText(this, "Button pressed", Toast.LENGTH_LONG).show();
    }

    public void deleteMember() {
        Toast.makeText(this, "Button pressed", Toast.LENGTH_LONG).show();
    }
}
