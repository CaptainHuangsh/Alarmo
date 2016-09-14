package com.example.actionbartest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import java.lang.reflect.Method;

/**
 * Created by owenh on 2016/9/11.
 */

public class ActionSearch6 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actionbar_2);
        setTitle("actionSearch");
        android.app.ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);//设置返回（通过Action Bar图标进行导航）
    }
    //重写Activity的onCreateOptionsMenu()方法
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_2,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        /*searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                //When the searchCalss expanded
                Log.d("ActionSearch","on expand");
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                //When the searchCalss collapsed
                Log.d("ActionSearch","on collaps");
                return false;
            }
        });*/
        return super.onCreateOptionsMenu(menu);
    }

    //overflow中的图标显示(未起作用)
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
    //响应Action按钮的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_p:
                Intent intent1 = new Intent(this,ActionProvider.class);
                startActivity(intent1);
                return true;
            case R.id.edit_p:
                Toast.makeText(this, "edit clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.star:
                Toast.makeText(this, "star clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
