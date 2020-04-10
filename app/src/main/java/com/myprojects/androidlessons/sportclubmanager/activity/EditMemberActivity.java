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
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.model.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.myprojects.androidlessons.sportclubmanager.activity.DetailMemberActivity.EXTRA_MEMBER;

public class EditMemberActivity extends AppCompatActivity {

    Member member;

    @BindView(R.id.et_edit_name) EditText editName;
    @BindView(R.id.et_edit_surname) EditText editSurname;
    @BindView(R.id.et_edit_phone_number) EditText editNumber;
    @BindView(R.id.picker_edit_birthday) DatePicker picker;
    @BindView(R.id.tb_edit) Toolbar mToolbar;
    @BindView(R.id.fab_edit_member) FloatingActionButton fabEditMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        member = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MEMBER));

        setEditViewFields();

        fabEditMember.setOnClickListener(View -> saveMember());
    }

    private void setEditViewFields() {
        String name = member.getMemberName();
        String surname = member.getMemberSurname();
        String phoneNumber = member.getMemberPhoneNumber();
        String dateBirth = member.getMemberDateBirth();

        String day = dateBirth.substring(0, 2);
        Log.i("day", day);


        editName.setText(name);
        editSurname.setText(surname);
        editNumber.setText(phoneNumber);

        picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        });
    }

    private void saveMember() {
        final String name = editName.getText().toString().trim();
        final String surname = editSurname.getText().toString().trim();
        final String phoneNumber = editNumber.getText().toString().trim();
        final String dateOfBirth = picker.getDayOfMonth() + "/" + picker.getMonth() + "/" + picker.getYear();

        if (!TextUtils.isEmpty(name)) {
            member.setMemberName(name);
        }
        Log.i("name", member.getMemberName());

        if (!TextUtils.isEmpty(surname)) {
            member.setMemberSurname(surname);
        }
        Log.i("name", member.getMemberSurname());

        if (!TextUtils.isEmpty(phoneNumber)) {
            member.setMemberPhoneNumber(phoneNumber);
        }
        Log.i("name", member.getMemberPhoneNumber());

        member.setMemberDateBirth(dateOfBirth);

        AppDatabase
                .getInstance(this)
                .getMemberDao()
                .updateMember(member)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        Intent mIntent = new Intent(this, DetailMemberActivity.class);
        mIntent.putExtra(DetailMemberActivity.EXTRA_MEMBER, Parcels.wrap(member));
        startActivity(mIntent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent mIntent = new Intent(getApplicationContext(), DetailMemberActivity.class);
        mIntent.putExtra(DetailMemberActivity.EXTRA_MEMBER, Parcels.wrap(member));
        startActivityForResult(mIntent, 0);
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




