package com.myprojects.androidlessons.sportclubmanager.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.myprojects.androidlessons.sportclubmanager.model.TextTemplate;

public class DatabaseTextTemplate extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "text_template_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TEMPLATE_TABLE = "martial_arts";
    private static final String ID_KEY = "ID";
    private static final String TEMPLATE_KEY = "template";

    public DatabaseTextTemplate(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDatabaseSQL = "create table " + TEMPLATE_TABLE + "( "
                + ID_KEY + " integer primary key autoincrement" + ", "
                + TEMPLATE_KEY + " text" +
                " )";
        db.execSQL(createDatabaseSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TEMPLATE_TABLE);
        onCreate(db);
    }

    public void addTemplate(TextTemplate template) {
        SQLiteDatabase database = getWritableDatabase();
        String addTemplateSQLCommand = "insert into " + TEMPLATE_TABLE + " values(null, '"
                + template.getTemplateMessage() + "')";
        database.execSQL(addTemplateSQLCommand);
        database.close();
    }

    public void updateTemplateObject(String textTemplate, int textTemplateId){
        SQLiteDatabase database = getWritableDatabase();
        String modifyTemplateSQLCommand = "update " + TEMPLATE_TABLE + " set "
                + TEMPLATE_KEY + " = '" + textTemplate + "' "
                + "where " + ID_KEY + " = " + textTemplateId;
        database.execSQL(modifyTemplateSQLCommand);
        database.close();
    }

    public TextTemplate returnTextTemplateObjectByID(int id){
        SQLiteDatabase database = getWritableDatabase();
        String sqlQueryCommand = "select * from " + TEMPLATE_TABLE +
                " where " + ID_KEY + " = " + id;
        Cursor cursor = database.rawQuery(sqlQueryCommand, null);
        TextTemplate textTemplate = null;

        if(cursor.moveToFirst()){
            textTemplate = new TextTemplate(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1));
        }
        database.close();
        return textTemplate;
    }

    public boolean checkDatabaseIsEmpty(){
        SQLiteDatabase database = getWritableDatabase();
        String countSqlQueryCommand = "SELECT count(*) FROM " + TEMPLATE_TABLE;
        Cursor cursor = database.rawQuery(countSqlQueryCommand, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        database.close();

        return count <= 0;
    }
}
