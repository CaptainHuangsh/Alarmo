package com.example.datatest.activity.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datatest.R;
import com.example.datatest.activity.provider.TDatabaseHelper;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    private Button mBtnCreateData;
    private Button mViewData;
    private Button mAdd;
    private Button mViewPage;
    private EditText mName;
    private EditText mPrice;
    private EditText mDate;
    private EditText mPage;
    private TextView mPreView;
    private TDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();
        setListener();
        init();
    }

    public void init() {
        dbHelper = new TDatabaseHelper(this, "TestDa.db", null, 2);
    }

    public void findview() {
        mBtnCreateData = (Button) findViewById(R.id.create_Database);
        mAdd = (Button) findViewById(R.id.data_add);
        mViewData = (Button) findViewById(R.id.view_data);
        mName = (EditText) findViewById(R.id.data_name);
        mPrice = (EditText) findViewById(R.id.data_price);
        mDate = (EditText) findViewById(R.id.data_date);
        mPage = (EditText) findViewById(R.id.data_page);
        mPreView = (TextView) findViewById(R.id.pre_view);
        mViewPage = (Button)findViewById(R.id.view_page);
    }

    public void setListener() {
        mBtnCreateData.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mViewData.setOnClickListener(this);
        mViewPage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_Database:
                createDataBase(v);
                break;
            case R.id.data_add:
                addData();
                break;
            case R.id.view_data:
                viewD();
                break;
            case R.id.view_page:
                Intent intent = new Intent(this, ViewPage.class);
                startActivity(intent);
                break;
        }
    }

    public void addData() {
        if (!(TextUtils.isEmpty(mName.getText()) ||
                TextUtils.isEmpty(mPrice.getText()) ||
                TextUtils.isEmpty(mDate.getText()) ||
                TextUtils.isEmpty(mPage.getText()))) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            //组装数据
            values.put("name", mName.getText() + "");
            values.put("price", Integer.parseInt(mPrice.getText() + ""));
            values.put("date", mDate.getText() + "");
            values.put("page", Integer.parseInt(mPage.getText() + ""));
            db.insert("DataTest", null, values);
            values.clear();
            Log.d("TestInsert", "fff");
        }

    }

    public void viewD() {
        if (!(TextUtils.isEmpty(mName.getText()) ||
                TextUtils.isEmpty(mPrice.getText()) ||
                TextUtils.isEmpty(mDate.getText()) ||
                TextUtils.isEmpty(mPage.getText()))) {
            String ss = "" + mName.getText() + "\n" + mPrice.getText() + "\n"
                    + mDate.getText() + "\n" + mPage.getText();
            mPreView.setText(ss);
            Toast.makeText(this, "ok?", Toast.LENGTH_SHORT).show();
        }
        String sss = "";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //查询数据
        Cursor cursor = db.query("DataTest", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                //遍历Cursor对象取出数据
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int price = cursor.getInt(cursor.getColumnIndex("price"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                int page = cursor.getInt(cursor.getColumnIndex("page"));
                Log.d("ManActivity", "" + id);
                Log.d("ManActivity", name);
                Log.d("ManActivity", "" + price);
                Log.d("ManActivity", date);
                Log.d("ManActivity", "" + page);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    //创建数据库表  第一次点击时系统会检测是否已存在此数据库文件，自动判断是否创建此数据库文件
    public void createDataBase(View view) {
        Log.d("createdata", "true");
//        Snackbar.make(view,"haung",Snackbar.LENGTH_LONG).show();

        //最后一个参数为数据库版本信息，比前一个值大即可执行TDatabaseHelper的onUpgrade();
        dbHelper.getWritableDatabase();
    }
}
