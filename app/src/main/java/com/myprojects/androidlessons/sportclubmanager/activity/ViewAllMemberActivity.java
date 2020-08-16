package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
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
    @BindView(R.id.tb_viewAllAct) Toolbar mToolbar;
    @BindView(R.id.fab_save_new_member) FloatingActionButton fabSaveNewMember;
    @BindView(R.id.txt_sort_debtors) TextView txtSortDebtors;
    @BindView(R.id.txt_sort_all_payments) TextView txtSortAllPayments;
    @BindView(R.id.iv_sort) ImageView imgSort;
    @BindView(R.id.txt_member_count) TextView txtMemberCount;

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

        txtMemberCount.setOnClickListener(View ->{
            openStatisticActivityMemberCount();
        });

        txtSortDebtors.setOnClickListener(View -> {
            try{
                txtSortDebtors.setVisibility(View.INVISIBLE);
                txtSortAllPayments.setVisibility(View.VISIBLE);
                sortByValidPayment();
            }catch (ParseException e){
                e.printStackTrace();
            }
        });

        txtSortAllPayments.setOnClickListener(View -> {
                txtSortAllPayments.setVisibility(View.INVISIBLE);
                txtSortDebtors.setVisibility(View.VISIBLE);
                viewAll();
        });

    }

    private void openStatisticActivityMemberCount() {
        List<Member> memberListMemberCount;
        memberListMemberCount = mAdapter.getMemberList();
        Intent intent = new Intent(this, StatisticActivity.class);
        intent.putExtra(StatisticActivity.EXTRA_MEMBER_LIST, Parcels.wrap(memberListMemberCount));
        startActivity(intent);
    }


    private void addNewMember() {
        Intent intent = new Intent(this, AddMemberActivity.class);
        startActivity(intent);
    }

    void sortByValidPayment() throws ParseException {

        Locale currentLocale;
        if (Build.VERSION.SDK_INT >= 26) {
            currentLocale = this.getResources().getConfiguration().getLocales().get(0);
        } else {
            currentLocale = this.getResources().getConfiguration().locale;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", currentLocale);
        Calendar calendar = Calendar.getInstance();
        Date today = new Date();

        memberList = mAdapter.getMemberList();

        Log.i("memberlist", memberList.toString());

        List<Member> sortedMembers = new ArrayList<>();

        for (int i = 0; i < memberList.size(); i++) {

            if (!memberList.get(i).getMemberPaymentDate().equals("") &&
                    !memberList.get(i).getMemberPaymentDate().equals("No payments")) {

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

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_statistic) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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