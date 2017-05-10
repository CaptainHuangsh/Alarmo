package com.example.owenh.alarmo.fragment;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.dialog.ColorDialog;

/**
 * Created by owen on 2017/5/9.
 */


//TODO 再preference的summary中显示当前pref中存储的值
// TODO 美化界面，完成设置和时间选择就推一个版本到应用宝
public class SettingFragment extends PreferenceFragment implements
        Preference.OnPreferenceClickListener
        , Preference.OnPreferenceChangeListener {
    private Preference mColor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
        mColor = findPreference("take_color");
        mColor.setOnPreferenceClickListener(this);
        initPref();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("settingsFragment","onStart");
        initPref();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("settingsFragment","onResume");

    }

    private void initPref() {
        PreferenceManager.setDefaultValues(getActivity(), R.xml.pref_settings, false);
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        mColor.setSummary(preferences.getString("pref_text_color", ""));
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (mColor == preference) {
            ShowColorDialog();
        }
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void ShowColorDialog() {

        ColorDialog dialog = new ColorDialog(getContext());
        dialog.show();
    }
}
