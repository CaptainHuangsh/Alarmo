package com.example.owenh.alarmo.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.dialog.ColorDialog;

/**
 * Created by owen on 2017/5/9.
 */

public class SettingFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener
        , Preference.OnPreferenceChangeListener {
    private Preference mColor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
        mColor = findPreference("take_color");
        mColor.setOnPreferenceClickListener(this);
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
        /*final String[] items = {
                "NetWork", "ActivityLife", "Fragment", "Broadcast", "Notification"
                , "Service", "Settings"
        };*/
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity()
                , android.R.layout.simple_list_item_1, items);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ListView listView = (ListView) getActivity().findViewById(R.id.main_color_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"heheda"+items[position],Toast.LENGTH_SHORT).show();
            }
        });*/
//        View dialogLayout = inflater.inflate(R.layout.dialog_take_color, (ViewGroup) getActivity().findViewById(R.id.dialog_root));
        /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setItems(items,null);
        AlertDialog dialog = builder.create();
        dialog.show();*/
        ColorDialog dialog = new ColorDialog(getContext());
        dialog.show();
    }
}
