package com.myprojects.androidlessons.sportclubmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.myprojects.androidlessons.sportclubmanager.entity.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.DatabaseClient;

import java.util.List;

public class MemberInfoActivity extends AppCompatActivity {

    TextView txtName, txtSurname, txtDateBirth, txtPhoneNumber, txtPaymentDate;
    ListView memberListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        txtName = findViewById(R.id.txt_member_info_name);
        txtSurname = findViewById(R.id.txt_member_info_surname);
        txtPhoneNumber = findViewById(R.id.txt_member_info_phone_number);
        txtDateBirth = findViewById(R.id.txt_member_info_date_birth);
        txtPaymentDate = findViewById(R.id.txt_member_info_payment_date);
        memberListView = findViewById(R.id.lv_info);

        String name = getIntent().getStringExtra("name");
        String surname = getIntent().getStringExtra("surname");
        String dataBirth = getIntent().getStringExtra("dataBirth");
        String phone = getIntent().getStringExtra("phoneNum");
        txtName.setText(name);
        txtSurname.setText(surname);
        txtDateBirth.setText(dataBirth);
        txtPhoneNumber.setText(phone);
    }
}










