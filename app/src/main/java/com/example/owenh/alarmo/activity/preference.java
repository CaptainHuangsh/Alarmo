package com.example.owenh.alarmo.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

import com.example.owenh.alarmo.R;

/**
 * Created by owen on 2016/12/21.
 */

public class preference extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private EditTextPreference mEtPreference;
    private ListPreference mListPreference;
    private CheckBoxPreference mCheckPreference;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        addPreferencesFromResource(R.xml.settings);
        initPreferences();
    }
    private void initPreferences() {
//        mEtPreference = (EditTextPreference)findPreference(Consts.EDIT_KEY);
//        mListPreference = (ListPreference)findPreference(Consts.LIST_KEY);
//        mCheckPreference = (CheckBoxPreference)findPreference(Consts.CHECKOUT_KEY);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
