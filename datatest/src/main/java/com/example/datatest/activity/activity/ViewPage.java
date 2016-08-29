package com.example.datatest.activity.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.datatest.R;
import com.example.datatest.activity.provider.TDatabaseHelper;
import com.example.datatest.activity.provider.TestAdapter;
import com.example.datatest.activity.provider.TestI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owenh on 2016/8/28.
 */

public class ViewPage extends Activity {

    private List<TestI> testIList = new ArrayList<TestI>();
    private ListView listView;
    private TDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpage);
        findView();
        initTestI();
        TestAdapter adapter = new TestAdapter(ViewPage.this,
                R.layout.test_item,testIList);
        listView.setAdapter(adapter);
    }

    public void initTestI(){
        dbHelper = new TDatabaseHelper(this, "TestDa.db", null, 2);
       /* TestI testI1 = new TestI("Android",100,"897",2);
        testIList.add(testI1);
        TestI testI2 = new TestI("Java",190,"33",2);
        testIList.add(testI2);
        TestI testI3 = new TestI("Python",177,"dadas",2);
        testIList.add(testI3);
        TestI testI4 = new TestI("ruby",1989,"da",2);
        testIList.add(testI4);*/

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
                TestI testI0 = new TestI(name,price,date,page);
                testIList.add(testI0);
            } while (cursor.moveToNext());
        }
        cursor.close();

    }

    public void findView(){
        listView = (ListView)findViewById(R.id.Test_view);
    }
}
