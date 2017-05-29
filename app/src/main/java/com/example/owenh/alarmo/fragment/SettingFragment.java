package com.example.owenh.alarmo.fragment;

import android.content.Intent;
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
import com.example.owenh.alarmo.activity.WatchActivity;
import com.example.owenh.alarmo.common.C;
import com.example.owenh.alarmo.dialog.ColorDialog;
import com.example.owenh.alarmo.dialog.TimesDialog;
import com.example.owenh.alarmo.util.DBManager;
import com.example.owenh.alarmo.util.SPUtils;

/**
 * Created by owen on 2017/5/9
 */

public class SettingFragment extends PreferenceFragment implements
        Preference.OnPreferenceClickListener
        , Preference.OnPreferenceChangeListener {

    private static final int UPDATE_COLOR_SUM = 0;

    private Preference mColor;
    private Preference mSelectTimes;
    private Preference mAddIcon;
    private String colorSum;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_COLOR_SUM:
                    mColor.setSummary(C.colorMap.get((String) SPUtils.getInstance().get(getActivity(), "pref_text_color", "")));
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
        mSelectTimes = findPreference("pref_select_times");
        mAddIcon = findPreference("add_icon");
//        mOneMinAdvance = findPreference("pref_advance_one");
        mColor.setOnPreferenceClickListener(this);
        mSelectTimes.setOnPreferenceClickListener(this);
        mAddIcon.setOnPreferenceClickListener(this);
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
        colorSum = (String) SPUtils.getInstance().get(getActivity(), "pref_text_color", "");
        Message msg = new Message();
        msg.obj = colorSum;
        msg.what = UPDATE_COLOR_SUM;
        handler.sendMessage(msg);
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (mColor == preference) {
            ShowColorDialog();
        }
        if (mSelectTimes == preference) {
            ShowTimesDialog();
        }
        if (mAddIcon == preference) {
            addIcon();
        }
        return true;
    }

    private void ShowTimesDialog() {
        TimesDialog dialog = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            dialog = new TimesDialog(getContext());
        } else {
            dialog = new TimesDialog(getActivity());
        }
        final TimesDialog finalDialog = dialog;
        dialog.setYesOnclickListener(new TimesDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                finalDialog.dismiss();
                DBManager.getInstance().closeDatabase();
            }
        });
        dialog.setNoOnclickListener(new TimesDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                finalDialog.dismiss();
                DBManager.getInstance().closeDatabase();
            }
        });
        dialog.show();
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


    /**
     * 添加当前应用的桌面快捷方式
     */
    public void addIcon() {
        // 安装的Intent
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 快捷名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Alarmo时钟");
        // 快捷图标是允许重复
        shortcut.putExtra("duplicate", false);
        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
//        shortcutIntent.putExtra("tName", "aaa");
        shortcutIntent.setClass(getActivity(), WatchActivity.class);
        shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        // 快捷图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(getActivity(), R.drawable.alarm_clock);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        // 发送广播
        getActivity().sendBroadcast(shortcut);
        // 经测试不是根据快捷方式的名字判断重复的
        // 应该是根据快链的Intent来判断是否重复的,即Intent.EXTRA_SHORTCUT_INTENT字段的value
        // 但是名称不同时，虽然有的手机系统会显示Toast提示重复，仍然会建立快链
        // 屏幕上没有空间时会提示
        // 注意：重复创建的行为MIUI和三星手机上不太一样，小米上似乎不能重复创建快捷方式
        // 名字
    }

}
