package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.adapter.MemberAdapter;
import com.myprojects.androidlessons.sportclubmanager.model.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ViewAllMemberActivity extends AppCompatActivity {

    @BindView(R.id.rv_all_members)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_find_member_for_local_base)
    Button btnFindMember;
    @BindView(R.id.et_find_member_for_local_base)
    EditText editFindMember;
    @BindView(R.id.btn_sort)
    Button btnSort;
    @BindView(R.id.tb_viewAllAct)
    Toolbar mToolbar;

    MemberAdapter mAdapter;
    List<Member> memberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_members);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24px);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        viewAll();


        btnFindMember.setOnClickListener(View -> findMember());

        btnSort.setOnClickListener(View -> {
            try {
                viewDebtors();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    private void viewDebtors() throws ParseException {
        memberList = mAdapter.getMemberList();
        List<Member> sortedMembers = new ArrayList<>();
        Date todayDate = Calendar.getInstance().getTime();

        for(int i = 0; i < memberList.size(); i++){

            Member member = memberList.get(i);

            String dateStr = member.getMemberPaymentDate();

            SimpleDateFormat mFormat = new SimpleDateFormat("dd/MM/yyyy");

            Date paymentDate = mFormat.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(paymentDate);
            calendar.add(Calendar.MONTH,1);
            Date validPayment = calendar.getTime();


            if(validPayment.before(todayDate)){
                sortedMembers.add(member);
            }

        }
        Log.i("memberList after", memberList.toString());
        Log.i("sortedMembers after", sortedMembers.toString());

        mAdapter = new MemberAdapter(this, sortedMembers);
        mAdapter.setOnItemClickListener(this::openMemberInfoActivity);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void findMember() {
        memberList = mAdapter.getMemberList();
        List<Member> findMembers = new ArrayList<>();
        String name = editFindMember.getText().toString().trim();

        for (Member member : memberList) {
            if (member.getMemberName().equals(name)) {
                findMembers.add(member);
                mAdapter = new MemberAdapter(this, findMembers);
                mAdapter.setOnItemClickListener(this::openMemberInfoActivity);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                Toast.makeText(this, "The member not exist", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void viewAll() {
        AppDatabase
                .getInstance(this)
                .getMemberDao()
                .getAllMembers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbMembers -> {

                    mAdapter = new MemberAdapter(this, dbMembers);
                    mAdapter.setOnItemClickListener(this::openMemberInfoActivity);
                    mRecyclerView.setAdapter(mAdapter);

                });
    }

    void openMemberInfoActivity(Member member) {
        Intent intent = new Intent(this, MemberInfoActivity.class);
        intent.putExtra(MemberInfoActivity.EXTRA_MEMBER, Parcels.wrap(member));
        startActivity(intent);
    }
}