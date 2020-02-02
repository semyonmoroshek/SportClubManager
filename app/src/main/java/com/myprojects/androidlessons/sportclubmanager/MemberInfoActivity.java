package com.myprojects.androidlessons.sportclubmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myprojects.androidlessons.sportclubmanager.entity.ClubMember;

import java.util.ArrayList;
import java.util.List;

public class MemberInfoActivity extends AppCompatActivity {

    DatabaseReference myRef;
    ClubMember clubMember;
    List<ClubMember> memberList;
    List<String> memberNameList;
    ListView memberListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        memberListView = findViewById(R.id.member_list);
        memberNameList = new ArrayList<>();
        memberList = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference();

        findMember();
    }

    private void findMember() {
        myRef.addValueEventListener(new ValueEventListener() {

            List<ClubMember> list = new ArrayList<>();
            List<String> listName = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    clubMember = ds.getValue(ClubMember.class);
                    list.add(clubMember);
                }

                for (ClubMember member : list) {
                    listName.add(member.getMemberName() + member.getMemberSurname());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MemberInfoActivity.this,
                        R.layout.item_list, R.id.txt_item_simple_list, listName);
                memberListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}

