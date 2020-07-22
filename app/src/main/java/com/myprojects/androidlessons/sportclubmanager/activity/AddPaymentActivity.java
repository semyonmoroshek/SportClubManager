package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;

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

public class AddPaymentActivity extends AppCompatActivity {

    Member member;

    @BindView(R.id.picker_payment) DatePicker mPicker;
    @BindView(R.id.tb_payment) Toolbar mToolbar;
    @BindView(R.id.fab_save_payment) FloatingActionButton fabAddPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_payment);
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

        member = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MEMBER));

        fabAddPayment.setOnClickListener(View -> saveNewPayment());

    }

    private void saveNewPayment() {
        int day = mPicker.getDayOfMonth();
        int month = mPicker.getMonth() + 1;
        int year = mPicker.getYear();

        String dayStr = String.valueOf(day);
        String monthStr = String.valueOf(month);
        String yearStr = String.valueOf(year);

        if (day < 10) {
            dayStr  = "0" + day;
        }
        if (month < 10) {
            monthStr =  "0" + month;
        }

        String datePayment = dayStr + "/" + monthStr + "/" + yearStr;

        member.setMemberPaymentDate(datePayment);

        AppDatabase
                .getInstance(this)
                .getMemberDao()
                .updateMember(member)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        sendNotificationSms();

//        Intent mIntent = new Intent(this, DetailMemberActivity.class);
//        mIntent.putExtra(DetailMemberActivity.EXTRA_MEMBER, Parcels.wrap(member));
//        startActivity(mIntent);

    }

    private void sendNotificationSms() {
        String phoneNumber = member.getMemberPhoneNumber();
        String message = "Paldies, esam saņēmuši Jūsu maksājumu." + "\n" + "Foršu dienu!";

        Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("sms:" + phoneNumber)
        );
        intent.putExtra("sms_body", message);
        startActivity(intent);
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