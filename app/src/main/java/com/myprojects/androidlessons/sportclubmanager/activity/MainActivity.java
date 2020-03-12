package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.myprojects.androidlessons.sportclubmanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_members)
    View btnMembers;
    @BindView(R.id.btn_budget)
    View btnBudget;
    @BindView(R.id.btn_add_new_member)
    View btnAddNewMember;
    @BindView(R.id.btn_view_all_members)
    View btnViewAllMembers;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();
        btnViewAllMembers.setOnClickListener(v -> openMemberList());
        btnAddNewMember.setOnClickListener(v -> openAddMemberActivity());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Toast.makeText(this, "Some action", Toast.LENGTH_LONG).show();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    void openMemberList() {
        Intent intent = new Intent(MainActivity.this, ViewAllMemberActivity.class);
        startActivity(intent);
    }


    void openAddMemberActivity() {
        Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
        startActivity(intent);
    }
}
