package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.adapter.MemberAdapter;
import com.myprojects.androidlessons.sportclubmanager.model.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase;

import org.parceler.Parcels;

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

    MemberAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_members);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        viewAll();

        btnFindMember.setOnClickListener(View -> findMember());
    }

    private void findMember() {
        List<Member> memberList = mAdapter.getMemberList();
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