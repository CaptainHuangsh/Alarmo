package com.example.owenh.alarmo.util;

import android.app.Service;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;

/**
 * Created by owenh on 2016/8/5.
 */

public class AlarmoUtil {

    public static AlarmoUtil getInstance() {
        return AlarmoHolder.sInstance;
    }

    public static final class AlarmoHolder {
        public static final AlarmoUtil sInstance = new AlarmoUtil();
    }

    public boolean isRing(String time) {
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

    /**
     * final Activity activity  ：调用该方法的Activity实例
     * long milliseconds ：震动的时长，单位是毫秒
     * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */

    public void vibrate(final Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(
                Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    public void vibrate(final Context context, long[] pattern,
                        boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(
                Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }
}
