package com.myprojects.androidlessons.sportclubmanager.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.myprojects.androidlessons.sportclubmanager.MemberInfoActivity;
import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.entity.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.DatabaseClient;

import java.util.ArrayList;
import java.util.List;

public class ViewAllMembersActivity extends AppCompatActivity {

    ListView memberListView;
    Button btnFindMember;
    EditText editFindMember;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_members);

        memberListView = findViewById(R.id.lv_all_members);
        btnFindMember = findViewById(R.id.btn_find_member_for_local_base);
        editFindMember = findViewById(R.id.et_find_member_for_local_base);
        name = editFindMember.getText().toString();


        viewAllMembers();

        btnFindMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findMember();
            }
        });

        AdapterView.OnItemClickListener memberInfo = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {


                Intent intent = new Intent(ViewAllMembersActivity.this, MemberInfoActivity.class);
                startActivity(intent);
            }
        };
        memberListView.setOnItemClickListener(memberInfo);
    }

    private void findMember() {
        name = editFindMember.getText().toString();

        class GetMember extends AsyncTask<Void, Void, List<Member>> {
            @Override
            protected List<Member> doInBackground(Void... voids) {
                List<Member> memberList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .memberDao()
                        .getAll();

                List<Member> foundMembers = new ArrayList<>();

                for (Member member : memberList) {
                    if (member.getMemberName().contains(name)) {
                        foundMembers.add(member);
                    }
                }


                return foundMembers;
            }


            @Override
            protected void onPostExecute(List<Member> members) {
                super.onPostExecute(members);
                ArrayAdapter<Member> adapter = new ArrayAdapter<>(ViewAllMembersActivity.this,
                        R.layout.item_list, R.id.txt_item_simple_list, members);

                memberListView.setAdapter(adapter);
            }
        }
        GetMember gt = new GetMember();
        gt.execute();

    }

    private void viewAllMembers() {
        class GetMember extends AsyncTask<Void, Void, List<Member>> {
            @Override
            protected List<Member> doInBackground(Void... voids) {
                List<Member> memberList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .memberDao()
                        .getAll();
                return memberList;
            }

            @Override
            protected void onPostExecute(List<Member> members) {
                super.onPostExecute(members);
                ArrayAdapter<Member> adapter = new ArrayAdapter<>(ViewAllMembersActivity.this,
                        R.layout.item_list, R.id.txt_item_simple_list, members);
                memberListView.setAdapter(adapter);
            }
        }
        GetMember gt = new GetMember();
        gt.execute();
    }


}

