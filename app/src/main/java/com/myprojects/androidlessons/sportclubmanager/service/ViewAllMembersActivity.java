package com.myprojects.androidlessons.sportclubmanager.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
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
import com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewAllMembersActivity extends AppCompatActivity {

    String name;
    List<Member> memberList;
    AppDatabase db;

    @BindView(R.id.lv_all_members) ListView memberListView;
    @BindView(R.id.btn_find_member_for_local_base) Button btnFindMember;
    @BindView(R.id.et_find_member_for_local_base) EditText editFindMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_members);
        ButterKnife.bind(this);

        name = editFindMember.getText().toString();
        memberList = new ArrayList<>();

        viewAllMembers();

        btnFindMember.setOnClickListener(v -> findMember());

        final AdapterView.OnItemClickListener memberInfo = (parent, v, position, id) -> {
            Intent intent = new Intent(ViewAllMembersActivity.this, MemberInfoActivity.class);
            intent.putExtra("name", memberList.get(position).getMemberName());
            intent.putExtra("surname", memberList.get(position).getMemberSurname());
            intent.putExtra("dataBirth", memberList.get(position).getMemberDateBirth());
            intent.putExtra("phoneNum", memberList.get(position).getMemberPhoneNumber());
            intent.putExtra("id", memberList.get(position).getMemberId());

            String memberId =  Integer.toString(memberList.get(position).getMemberId());
            intent.putExtra("id", memberId);

            startActivity(intent);
        };
        memberListView.setOnItemClickListener(memberInfo);
    }

    private void findMember() {
        name = editFindMember.getText().toString();
        class GetMember extends AsyncTask<Void, Void, List<Member>> {
            @Override
            protected List<Member> doInBackground(Void... voids) {
                loadAllMembersFromDatabase();
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
                loadAllMembersFromDatabase();
                sortListByPaymentDate(memberList);
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

    public List<Member> sortListByPaymentDate(List<Member> memberList) {
        for (int out = memberList.size() - 1; out >= 1; out--) {
            for (int in = 0; in < out; in++) {
                if (memberList.get(in).getMemberName().length() > memberList.get(in + 1).getMemberName().length()) {
                    Collections.swap(memberList, in, in + 1);
                }
            }
        }
        return memberList;
    }

    List<Member> loadAllMembersFromDatabase() {
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "members").build();
        memberList = db.getMemberDao().getAll();
        return memberList;
    }
}