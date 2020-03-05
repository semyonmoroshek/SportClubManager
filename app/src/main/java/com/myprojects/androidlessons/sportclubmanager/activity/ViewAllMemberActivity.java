package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.adapter.MemberAdapter;
import com.myprojects.androidlessons.sportclubmanager.model.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ViewAllMemberActivity extends AppCompatActivity {

    @BindView(R.id.rv_all_members) RecyclerView mRecyclerView;
    @BindView(R.id.btn_find_member_for_local_base) Button btnFindMember;
    @BindView(R.id.et_find_member_for_local_base) EditText editFindMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_members);
        ButterKnife.bind(this);

        viewAll();
    }

    private void viewAll() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        AppDatabase
                .getInstance(this)
                .getMemberDao()
                .getAllMembers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbMembers -> {
                    MemberAdapter adapter = new MemberAdapter(this, dbMembers);
                    adapter.setOnItemClickListener(this::openMemberInfoActivity);
                    mRecyclerView.setAdapter(adapter);

                });
    }

    void openMemberInfoActivity(Member member) {
        Intent intent = new Intent(this, MemberInfoActivity.class);
        startActivity(intent);

    }
}