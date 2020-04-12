package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.model.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddMemberActivity extends AppCompatActivity {

    @BindView(R.id.et_add_member_name) EditText editName;
    @BindView(R.id.et_add_member_surname) EditText editSurname;
    @BindView(R.id.et_add_member_phone_number) EditText editPhoneNumber;
    @BindView(R.id.picker_add_member_bithday) DatePicker picker;
    @BindView(R.id.fab_save_new_member) FloatingActionButton fabSaveNewMember;
    @BindView(R.id.tb_member_add) Toolbar mToolbar;
    @BindView(R.id.cb_add_member_payment) CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        fabSaveNewMember.setOnClickListener(View -> addMember());
    }



    public void addMember() {
        Date today = new Date();
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(today);

        final String name = editName.getText().toString().trim();
        final String surname = editSurname.getText().toString().trim();
        final String phoneNumber = editPhoneNumber.getText().toString().trim();

        int day = picker.getDayOfMonth();
        Log.i("intday", String.valueOf(day));

        int month = picker.getMonth() + 1;
        Log.i("intmonth", String.valueOf(month));

        int year = picker.getYear();
        Log.i("intyear", String.valueOf(year));


        String dayStr = String.valueOf(day);
        Log.i("daystr", dayStr);

        String montStr = String.valueOf(day);
        Log.i("montstr", montStr);

        String yearStr = String.valueOf(year);
        Log.i("yearstr", yearStr);


        if(day < 10){
            dayStr = "0" + day;
        }
        if(month < 10){
            montStr = "0" + month;
        }

        final String dateOfBirth = dayStr + "/" + montStr + "/" + yearStr;
        String paymentDate = "No payments";
        Log.i("dayOf", dateOfBirth);

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

        if(mCheckBox.isChecked()){
            paymentDate = dateString;
        }

        Member member = new Member(name, surname, phoneNumber, dateOfBirth, paymentDate);

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
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), ViewAllMemberActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    public void rootLayoutTapped(View view) {


        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}