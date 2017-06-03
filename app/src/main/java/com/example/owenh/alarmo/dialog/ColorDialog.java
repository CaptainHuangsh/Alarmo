package com.example.owenh.alarmo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.adapter.ColorListAdapter;
import com.example.owenh.alarmo.provider.domain.AColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owen on 2017/5/10.
 */

public class ColorDialog extends Dialog {

    private Context mContext;
    private ListView listView;
    private Button yes, no;
    private List<AColor> colors = new ArrayList<>();
    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    public ColorDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_take_color);
        initColor();
        initView();
        initEvent();
//        PreferenceManager.setDefaultValues(getContext(), R.xml.pref_settings, false);
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        ColorListAdapter mAdapter = new ColorListAdapter(getContext(), R.layout.items_take_color, colors);
        listView = (ListView) findViewById(R.id.main_color_list);
        listView.setAdapter(mAdapter);
//        final SharedPreferences finalPreferences = preferences;
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finalPreferences.edit().putString("pref_text_color", colors.get(position).getColorValue()).apply();
                dismiss();
            }
        });*/
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });
    }

    private void initView() {
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);
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

    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }

    public void setNoOnclickListener(onNoOnclickListener onNoOnclickListener) {
        this.noOnclickListener = onNoOnclickListener;
    }


    public interface onYesOnclickListener {
        void onYesClick();
    }

    public interface onNoOnclickListener {
        void onNoClick();
    }
}
