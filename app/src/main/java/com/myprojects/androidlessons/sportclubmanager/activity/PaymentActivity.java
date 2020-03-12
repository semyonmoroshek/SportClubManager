package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class PaymentActivity extends AppCompatActivity {

    Member member;

    @BindView(R.id.et_price)
    EditText editPrice;
    @BindView(R.id.btn_save_payment)
    Button btnSavePayment;
    @BindView(R.id.picker_payment)
    DatePicker mPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        member = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MEMBER));

        btnSavePayment.setOnClickListener(View -> saveNewPayment());

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

        Intent intent = new Intent(this, ViewAllMemberActivity.class);
        startActivity(intent);
    }
}
