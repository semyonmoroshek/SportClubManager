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

public class MemberListActivity extends AppCompatActivity {

    @BindView(R.id.rv_all_members) RecyclerView mRecyclerView;
    @BindView(R.id.btn_find_member_for_local_base) Button btnFindMember;
    @BindView(R.id.et_find_member_for_local_base) EditText editFindMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_members);
        ButterKnife.bind(this);

        viewAll();

        btnFindMember.setOnClickListener(v -> findMember());
    }

    private void viewAll() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

      AppDatabase
                .getInstance(this)
                .getMemberDao()
                .getAllMembers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbMembers ->{
                    MemberAdapter adapter = new MemberAdapter(this, dbMembers);
                    mRecyclerView.setAdapter(adapter);

                });
    }

    private void findMember() {
//        name = editFindMember.getText().toString();
//        class GetMember extends AsyncTask<Void, Void, List<Member>> {
//            @Override
//            protected List<Member> doInBackground(Void... voids) {
//                loadAllMembersFromDatabase();
//                List<Member> foundMembers = new ArrayList<>();
//                for (Member member : memberList) {
//                    if (member.getMemberName().contains(name)) {
//                        foundMembers.add(member);
//                    }
//                }
//                return foundMembers;
//            }
//
//            @Override
//            protected void onPostExecute(List<Member> members) {
//                super.onPostExecute(members);
//                ArrayAdapter<Member> adapter = new ArrayAdapter<>(MemberListActivity.this,
//                        R.layout.item_list, R.id.txt_item_simple_list, members);
//                memberListView.setAdapter(adapter);
//            }
//        }
//        GetMember gt = new GetMember();
//        gt.execute();
    }

    private void viewAllMembers() {
//        class GetMember extends AsyncTask<Void, Void, List<Member>> {
//            @Override
//            protected List<Member> doInBackground(Void... voids) {
//                loadAllMembersFromDatabase();
//                sortListByPaymentDate(memberList);
//                return memberList;
//            }
//
//            @Override
//            protected void onPostExecute(List<Member> members) {
//                super.onPostExecute(members);
//                ArrayAdapter<Member> adapter = new ArrayAdapter<>(MemberListActivity.this,
//                        R.layout.item_list, R.id.txt_item_simple_list, members);
//                memberListView.setAdapter(adapter);
//            }
//        }
//        GetMember gt = new GetMember();
//        gt.execute();
//    }
//
//    public List<Member> sortListByPaymentDate(List<Member> memberList) {
//        for (int out = memberList.size() - 1; out >= 1; out--) {
//            for (int in = 0; in < out; in++) {
//                if (memberList.get(in).getMemberName().length() > memberList.get(in + 1).getMemberName().length()) {
//                    Collections.swap(memberList, in, in + 1);
//                }
//            }
//        }
//        return memberList;
    }

//    List<Member> loadAllMembersFromDatabase() {
//        db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "members").build();
//        db.getMemberDao().getAllMembers();
//        return memberList;
//    }
}