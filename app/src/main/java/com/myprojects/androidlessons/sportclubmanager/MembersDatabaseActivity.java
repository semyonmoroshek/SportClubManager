package com.myprojects.androidlessons.sportclubmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    String birthdayDate;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ClubMember clubMember;
    ArrayList<ClubMember> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_database);

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

    }

    public void addNewMember(View view) {
        String name = editUserName.getText().toString();
        String surname = editUserSurname.getText().toString();
        String phoneNumber = editUserPhoneNumber.getText().toString();
        String dateOfBirth = birthdayDate;
        ClubMember member = new ClubMember(name, surname, phoneNumber, dateOfBirth);
        myRef.push().setValue(member);
    }


    public void viewAllMembersList(View view) {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    clubMember = ds.getValue(ClubMember.class);
                    memberList.add(clubMember);
                }

                for(ClubMember member: memberList){
                    Log.i("Info", member.toString());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void findMember(View view) {
        Intent intent = new Intent(MembersDatabaseActivity.this, MembersDatabaseActivity.class);
        startActivity(intent);
    }

    public void editMember(View view) {
        Toast.makeText(this, "Button pressed", Toast.LENGTH_LONG).show();
    }

    public void deleteMember(View view) {
        Toast.makeText(this, "Button pressed", Toast.LENGTH_LONG).show();
    }

    public void saveDate(View view) {
        birthdayDate = picker.getDayOfMonth() + "/" + picker.getMonth() + "/" + picker.getYear();

    }
}
