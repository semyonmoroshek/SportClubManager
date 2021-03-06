package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.model.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailMemberActivity extends AppCompatActivity {

    public static final String EXTRA_MEMBER = "EXTRA_EMPLOYEE";


    @BindView(R.id.txt_member_info_name) TextView txtName;
    @BindView(R.id.txt_member_info_surname) TextView txtSurname;
    @BindView(R.id.txt_member_info_phone_number) TextView txtPhoneNumber;
    @BindView(R.id.txt_member_info_date_birth) TextView txtDateBirth;
    @BindView(R.id.txt_member_info_payment_date) TextView txtPaymentDate;
    @BindView(R.id.btn_edit_member) Button btnEditMember;
    @BindView(R.id.btn_delete_member) Button btnDeleteMember;
    @BindView(R.id.btn_add_payment) Button btnAddPayment;
    @BindView(R.id.tb_member_info) Toolbar mToolbar;

    Member member;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        btnEditMember.setOnClickListener(View -> openEditMemberActivity());
        btnDeleteMember.setOnClickListener(View -> deleteMember());
        btnAddPayment.setOnClickListener(View -> addPayment());

        member = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MEMBER));

        txtName.setText(member.getMemberName());
        txtSurname.setText(member.getMemberSurname());
        txtPhoneNumber.setText(member.getMemberPhoneNumber());
        txtPaymentDate.setText("payment: " + member.getMemberPaymentDate());
        txtDateBirth.setText("birthday: " + member.getMemberDateBirth());
    }

    private void addPayment() {
        Intent intent = new Intent(DetailMemberActivity.this, AddPaymentActivity.class);
        intent.putExtra(DetailMemberActivity.EXTRA_MEMBER, Parcels.wrap(member));
        startActivity(intent);
    }

    private void deleteMember() {
        AppDatabase
                .getInstance(this)
                .getMemberDao()
                .deleteMember(member)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        openMembersList();
    }

    private void openMembersList() {
        Intent intent = new Intent(this, ViewAllMemberActivity.class);
        startActivity(intent);
    }

    void openEditMemberActivity(){
        Intent intent = new Intent(DetailMemberActivity.this, EditMemberActivity.class);
        intent.putExtra(DetailMemberActivity.EXTRA_MEMBER, Parcels.wrap(member));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), ViewAllMemberActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}










