package com.myprojects.androidlessons.sportclubmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myprojects.androidlessons.sportclubmanager.service.AddMemberActivity;
import com.myprojects.androidlessons.sportclubmanager.service.ViewAllMembersActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button btnMembers = findViewById(R.id.btn_members);
//        Button btnPayments = findViewById(R.id.btn_payments);
        Button btnBudget = findViewById(R.id.btn_budget);
        Button btnAddNewMember = findViewById(R.id.btn_add_new_member);
        Button btnViewAllMembers = findViewById(R.id.btn_view_all_members);


        btnMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MembersOperationsActivity.class);
                startActivity(intent);
            }
        });

        btnViewAllMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewAllMembersActivity.class);
                startActivity(intent);
            }
        });


        btnBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BudgetActivity.class);
                startActivity(intent);
            }
        });

        btnAddNewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });

    }
}
