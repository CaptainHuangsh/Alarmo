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

public class RingUtil {

    private MediaPlayer mediaPlayer = new MediaPlayer();

    public void Ring() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(),
                    "ring0.mp3");
            mediaPlayer.setDataSource((file.getPath()));//指定音频文件
            mediaPlayer.prepare();//让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ringStartOrStop() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        } else {
            mediaPlayer.reset();
        }
    }

    public static boolean isRing(String time) {
        Log.d("huangshaohua", "2 " + time);
        DBManager.getInstance().openDatabase();
        final SQLiteDatabase db = DBManager.getInstance().getDatabase();
        Cursor cursor = db.rawQuery("select isRing from SelectTimes where time = ?", new String[]{
                time
        });
        int b = 0;
        if (cursor.moveToFirst()) {
            Log.d("huangshaohua", "3 " + time);
            b = cursor.getInt(cursor.getColumnIndex("isRing"));
        }
        Log.d("huangshaohua", "4 " + (b > 0));
        return b > 0;
    }
}
