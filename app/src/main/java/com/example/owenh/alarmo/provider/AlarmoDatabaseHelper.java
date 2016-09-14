package com.example.owenh.alarmo.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by owenh on 2016/8/21.
 */

public class AlarmoDatabaseHelper extends SQLiteOpenHelper{

    public static final String CREATE_ALARM = "create table Alarmosum (" +
            "id integer primary key autoincrement," +
            "ringuri text)";

    private Context mContext;

    public AlarmoDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory
                                factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_ALARM);
        Toast.makeText(mContext,"create database successed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
