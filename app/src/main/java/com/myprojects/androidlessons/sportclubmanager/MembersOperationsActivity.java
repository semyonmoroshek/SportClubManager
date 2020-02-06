package com.myprojects.androidlessons.sportclubmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myprojects.androidlessons.sportclubmanager.entity.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.DatabaseClient;
import com.myprojects.androidlessons.sportclubmanager.service.ViewAllMembersActivity;

import java.util.ArrayList;


public class MembersOperationsActivity extends AppCompatActivity {

    Button btnSaveMember, btnViewAllMembers, btnFindMember, btnEditMember, btnDeleteMember;
    EditText editUserName, editUserSurname, editUserPhoneNumber;
    DatePicker picker;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<Member> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_operations);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnSaveMember = findViewById(R.id.btn_add_member);
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

        btnSaveMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMemberToRoom();
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

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .getMemberDao()
                        .insert(member);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MembersListActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
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

        myRef.push().setValue(member);
        Toast.makeText(this, "Member added successfully", Toast.LENGTH_LONG).show();
    }

    public void viewAllMembersList() {
        Intent intent = new Intent(MembersOperationsActivity.this, ViewAllMembersActivity.class);
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
