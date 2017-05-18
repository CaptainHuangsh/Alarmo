package com.example.owenh.alarmo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by owen on 2017/5/18.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private final String CREATE_DB = "create table SelectTimes(" +
            "id integer primary key autoincrement," +
            "time text," +
            "isRing int)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
