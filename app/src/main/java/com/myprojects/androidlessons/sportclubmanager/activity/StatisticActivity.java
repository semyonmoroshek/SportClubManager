package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.adapter.CustomAdapter;
import com.myprojects.androidlessons.sportclubmanager.model.Member;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticActivity extends AppCompatActivity {

    public static final String EXTRA_MEMBER_LIST = "EXTRA_EMPLOYEE";

    @BindView (R.id.txt_count_all_members) TextView txtMemberCount;
    @BindView (R.id.txt_active_count) TextView txtActiveMemberCount;

    CustomAdapter mAdapter;
    List <Member> mMemberList = new ArrayList<>();
    int itemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        ButterKnife.bind(this);

        mMemberList = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MEMBER_LIST));

        setFields();
    }

    private void setFields() {
        setAllMemberCountField();
        setActiveMemberCountField();
    }

    private void setActiveMemberCountField() {
        int activeMemberCount = 0;

        for(int i = 0; i < mMemberList.size(); i++ ){
            if(!mMemberList.get(i).getMemberPaymentDate().equals("No payments")){
                activeMemberCount++;
            }
        }
        String activeMemberCountString = String.valueOf(activeMemberCount);
        txtActiveMemberCount.setText(activeMemberCountString);
    }

    private void setAllMemberCountField() {
        String memberCount = String.valueOf(mMemberList.size());
        txtMemberCount.setText(memberCount);
    }
}
