package com.myprojects.androidlessons.sportclubmanager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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
import com.myprojects.androidlessons.sportclubmanager.entity.Member;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MembersListActivity extends AppCompatActivity {

    DatabaseReference myRef;
    Member clubMember;
    List<Member> list;
    List<String> listName;

    @BindView(R.id.member_list) ListView memberListView;
    @BindView(R.id.btn_find_member) Button btnFindMember;
    @BindView(R.id.et_find_member) EditText editFindMember;
    @BindView(R.id.txt_member_info_name) TextView txtMemberInfoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_list);
        ButterKnife.bind(this);
        listName = new ArrayList<>();
        list = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference();
        viewMemberList();
        btnFindMember.setOnClickListener(v -> findMember());
        AdapterView.OnItemClickListener memberInfo = (parent, v, position, id) -> {
            Intent intent = new Intent(MembersListActivity.this, MemberInfoActivity.class);
            startActivity(intent);
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
                    clubMember = ds.getValue(Member.class);
                    list.add(clubMember);
                }
                for (Member member : list) {
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
