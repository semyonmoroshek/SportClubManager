package com.myprojects.androidlessons.sportclubmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myprojects.androidlessons.sportclubmanager.entity.ClubMember;

public class MembersDatabaseActivity extends AppCompatActivity {

    Button btnAddMember, btnViewAllMembers, btnFindMember, btnEditMember, btnDeleteMember;
    EditText editUserName, editUserSurname, editUserPhoneNumber;
    DatePicker picker;
    String birthdayDate;
    FirebaseDatabase database;
    DatabaseReference myRef;

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

        picker = (DatePicker) findViewById(R.id.picker_bithday);

        database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();

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

    public void saveDate(View view) {
        birthdayDate = picker.getDayOfMonth() + "/" + picker.getMonth() + "/" + picker.getYear();

    }
}
