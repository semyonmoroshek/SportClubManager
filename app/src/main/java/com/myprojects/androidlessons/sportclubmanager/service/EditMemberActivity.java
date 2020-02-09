package com.myprojects.androidlessons.sportclubmanager.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.entity.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase;
import com.myprojects.androidlessons.sportclubmanager.repository.MemberDao;

import java.util.ArrayList;
import java.util.List;

public class EditMemberActivity extends AppCompatActivity {

    EditText editNewName, editNewSurname, editPhoneNumber;
    DatePicker picker;
    Button btnSaveEditedMember;
    int memberId;
    List<Member> memberList;
    Member member;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);

        editNewName = findViewById(R.id.et_edit_name);
        editNewSurname = findViewById(R.id.et_edit_surname);
        editPhoneNumber = findViewById(R.id.et_edit_phone_number);
        picker = findViewById(R.id.picker_edit_birthday);

        String id = getIntent().getStringExtra("id");
        memberId = Integer.parseInt(id);
        btnSaveEditedMember = findViewById(R.id.btn_save_edit_member);
        btnSaveEditedMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editMember();
                Log.i("ListUra", memberList.toString());
                Log.i("memberUra", member.toString());
            }
        });
    }

    private void editMember() {

        loadAllMembersFromDatabase();

        Log.i("MyMember", member.toString());

        Log.i("button", "worked");
        String newName = editNewName.getText().toString().trim();
        String newSurname = editNewSurname.getText().toString().trim();
        String newPhoneNumber = editPhoneNumber.getText().toString().trim();
        String newDateOfBirth = picker.getDayOfMonth() + "/" + picker.getMonth() + "/" + picker.getYear();

        if (TextUtils.isEmpty(newName)) {
            editNewName.setError("This field must not be empty");
        }
        if (TextUtils.isEmpty(newSurname)) {
            editNewSurname.setError("This field must not be empty");
        }
        if (TextUtils.isEmpty(newPhoneNumber)) {
            editPhoneNumber.setError("This field must not be empty");
        }

        member.setMemberName(newName);
        member.setMemberSurname(newSurname);
        member.setMemberPhoneNumber(newPhoneNumber);
        member.setMemberDateBirth(newDateOfBirth);
//        saveNewMember();


    }

//    void saveNewMember(){
//        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "members")
//                .allowMainThreadQueries().build();
//        db.getMemberDao().insert(member);
//    }

    void loadAllMembersFromDatabase() {
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "members")
                .allowMainThreadQueries().build();
        memberList = db.getMemberDao().getAll();

        for (int i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).getMemberId() == memberId) {
                member = memberList.get(i);

                Log.i("List", memberList.toString());
                Log.i("member", member.toString());
            }
        }
    }
}




