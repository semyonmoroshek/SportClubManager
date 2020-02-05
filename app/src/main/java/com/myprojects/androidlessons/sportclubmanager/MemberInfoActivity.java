package com.myprojects.androidlessons.sportclubmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.myprojects.androidlessons.sportclubmanager.entity.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.DatabaseClient;
import com.myprojects.androidlessons.sportclubmanager.service.ViewAllMembersActivity;

import java.util.ArrayList;
import java.util.List;

public class MemberInfoActivity extends AppCompatActivity {

    TextView txtName, txtSurname, txtPhoneNumber, txtPaymentDate;
    ListView memberListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        txtName = findViewById(R.id.txt_member_info_name);
        txtSurname = findViewById(R.id.txt_member_info_surname);
        txtPhoneNumber = findViewById(R.id.txt_member_info_phone_number);
        txtPaymentDate = findViewById(R.id.txt_member_info_payment_date);
        memberListView = findViewById(R.id.lv_info);

        txtName.setText("New name");

        setMemberInfo();
    }


// Error here
    private void setMemberInfo() {
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
                    
                    ArrayAdapter<Member> adapter = new ArrayAdapter<>(MemberInfoActivity.this,
                            R.layout.activity_member_info, R.id.txt_member_info_name, members);
                    memberListView.setAdapter(adapter);
                }
            }
            GetMember gt = new GetMember();
            gt.execute();
        }
    }









