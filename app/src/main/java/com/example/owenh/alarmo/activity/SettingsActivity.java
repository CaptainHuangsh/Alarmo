package com.example.owenh.alarmo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.owenh.alarmo.R;

/**
 * Created by owen on 2016/12/21.
 */

public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
    }

    public void launch(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));

    }
}
