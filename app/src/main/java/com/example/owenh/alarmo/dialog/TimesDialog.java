package com.example.owenh.alarmo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.adapter.ColorListAdapter;
import com.example.owenh.alarmo.domain.AColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owen on 2017/5/10.
 */

public class TimesDialog extends Dialog {

    private Context mContext;

    private Button yes, no;
    private List<AColor> colors = new ArrayList<AColor>();
    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private LinearLayout mLRepeatTimes;
    private LinearLayout mRRepeatTimes;
    private  CompoundButton[] mTimesButtons = new CompoundButton[24];;


    public TimesDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_times);
        initView();
        initEvent();
        PreferenceManager.setDefaultValues(getContext(), R.xml.pref_settings, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        ColorListAdapter mAdapter = new ColorListAdapter(getContext(), R.layout.items_take_color, colors);


        final SharedPreferences finalPreferences = preferences;
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finalPreferences.edit().putString("pref_text_color", colors.get(position).getColorValue()).apply();
                dismiss();
            }
        });*/
        addTimesButton();
    }

    private void addTimesButton() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        for (int i = 0; i < 24; i++) {
            if (i%2!=0){
            final CompoundButton timeButton = (CompoundButton) layoutInflater.inflate(
                    R.layout.alarm_repeat_checkbox, mLRepeatTimes, false);
            timeButton.setText(makeTextForTimesPerDay(i));
            mLRepeatTimes.addView(timeButton);
            mTimesButtons[i] = timeButton;
            }else {
                final CompoundButton timeButton = (CompoundButton) layoutInflater.inflate(
                        R.layout.alarm_repeat_checkbox, mRRepeatTimes, false);
                timeButton.setText(makeTextForTimesPerDay(i));
                mRRepeatTimes.addView(timeButton);
                mTimesButtons[i] = timeButton;
            }
        }
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
        mLRepeatTimes = (LinearLayout) findViewById(R.id.l_alarm_repeat_times);
        mRRepeatTimes = (LinearLayout) findViewById(R.id.r_alarm_repeat_times);
    }

    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }

    public void setNoOnclickListener(onNoOnclickListener onNoOnclickListener) {
        this.noOnclickListener = onNoOnclickListener;
    }


    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }

    public String makeTextForTimesPerDay(int i){

        return "hehada";
    }
}
