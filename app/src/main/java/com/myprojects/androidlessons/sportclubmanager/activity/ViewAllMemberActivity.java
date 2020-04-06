package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.adapter.CustomAdapter;
import com.myprojects.androidlessons.sportclubmanager.model.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    @BindView(R.id.btn_view_all) Button btnViewAll;

    CustomAdapter mAdapter;
    List<Member> memberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_members);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();

        viewAll();

        fabSaveNewMember.setOnClickListener(View -> addNewMember());
        btnViewAll.setOnClickListener(View -> viewAll());
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
            }if(memberList.get(i).getMemberPaymentDate().equals("")) {
                sortedMembers.add(memberList.get(i));
            }
        }
        mAdapter = new CustomAdapter(this, sortedMembers);
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
                mAdapter = new CustomAdapter(this, findMembers);
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


                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewAllMemberActivity.this);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mAdapter = new CustomAdapter(this, dbMembers);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(this::openMemberInfoActivity);
                });
    }

    void openMemberInfoActivity(Member member) {
        Intent intent = new Intent(this, DetailMemberActivity.class);
        intent.putExtra(DetailMemberActivity.EXTRA_MEMBER, Parcels.wrap(member));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
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