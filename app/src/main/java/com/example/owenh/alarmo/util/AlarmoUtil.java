package com.example.owenh.alarmo.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.File;

import static android.R.attr.start;

/**
 * Created by owenh on 2016/8/5.
 */

public class AlarmoUtil {

    public static boolean isRing(String time) {
        //判断是否需要响铃
        DBManager.getInstance().openDatabase();
        final SQLiteDatabase db = DBManager.getInstance().getDatabase();
        Cursor cursor = db.rawQuery("select isRing from SelectTimes where time = ?", new String[]{
                time
        });
        int b = 0;
        if (cursor.moveToFirst()) {
            b = cursor.getInt(cursor.getColumnIndex("isRing"));
        }
        return b > 0;
    }
}
