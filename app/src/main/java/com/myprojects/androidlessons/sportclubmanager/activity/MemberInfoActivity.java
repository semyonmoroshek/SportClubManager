package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.model.Member;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberInfoActivity extends AppCompatActivity {

    public static final String EXTRA_MEMBER = "EXTRA_EMPLOYEE";


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

//        String name = getIntent().getStringExtra("name");
//        String surname = getIntent().getStringExtra("surname");
//        String dataBirth = getIntent().getStringExtra("dataBirth");
//        String phone = getIntent().getStringExtra("phoneNum");
//        id = getIntent().getStringExtra("id");
//
//        txtName.setText(name);
//        txtSurname.setText(surname);
//        txtDateBirth.setText(dataBirth);
//        txtPhoneNumber.setText(phone);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Member member = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MEMBER));

        txtName.setText(member.getMemberName());
        txtSurname.setText(member.getMemberSurname());
        txtPhoneNumber.setText(member.getMemberPhoneNumber());
        txtDateBirth.setText("birthday: " + member.getMemberDateBirth());



    }

    //    void openMemberInfo(){
//        Intent intent = new Intent(MemberInfoActivity.this, EditMemberActivity.class);
//        intent.putExtra("id", id);
//        startActivity(intent);
//    }

}










