package com.example.datatest.activity.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class ViewPage extends Activity implements Button.OnClickListener{

    private List<TestI> testIList = new ArrayList<TestI>();
    private ListView listView;
    private TDatabaseHelper dbHelper;
    private TestAdapter adapter;
    private boolean isLongClick;//用于判断是否有长点击事件，以和点击事件实现共存
    private LayoutInflater mLayoutInflater;
    private View mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpage);
        findView();
        setListAdapter();
        initTestI();
        setOnclickListener();
    }

    public void initTestI(){

        adapter = new TestAdapter(ViewPage.this,
                R.layout.test_item,testIList);
        listView.setAdapter(adapter);
        mLayoutInflater = LayoutInflater.from(this);


    }

    public void findView(){
        listView = (ListView)findViewById(R.id.Test_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case Dialog.BUTTON_POSITIVE:
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
            case Dialog.BUTTON_NEUTRAL:
                break;
        }
    }

    public void setListAdapter(){
        dbHelper = new TDatabaseHelper(this, "TestDa.db", null, 2);

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
                TestI testI0 = new TestI(id,name,price,date,page);
                testIList.add(testI0);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


    public void setOnclickListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TestI testI = testIList.get(position);
                mView = mLayoutInflater.inflate(R.layout.edit_dialog,null);

                final AlertDialog.Builder builder = new AlertDialog.Builder(ViewPage.this);
                builder.setTitle("tittle");
                builder.setView(mView);
                final EditText editName = (EditText)mView.findViewById(R.id.dialog_data_name);
                final EditText editPrice = (EditText)mView.findViewById(R.id.dialog_data_price);
                final EditText editDate = (EditText)mView.findViewById(R.id.dialog_data_date);
                final EditText editPage = (EditText)mView.findViewById(R.id.dialog_data_page);
                editName.setText(testI.getName()+"");
                editPrice.setText(testI.getPrice()+"");
                editDate.setText(testI.getDate()+"");
                editPage.setText(testI.getPage()+"");
                final SQLiteDatabase db = dbHelper.getWritableDatabase();
                final ContentValues values = new ContentValues();
                builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        builder
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!editName.getText().equals(testI.getName()+"")){
                            values.put("name",editName.getText()+"");
                            db.update("DataTest",values,"id = ?",new String[] {
                                    ""+testI.getId()});
                            values.clear();
                        }
                        if(!editDate.getText().equals(testI.getDate()+"")){
                            values.put("date",editDate.getText()+"");
                            db.update("DataTest",values,"id = ?",new String[] {
                                    ""+testI.getId()});
                            values.clear();
                        }
                        if(!editPrice.getText().equals(testI.getPrice()+"")){
                            values.put("price",editPrice.getText()+"");
                            db.update("DataTest",values,"id = ?",new String[] {
                                    ""+testI.getId()});
                            values.clear();
                        }
                        if(!editPage.getText().equals(testI.getPage()+"")){
                            values.put("page",editPage.getText()+"");
                            db.update("DataTest",values,"id = ?",new String[] {
                                    ""+testI.getId()});
                            values.clear();
                        }
                        //不起作用
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.show();
               /*  final CustomDialog dialog = new CustomDialog(ViewPage.this,
                        R.style.customDialog,R.layout.edit_dialog);
                EditText editName = (EditText)mView.findViewById(R.id.dialog_data_name);
                EditText editPrice = (EditText)mView.findViewById(R.id.dialog_data_price);
                EditText editDate = (EditText)mView.findViewById(R.id.dialog_data_date);
                EditText editPage = (EditText)mView.findViewById(R.id.dialog_data_page);
                Button ok = (Button)mView.findViewById(R.id.dialog_edit_ok);
                Button cancle = (Button)mView.findViewById(R.id.dialog_edit_cancle);
//                editName.setText("dasd");
//                editPrice.setText("565");
//                editDate.setText("4234");
//                editPage.setText("3245");
                *//*editName.setText(testI.getName()+"");
                editPrice.setText(Integer.parseInt(testI.getPrice()+""));
                editDate.setText(testI.getDate()+"");
                editPage.setText(Integer.parseInt(testI.getPage()+""));*//**//*
//                ok.setOnClickListener(ViewPage.this);
//                cancle.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });*//*
                dialog.show();
*/
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final TestI testI = testIList.get(position);
//                Toast.makeText(ViewPage.this,testI.getId()+"",Toast.LENGTH_LONG).show();
                final SQLiteDatabase db = dbHelper.getWritableDatabase();
                //delete()的第三个参数必须是 new String() {""}这种格式 这些应遵循sql语句
                /*db.delete("DataTest","id = ?",new String[] {""+testI.getId()});
                adapter.notifyDataSetChanged();
                return false;*/
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewPage.this);//先得到构造器
                builder.setTitle("Wanning");//标题
                builder.setMessage("Delete it ?");//内容
                builder.setIcon(R.mipmap.ic_launcher);//图标
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //delete()的第三个参数必须是 new String() {""}这种格式 这些应遵循sql语句
                        db.delete("DataTest","id = ?",new String[] {""+testI.getId()});
                        //确定按钮
//                 listView.setAdapter(adapter);


                        //每次删除操作后刷新
                        adapter.clear();
//                        adapter.notifyDataSetChanged();
                        setListAdapter();
                        initTestI();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //取消按钮
                        dialog.dismiss();//关闭dialog
                    }
                });
                builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //忽略按钮
                        dialog.dismiss();
                    }
                });
        /*
        * 可以简化代码如下
        *
        * */
                /*builder.setPositiveButton("确认", (DialogInterface.OnClickListener) this);
                builder.setNegativeButton("取消", (DialogInterface.OnClickListener) this);
                builder.setNeutralButton("忽略", (DialogInterface.OnClickListener) this);*/
                //此处不可用
                builder.create().show();
                return true;
                //将长点击事件返回值设置为true，否则会同时触发点击事件
            }
        });
    }
}
