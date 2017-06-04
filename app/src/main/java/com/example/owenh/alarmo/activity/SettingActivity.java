package com.example.owenh.alarmo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.fragment.SettingFragment;

/**
 * Created by owen on 2017/5/9.
 */

public class SettingActivity extends AppCompatActivity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        this.getFragmentManager().beginTransaction()
                .replace(R.id.main_setting,new SettingFragment())
                .commit();
    }

}
