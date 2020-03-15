package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.model.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.myprojects.androidlessons.sportclubmanager.activity.MemberInfoActivity.EXTRA_MEMBER;

public class EditMemberActivity extends AppCompatActivity {

    Member member;

    @BindView(R.id.et_edit_name) EditText editName;
    @BindView(R.id.et_edit_surname) EditText editSurname;
    @BindView(R.id.et_edit_phone_number) EditText editNumber;
    @BindView(R.id.picker_edit_birthday) DatePicker picker;
    @BindView(R.id.btn_save_edit_member) Button btnSaveEditedMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);
        ButterKnife.bind(this);

        member = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MEMBER));

        btnSaveEditedMember.setOnClickListener(View -> saveMember());
    }

    private void saveMember() {
        final String name = editName.getText().toString().trim();
        final String surname = editSurname.getText().toString().trim();
        final String phoneNumber = editNumber.getText().toString().trim();
        final String dateOfBirth = picker.getDayOfMonth() + "/" + picker.getMonth() + "/" + picker.getYear();
        if (!TextUtils.isEmpty(name)) {
            member.setMemberName(name);
        }
        if (!TextUtils.isEmpty(surname)) {
            member.setMemberName(name);
        }
        if (!TextUtils.isEmpty(phoneNumber)) {
            member.setMemberPhoneNumber(phoneNumber);
        }

        member.setMemberDateBirth(dateOfBirth);

        AppDatabase
                .getInstance(this)
                .getMemberDao()
                .updateMember(member)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        Intent mIntent = new Intent(this, ViewAllMemberActivity.class);
        startActivity(mIntent);
    }
}




