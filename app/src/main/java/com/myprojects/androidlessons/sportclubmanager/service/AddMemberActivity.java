package com.myprojects.androidlessons.sportclubmanager.service;

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

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.entity.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.DatabaseClient;

public class AddMemberActivity extends AppCompatActivity {

    EditText editName, editSurname, editPhoneNumber;
    DatePicker picker;
    Button btnSaveMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        editName = findViewById(R.id.et_add_member_name);
        editSurname = findViewById(R.id.et_add_member_surname);
        editPhoneNumber = findViewById(R.id.et_add_member_phone_number);
        picker = findViewById(R.id.picker_add_member_bithday);
        btnSaveMember = findViewById(R.id.btn_save_add_member);
        btnSaveMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMember();
            }
        });
    }

    public void saveMember() {
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
        class SaveMember extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Member member = new Member();
                member.setMemberName(name);
                member.setMemberSurname(surname);
                member.setMemberPhoneNumber(phoneNumber);
                member.setMemberDateBirth(dateOfBirth);
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .getMemberDao()
                        .insert(member);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), ViewAllMembersActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }
        SaveMember st = new SaveMember();
        st.execute();
    }
}