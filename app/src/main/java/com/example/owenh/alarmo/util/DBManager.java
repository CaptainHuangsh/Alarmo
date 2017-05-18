package com.example.owenh.alarmo.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.owenh.alarmo.common.BaseApplication;

import java.io.File;

/**
 * Created by owen on 2017/5/17.
 */

public class DBManager {
    public final String DB_NAME = "alarmo_setting.db";
    public final String PACKAGE_NAME = "com.example.owenh.alarmo";
    public final String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" +
            PACKAGE_NAME;  //在手机里存放数据库的位置(/data/data/com.example.owenh.alarmo/alarmo_setting.db);
    private final String CREATE_DB = "create table SelectTimes(" +
            "id integer primary key autoincrement," +
            "time text," +
            "isRing boolean)";
    private SQLiteDatabase database;
    private Context mContext;

    public DBManager() {
        mContext = BaseApplication.getAppContext();
    }

    public static DBManager getInstance() {
        return DBManagerHolder.sInstance;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void openDatabase() {
        this.database = this.openDatabase(DB_PATH + "/databases/" + DB_NAME);
    }

    @Nullable
    private SQLiteDatabase openDatabase(String dbfile) {
        if (!(new File(dbfile).exists())) {
            MyDatabaseHelper dpHelper = new MyDatabaseHelper(BaseApplication.getAppContext()
                    , DB_NAME, null, 1);
            dpHelper.getWritableDatabase();
            Toast.makeText(BaseApplication.getAppContext(), "Create DataBase Successful", Toast.LENGTH_SHORT)
                    .show();
            //初始化SelectTimes数据表，工作时间全部为响铃，休息时间不响铃
            final SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
            final ContentValues values = new ContentValues();
            for (int i = 0; i < 48; i++) {
                values.put("time", "" + makeTextForTimesPerDay(i));
                if (i < 8 || (i > 12 && i < 28)) values.put("isRing", true);
                else values.put("isRing", false);
                db.insert("SelectTimes", null, values);
            }

        }
        return SQLiteDatabase.openOrCreateDatabase(dbfile, null);

    }

    public void closeDatabase() {
        if (this.database != null) {
            this.database.close();
        }
    }

    private static final class DBManagerHolder {
        //用于单例模式
        public static final DBManager sInstance = new DBManager();
    }

    public static String makeTextForTimesPerDay(int i) {
        if (i % 2 == 0) {
            if ((8 + i / 2 < 24 ? 8 + i / 2 : (8 + i / 2) - 24) > 9)
                return String.format("%d:30", (8 + i / 2 < 24 ? 8 + i / 2 : (8 + i / 2) - 24));
            else {
                return String.format("0%d:30", (8 + i / 2 < 24 ? 8 + i / 2 : (8 + i / 2) - 24));
            }
        } else {
            if ((9 + i / 2 < 24 ? 9 + i / 2 : (9 + i / 2) - 24) > 9)
                return String.format("%d:00", (9 + i / 2 < 24 ? 9 + i / 2 : (9 + i / 2) - 24));
            else
                return String.format("0%d:00", (9 + i / 2 < 24 ? 9 + i / 2 : (9 + i / 2) - 24));
        }
    }
}
