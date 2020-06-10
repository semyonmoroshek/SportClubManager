package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.adapter.CustomAdapter;
import com.myprojects.androidlessons.sportclubmanager.model.Member;
import com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase.getInstance;

public class StatisticActivity extends AppCompatActivity {

    public static final String EXTRA_MEMBER_LIST = "EXTRA_EMPLOYEE";

    @BindView (R.id.txt_count) TextView txtMemberCount;

    CustomAdapter mAdapter;
    List <Member> mMemberList = new ArrayList<>();
    int itemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        ButterKnife.bind(this);

        mMemberList = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MEMBER_LIST));
        Log.i("memberlist10", mMemberList.toString());

        allMembers();

    }

    private void allMembers() {

        AppDatabase
                .getInstance(this)
                .getMemberDao()
                .getAllMembers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbMembers -> {

                    mAdapter = new CustomAdapter(this, dbMembers);
                    mMemberList = mAdapter.getMemberList();
                    Log.i("memberlist", mMemberList.toString());
                    itemCount = mAdapter.getItemCount();
                    Log.i("itemcount", String.valueOf(itemCount));


                });
    }
}
