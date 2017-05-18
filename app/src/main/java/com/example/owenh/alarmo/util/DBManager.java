package com.example.owenh.alarmo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
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
        Log.d("huangshaohuacontext",""+mContext);
    }

    public static DBManager getInstance() {
        return DBManagerHolder.sInstance;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void openDatabase() {
        Log.d("huangshaohua",""+DB_PATH + "/" + DB_NAME);
        //Log.e(TAG, DB_PATH + "/" + DB_NAME);
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }

    @Nullable
    private SQLiteDatabase openDatabase(String dbfile) {
            if (!(new File(dbfile).exists())) {
                MyDatabaseHelper dpHelper = new MyDatabaseHelper(BaseApplication.getAppContext()
                ,DB_NAME,null,1);
                dpHelper.getWritableDatabase();
                Toast.makeText(BaseApplication.getAppContext(),"Create DataBase Successful",Toast.LENGTH_SHORT)
                        .show();
                Log.d("huangshaohua","4");
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
}
