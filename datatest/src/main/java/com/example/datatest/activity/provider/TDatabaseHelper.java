package com.example.datatest.activity.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by owenh on 2016/8/21.
 */

public class TDatabaseHelper extends SQLiteOpenHelper{

    private final String CREATE_DATA = "create table DataTest (" +
            "id integer primary key autoincrement," +
            "name text," +
            "price iteger," +
            "date text," +
            "page integer)";
    private final String CREATE_PEO = "create table DataPeo(" +
            "id integer primary key autoincrement," +
            "peo_name text," +
            "peo_age integer," +
            "peo_sex text)";

    private Context mContext;

    public TDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory
                                factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_DATA);
        db.execSQL(CREATE_PEO);
        Toast.makeText(mContext,"create database successed",Toast.LENGTH_SHORT).show();
    }

    //升级数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists DataTest");
        db.execSQL("drop table if exists DataPeo");
        onCreate(db);
    }

}
