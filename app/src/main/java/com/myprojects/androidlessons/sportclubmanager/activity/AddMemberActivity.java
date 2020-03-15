package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.model.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddMemberActivity extends AppCompatActivity {

    @BindView(R.id.et_add_member_name) EditText editName;
    @BindView(R.id.et_add_member_surname) EditText editSurname;
    @BindView(R.id.et_add_member_phone_number) EditText editPhoneNumber;
    @BindView(R.id.picker_add_member_bithday) DatePicker picker;
    @BindView(R.id.fab_save_new_member)
    FloatingActionButton fabSaveNewMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        ButterKnife.bind(this);

        fabSaveNewMember.setOnClickListener(View -> addMember());
    }

    public void addMember() {
        final String name = editName.getText().toString().trim();
        final String surname = editSurname.getText().toString().trim();
        final String phoneNumber = editPhoneNumber.getText().toString().trim();
        final String dateOfBirth = picker.getDayOfMonth() + "/" + picker.getMonth() + "/" + picker.getYear();
        if (TextUtils.isEmpty(name)) {
            editName.setError("This field must not be empty");
            return;
        }
        if (TextUtils.isEmpty(surname)) {
            editSurname.setError("This field must not be empty");
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            editPhoneNumber.setError("This field must not be empty");
            return;
        }
        Member member = new Member(name, surname, phoneNumber, dateOfBirth, "");

        AppDatabase
                .getInstance(this)
                .getMemberDao()
                .addMember(member)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        Toast.makeText(this, "Member saved", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, ViewAllMemberActivity.class);
        startActivity(intent);
    }
}