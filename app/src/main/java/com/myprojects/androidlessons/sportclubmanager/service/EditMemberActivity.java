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
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EditMemberActivity extends AppCompatActivity {

    int memberId;
    List<Member> memberList;
    Member member;
    AppDatabase db;

    @BindView(R.id.et_edit_name) EditText editNewName;
    @BindView(R.id.et_edit_surname) EditText editNewSurname;
    @BindView(R.id.et_edit_phone_number) EditText editPhoneNumber;
    @BindView(R.id.picker_edit_birthday) DatePicker picker;
    @BindView(R.id.btn_save_edit_member) Button btnSaveEditedMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);
        ButterKnife.bind(this);

        String id = getIntent().getStringExtra("id");
        memberId = Integer.parseInt(id);

        btnSaveEditedMember.setOnClickListener(v -> editMember());
    }

    private void editMember() {

        loadAllMembersFromDatabase();

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
    }

    void loadAllMembersFromDatabase() {
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "members")
                .allowMainThreadQueries().build();
        memberList = db.getMemberDao().getAll();

        for (int i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).getMemberId() == memberId) {
                member = memberList.get(i);
            }
        }
    }
}




