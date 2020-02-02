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




    }

    private void findMember() {

    }

}

