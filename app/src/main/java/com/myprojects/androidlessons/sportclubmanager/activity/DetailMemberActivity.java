package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.model.Member;
import com.myprojects.androidlessons.sportclubmanager.model.TextTemplate;
import com.myprojects.androidlessons.sportclubmanager.repository.AppDatabase;
import com.myprojects.androidlessons.sportclubmanager.repository.DatabaseTextTemplate;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailMemberActivity extends AppCompatActivity {

    public static final String EXTRA_MEMBER = "EXTRA_EMPLOYEE";
    public static final int REQUEST_CALL = 1;

    @BindView(R.id.txt_member_info_name) TextView txtName;
    @BindView(R.id.txt_member_info_surname) TextView txtSurname;
    @BindView(R.id.txt_member_info_phone_number) TextView txtPhoneNumber;
    @BindView(R.id.txt_member_info_date_birth) TextView txtDateBirth;
    @BindView(R.id.txt_member_info_payment_date) TextView txtPaymentDate;
    @BindView(R.id.tb_member_info) Toolbar mToolbar;
    @BindView(R.id.iv_phone) ImageView ivPhone;
    @BindView(R.id.btn_send_notification) Button btnSendNotification;
    @BindView(R.id.btn_sms_template) Button btnCreateNotificationTemplate;
    @BindView(R.id.fab_detail_delete) FloatingActionButton fabDelete;
    @BindView(R.id.fab_detail_edit) FloatingActionButton fabEdit;
    @BindView(R.id.fab_detail_payment) FloatingActionButton fabPayment;
    @BindView(R.id.iv_message) ImageView imgMessage;
    @BindView(R.id.iv_call) ImageView imgCall;

    Member member;
    private String templateMessage = "";
    TextTemplate mTemplate;
    DatabaseTextTemplate mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDatabase = new DatabaseTextTemplate(this);

        AlertDialog diaBox = AskOption();

        member = Parcels.unwrap(getIntent()
                .getParcelableExtra(EXTRA_MEMBER));
        setMemberDetailFields();

        fabEdit.setOnClickListener(View -> openEditMemberActivity());
        fabPayment.setOnClickListener(View -> addPayment());
        fabDelete.setOnClickListener(View -> diaBox.show());

        addDefaultMessageNotificationTemplate();

        btnSendNotification.setOnClickListener(View ->
                sendNotificationSms());
        btnCreateNotificationTemplate.setOnClickListener(View ->
                createNotificationTemplate());

        ivPhone.setOnClickListener(View -> call());

        txtPhoneNumber.setOnClickListener(View -> call());

        imgCall.setOnClickListener(View -> call());

        imgMessage.setOnClickListener(View -> sendNotificationSms());
    }

    private void addDefaultMessageNotificationTemplate() {
        if(mDatabase.getProfilesCount() == 0){
            templateMessage = "Hello! Looks like you have an unpaid bill for a trainings." + "\n" +
                    "Please pay it." + "\n" + "Thank you. Have a nice day!";
            mTemplate = new TextTemplate(1, templateMessage);
            mDatabase.addTemplate(mTemplate);
        }else{
            templateMessage = mDatabase.getAllTemplates().get(0).getTemplateMessage();
        }
    }

    private void setMemberDetailFields() {
        if (member != null) {
            txtName.setText(member.getMemberName());
            txtSurname.setText(member.getMemberSurname());
            txtPhoneNumber.setText(member.getMemberPhoneNumber());
            txtPaymentDate.setText(member.getMemberPaymentDate());
            txtDateBirth.setText(member.getMemberDateBirth());
        }
    }

    private void sendNotificationSms() {
        String phoneNumber = member.getMemberPhoneNumber();
        String message = mDatabase.getAllTemplates().get(0).getTemplateMessage();

        Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("sms:" + phoneNumber)
        );
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }

    private void createNotificationTemplate() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sms template");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE |
                InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, whichButton) -> {

            templateMessage = input.getText().toString();
            Log.i("message3", templateMessage);
            mDatabase.updateTemplateObject(templateMessage, 1);
            Log.i("template", mDatabase.getAllTemplates().get(0).toString());
        });

        builder.setNegativeButton("Cancel", (dialog, whichButton) -> {
            dialog.cancel();
            Log.i("message4", templateMessage);
        });
        builder.show();
    }

    private void call() {

        String number = member.getMemberPhoneNumber();

        if (number.length() > 0) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(
                        Intent.ACTION_CALL,
                        Uri.parse(dial)));
            }
        } else {
            Toast.makeText(this,
                    "Phone number is empty",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                call();
            } else {
                Toast.makeText(this,
                        "Permission DENIED",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private AlertDialog AskOption() {

        return new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure want to delete member?")
                .setIcon(R.drawable.ic_delete_forever)
                .setPositiveButton("Delete", (dialog, whichButton) -> {
                    deleteMember();
                    dialog.dismiss();
                    Toast.makeText(this,
                            member.getMemberName() + " " + "deleted",
                            Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("cancel", (dialog, which) ->
                        dialog.dismiss())
                .create();
    }

    private void addPayment() {
        Intent intent = new Intent(
                DetailMemberActivity.this,
                AddPaymentActivity.class
        );
        intent.putExtra(DetailMemberActivity.EXTRA_MEMBER,
                Parcels.wrap(member));
        startActivity(intent);
    }

    private void deleteMember() {
        AppDatabase
                .getInstance(this)
                .getMemberDao()
                .deleteMember(member)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        openMembersList();
    }

    private void openMembersList() {
        Intent intent = new Intent(this, ViewAllMemberActivity.class);
        startActivity(intent);
        finish();
    }

    void openEditMemberActivity() {
        Intent intent = new Intent(
                DetailMemberActivity.this,
                EditMemberActivity.class);
        intent.putExtra(DetailMemberActivity.EXTRA_MEMBER, Parcels.wrap(member));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(),
                ViewAllMemberActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}










