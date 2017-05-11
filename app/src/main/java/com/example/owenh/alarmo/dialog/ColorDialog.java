package com.example.owenh.alarmo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.adapter.ColorAdapter;
import com.example.owenh.alarmo.common.C;
import com.example.owenh.alarmo.domain.AColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owen on 2017/5/10.
 */

public class ColorDialog extends Dialog {

    private Context mContext;

    private Button yes, no;
    private List<AColor> colors = new ArrayList<AColor>();

    public ColorDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_take_color);
        initColor();
        PreferenceManager.setDefaultValues(getContext(), R.xml.pref_settings, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        ColorAdapter mAdapter = new ColorAdapter(getContext(), R.layout.items_take_color, colors);
        ListView listView = (ListView) findViewById(R.id.main_color_list);
        listView.setAdapter(mAdapter);
        final SharedPreferences finalPreferences = preferences;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finalPreferences.edit().putString("pref_text_color", colors.get(position).getColorValue()).apply();
                dismiss();
            }
        });
    }

    //设置中的内置颜色选项
    private void initColor() {
        //http://blog.csdn.net/u013058160/article/details/49158779
        AColor aColor = new AColor("蓝色", "#0000FF");
        colors.add(aColor);
        aColor = new AColor("绿色", "#008000");
        colors.add(aColor);
        aColor = new AColor("暗青色", "#008B8B");
        colors.add(aColor);
        aColor = new AColor("深天蓝色", "#00BFFF");
        colors.add(aColor);
        aColor = new AColor("暗宝石绿", "#00CED1");
        colors.add(aColor);
        aColor = new AColor("酸橙色", "#00FF00");
        colors.add(aColor);
        aColor = new AColor("浅绿色", "#00FFFF");
        colors.add(aColor);
        aColor = new AColor("靛青色", "#4B0082");
        colors.add(aColor);
        aColor = new AColor("薄荷色", "#F5FFFA");
        colors.add(aColor);
        aColor = new AColor("幽灵白", "#F8F8FF");
        colors.add(aColor);
        aColor = new AColor("红色", "#FF0000");
        colors.add(aColor);
        aColor = new AColor("金色", "#FFD700");
        colors.add(aColor);
        aColor = new AColor("黄色", "#FFFF00");
        colors.add(aColor);


    }
}
