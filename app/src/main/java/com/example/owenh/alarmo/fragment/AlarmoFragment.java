package com.example.owenh.alarmo.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.activity.AlarmMain;
import com.example.owenh.alarmo.activity.WatchActivity;
import com.example.owenh.alarmo.services.RingService;
import com.example.owenh.alarmo.util.DBManager;
import com.example.owenh.alarmo.util.SPUtils;
import com.example.owenh.alarmo.util.T;

/**
 * Created by owen on 2017/6/4.
 */

public class AlarmoFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ImageButton mToWatch;
    private Switch mSwitch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_alarmo, container, false);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        setListener();
        init();
    }

    private void findViews(View v) {
        mToWatch = (ImageButton) v.findViewById(R.id.to_watch);
        mSwitch = (Switch) v.findViewById(R.id.on_off_service2);

    }

    public void setListener() {
        mSwitch.setOnClickListener(this);
        mToWatch.setOnClickListener(this);

    }

    public void init() {
        mSwitch.setChecked(RingService.isRingServiceSurvive);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.to_watch:
                Intent intent = new Intent(getActivity(), WatchActivity.class);
                startActivity(intent);
                break;
            case R.id.on_off_service2:
                Intent serviceIntent = new Intent(getActivity(), RingService.class);
                if (mSwitch.isChecked()) {
                    getActivity().startService(serviceIntent);
                    T.showShort(getActivity(), "打开整点报时");
                } else {
                    getActivity().stopService(serviceIntent);
                    T.showShort(getActivity(), "关闭整点报时");
                }
                break;
            default:
                break;
        }
    }
}
