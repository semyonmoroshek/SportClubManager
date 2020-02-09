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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberInfoActivity extends AppCompatActivity {

    @BindView(R.id.txt_member_info_name) TextView txtName;
    @BindView(R.id.txt_member_info_surname) TextView txtSurname;
    @BindView(R.id.txt_member_info_phone_number) TextView txtPhoneNumber;
    @BindView(R.id.txt_member_info_date_birth) TextView txtDateBirth;
    @BindView(R.id.txt_member_info_payment_date) TextView txtPaymentDate;
    @BindView(R.id.btn_edit_member_info) Button btnEditMemberInfo;

    String id;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);
        ButterKnife.bind(this);

        String name = getIntent().getStringExtra("name");
        String surname = getIntent().getStringExtra("surname");
        String dataBirth = getIntent().getStringExtra("dataBirth");
        String phone = getIntent().getStringExtra("phoneNum");
        id = getIntent().getStringExtra("id");

        txtName.setText(name);
        txtSurname.setText(surname);
        txtDateBirth.setText(dataBirth);
        txtPhoneNumber.setText(phone);

        btnEditMemberInfo.setOnClickListener(v -> openMemberInfo());
    }

    void openMemberInfo(){
        Intent intent = new Intent(MemberInfoActivity.this, EditMemberActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}










