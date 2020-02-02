package com.myprojects.androidlessons.sportclubmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myprojects.androidlessons.sportclubmanager.entity.ClubMember;

import java.util.ArrayList;
import java.util.List;

public class MembersListActivity extends AppCompatActivity {

    DatabaseReference myRef;
    ClubMember clubMember;
    List<ClubMember> list;
    List<String> listName;
    ListView memberListView;
    Button btnFindMember;
    EditText editFindMember;
    TextView txtMemberInfoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_list);

        memberListView = findViewById(R.id.member_list);
        listName = new ArrayList<>();
        list = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference();
        btnFindMember = findViewById(R.id.btn_find_member);
        editFindMember = findViewById(R.id.et_find_member);
        txtMemberInfoName = findViewById(R.id.txt_member_info_name);

        viewMemberList();

        btnFindMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findMember();
            }
        });

        AdapterView.OnItemClickListener memberInfo = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(MembersListActivity.this, MemberInfoActivity.class);
                startActivity(intent);
            }
        };
        memberListView.setOnItemClickListener(memberInfo);

    }

    public void findMember() {
        List<String> foundMembers = new ArrayList<>();
        String name = editFindMember.getText().toString();

        for (String member : listName) {
            if (member.contains(name)) {
                foundMembers.add(member);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MembersListActivity.this,
                R.layout.item_list, R.id.txt_item_simple_list, foundMembers);
        memberListView.setAdapter(adapter);
    }

    public void viewMemberList() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    clubMember = ds.getValue(ClubMember.class);
                    list.add(clubMember);
                }
                for (ClubMember member : list) {
                    listName.add(member.getMemberName() + " " + member.getMemberSurname());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MembersListActivity.this,
                        R.layout.item_list, R.id.txt_item_simple_list, listName);
                memberListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
