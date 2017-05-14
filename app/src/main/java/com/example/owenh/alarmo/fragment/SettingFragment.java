package com.example.owenh.alarmo.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.common.C;
import com.example.owenh.alarmo.dialog.ColorDialog;

/**
 * Created by owen on 2017/5/9.
 */


//TODO 再preference的summary中显示当前pref中存储的值
// TODO 美化界面，完成设置和时间选择就推一个版本到应用宝
public class SettingFragment extends PreferenceFragment implements
        Preference.OnPreferenceClickListener
        , Preference.OnPreferenceChangeListener {

    private static final int UPDATE_SUM = 0;

    private Preference mColor;
    private String colorSum;
    SharedPreferences preferences;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_SUM:
                    mColor.setSummary(C.colorMap.get(preferences.getString("pref_text_color", "")));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
        mColor = findPreference("take_color");
        mColor.setOnPreferenceClickListener(this);
        initPref();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        initPref();
    }

    @Override
    public void onResume() {
        super.onResume();
        initPref();
    }

    private void initPref() {
        PreferenceManager.setDefaultValues(getActivity(), R.xml.pref_settings, false);
        preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        colorSum = preferences.getString("pref_text_color", "");
        Message msg = new Message();
        msg.obj = colorSum;
        msg.what = UPDATE_SUM;
        handler.sendMessage(msg);
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


    public void ShowColorDialog() {

        ColorDialog dialog = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            dialog = new ColorDialog(getContext());
        } else {
            dialog = new ColorDialog(getActivity());
        }
        final ColorDialog finalDialog = dialog;
        dialog.setYesOnclickListener(new ColorDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                initPref();
                finalDialog.dismiss();
            }
        });
        dialog.setNoOnclickListener(new ColorDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                initPref();
                finalDialog.dismiss();
            }
        });
        dialog.show();
    }
}
