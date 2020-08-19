package com.myprojects.androidlessons.sportclubmanager.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.model.TextTemplate;
import com.myprojects.androidlessons.sportclubmanager.repository.DatabaseTextTemplate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.btn_saveTemplate_1) Button btnCreateNotificationTemplate1;
    @BindView(R.id.btn_saveTemplate_2) Button btnCreateNotificationTemplate2;
    @BindView(R.id.btn_saveTemplate_3) Button btnCreateNotificationTemplate3;

    private static final String MESSAGE_TEMPLATE_1 = "messageTemplate1.txt";

    TextTemplate mTemplate;
    private String templateMessage = "";
    DatabaseTextTemplate mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        mDatabase = new DatabaseTextTemplate(this);

        addDefaultMessageNotificationTemplate();

        btnCreateNotificationTemplate1.setOnClickListener(View ->
                createNotificationTemplate());

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

    private void createNotificationTemplate() {


        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.create_message_template_dilog);


        dialog.show();

        Button btnSave = dialog.findViewById(R.id.btn_saveCreateTemplate);
        Button btnCancel = dialog.findViewById(R.id.btn_cancelCreateTemplate);

        btnSave.setOnClickListener(v -> {
            Toast.makeText(this, "save btn", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        });

        btnCancel.setOnClickListener(v -> {
            Toast.makeText(this, "cancel btn", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        });











//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Sms template");
//
//        final EditText input = new EditText(this);
//
//        input.setInputType(InputType.TYPE_CLASS_TEXT |
//                InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE |
//                InputType.TYPE_TEXT_FLAG_MULTI_LINE);
//        builder.setView(input);
//
//        builder.setPositiveButton("Save", (dialog, whichButton) -> {
//
//            templateMessage = input.getText().toString();
//            Log.i("message3", templateMessage);
//            mDatabase.updateTemplateObject(templateMessage, 1);
//            Log.i("template", mDatabase.getAllTemplates().get(0).toString());
//        });
//
//        builder.setNegativeButton("Cancel", (dialog, whichButton) -> {
//            dialog.cancel();
//            Log.i("message4", templateMessage);
//        });
//        builder.show();
    }

}
