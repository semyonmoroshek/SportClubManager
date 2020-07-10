package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.model.Member;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticActivity extends AppCompatActivity {

    public static final String EXTRA_MEMBER_LIST = "EXTRA_EMPLOYEE";

    @BindView (R.id.txt_count_all_members) TextView txtMemberCount;
    @BindView (R.id.txt_active_count) TextView txtActiveMemberCount;
    @BindView(R.id.tb_statistic) Toolbar mToolbar;
    @BindView(R.id.graph_member_count) GraphView mGraphView;

    LineGraphSeries<DataPoint> series;
    List <Member> mMemberList = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        series = new LineGraphSeries<>(getDataPoint());
        mGraphView.addSeries(series);
        mGraphView.getGridLabelRenderer()
                .setLabelFormatter(new DefaultLabelFormatter(){
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if(isValueX){
                            return sdf.format(new Date((long)value));
                        }
                        return super.formatLabel(value, isValueX);
                    }
                });

        mMemberList = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MEMBER_LIST));

        Log.i("mlist", mMemberList.toString());

        setFields();
    }

    private DataPoint[] getDataPoint() {
        DataPoint[] dp = new DataPoint[]{
                new DataPoint(new Date().getTime(), 1),
                new DataPoint(new Date().getTime(), 2),
                new DataPoint(new Date().getTime(), 13),
                new DataPoint(new Date().getTime(), 9),
                new DataPoint(new Date().getTime(), 5),
                new DataPoint(new Date().getTime(), 3),
        };
        return dp;
    }

    private void setFields() {
        setAllMemberCountField();
        setActiveMemberCountField();
//        setMembersCountGraph();
    }

    private void setMembersCountGraph() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyy");
        Log.i("mdate", sdf.format(date));
    }

    private void setActiveMemberCountField() {
        int activeMemberCount = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();

        for(int i = 0; i < mMemberList.size(); i++ ){
            Member member = mMemberList.get(i);
            if(member.getMemberPaymentDate() != null &&
                    !member.getMemberPaymentDate().equals("No payments")){

                try {
                    Date memberPaymentDate = dateFormat.parse(member.getMemberPaymentDate());
                    Calendar memberPaymentUntilDate = Calendar.getInstance();
                    memberPaymentUntilDate.setTime(memberPaymentDate);
                    memberPaymentUntilDate.add(Calendar.MONTH, 1);
                    Date memberPaymentUntilDateDateFormat = memberPaymentUntilDate.getTime();


                    if(memberPaymentUntilDateDateFormat.after(today)){
                        activeMemberCount++;
                        Log.i("activeMemberCount", String.valueOf(activeMemberCount));
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        String activeMemberCountString = String.valueOf(activeMemberCount);
        txtActiveMemberCount.setText(activeMemberCountString);
    }

    private void setAllMemberCountField() {
        String memberCount = String.valueOf(mMemberList.size());
        txtMemberCount.setText(memberCount);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent mIntent = new Intent(getApplicationContext(), ViewAllMemberActivity.class);
        startActivityForResult(mIntent, 0);

        return true;
    }
}
