package com.myprojects.androidlessons.sportclubmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.myprojects.androidlessons.sportclubmanager.entity.Member;
import com.myprojects.androidlessons.sportclubmanager.service.EditMemberActivity;

import java.util.Objects;

public class MemberInfoActivity extends AppCompatActivity {

    TextView txtName, txtSurname, txtDateBirth, txtPhoneNumber, txtPaymentDate;
    Button btnEditMemberInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        txtName = findViewById(R.id.txt_member_info_name);
        txtSurname = findViewById(R.id.txt_member_info_surname);
        txtPhoneNumber = findViewById(R.id.txt_member_info_phone_number);
        txtDateBirth = findViewById(R.id.txt_member_info_date_birth);
        txtPaymentDate = findViewById(R.id.txt_member_info_payment_date);

        String name = getIntent().getStringExtra("name");
        String surname = getIntent().getStringExtra("surname");
        String dataBirth = getIntent().getStringExtra("dataBirth");
        String phone = getIntent().getStringExtra("phoneNum");
        final String id = getIntent().getStringExtra("id");
        txtName.setText(name);
        txtSurname.setText(surname);
        txtDateBirth.setText(dataBirth);
        txtPhoneNumber.setText(phone);

        int memberId = Integer.parseInt(id);

        btnEditMemberInfo = findViewById(R.id.btn_edit_member_info);
        btnEditMemberInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberInfoActivity.this, EditMemberActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

}










