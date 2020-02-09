package com.myprojects.androidlessons.sportclubmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.myprojects.androidlessons.sportclubmanager.service.AddMemberActivity;
import com.myprojects.androidlessons.sportclubmanager.service.ViewAllMembersActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_members)View btnMembers;
    @BindView(R.id.btn_budget)View btnBudget;
    @BindView(R.id.btn_add_new_member)View btnAddNewMember;
    @BindView(R.id.btn_view_all_members)View btnViewAllMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnMembers.setOnClickListener(v -> openMemberCrudOperation());
        btnViewAllMembers.setOnClickListener(v -> openMemberList());
        btnBudget.setOnClickListener(v -> openBudgetActivity());
        btnAddNewMember.setOnClickListener(v -> openAddMemberActivity());

    }

    void openMemberCrudOperation(){
        Intent intent = new Intent(MainActivity.this, MembersOperationsActivity.class);
        startActivity(intent);
    }

    void openMemberList(){
        Intent intent = new Intent(MainActivity.this, ViewAllMembersActivity.class);
        startActivity(intent);
    }

    void openBudgetActivity(){
        Intent intent = new Intent(MainActivity.this, BudgetActivity.class);
        startActivity(intent);
    }

    void openAddMemberActivity(){
        Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
        startActivity(intent);
    }


}
