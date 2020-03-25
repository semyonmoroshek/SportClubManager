package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.adapter.MemberAdapter;
import com.myprojects.androidlessons.sportclubmanager.model.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ViewAllMemberActivity extends AppCompatActivity {

    @BindView(R.id.rv_all_members) RecyclerView mRecyclerView;
    @BindView(R.id.btn_find_member_for_local_base) Button btnFindMember;
    @BindView(R.id.et_find_member_for_local_base) EditText editFindMember;
    @BindView(R.id.btn_sort) Button btnSort;
    @BindView(R.id.tb_viewAllAct) Toolbar mToolbar;
    @BindView(R.id.fab_save_new_member) FloatingActionButton fabSaveNewMember;
    @BindView(R.id.btn_sort_abc) Button btnSortAbc;

    MemberAdapter mAdapter;
    List<Member> memberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_members);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        viewAll();

        fabSaveNewMember.setOnClickListener(View -> addNewMember());

        btnFindMember.setOnClickListener(View -> findMember());

        btnSort.setOnClickListener(View -> {
            try {
                sortByValidPayment();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }


    private void addNewMember() {
        Intent intent = new Intent(this, AddMemberActivity.class);
        startActivity(intent);
    }

    void sortByValidPayment() throws ParseException {
        Locale currentLocale = getResources().getConfiguration().locale;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", currentLocale);
        Calendar calendar = Calendar.getInstance();
        Date today = new Date();

        memberList = mAdapter.getMemberList();
        List<Member> sortedMembers = new ArrayList<>();

        for (int i = 0; i < memberList.size(); i++) {

            if (!memberList.get(i).getMemberPaymentDate().equals("")) {

                String memberPaymentDateString = memberList.get(i).getMemberPaymentDate();
                Date memberPaymentDate = dateFormat.parse(memberPaymentDateString);
                calendar.setTime(memberPaymentDate);
                calendar.add(Calendar.MONTH, 1);

                Date memberValidPayment = calendar.getTime();

                if (memberValidPayment.before(today)) {
                    sortedMembers.add(memberList.get(i));
                }
            }
        }
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

                    if (dbMembers.size() > 0) {
                        Collections.sort(dbMembers, (object1, object2) -> object1.getMemberName().compareTo(object2.getMemberName()));
                    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.menu_view_all:
//                Toast.makeText(this, "View all pressed", Toast.LENGTH_LONG).show();
//                Log.i("menu", ",menu");
//                return true;
//            case R.id.menu_budget:
//                Toast.makeText(this, "Menu budget pressed", Toast.LENGTH_LONG).show();
//                return true;
//            case R.id.menu_demo:
//                Toast.makeText(this, "Menu demo pressed", Toast.LENGTH_LONG).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

}