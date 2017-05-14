package com.example.owenh.alarmo.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.domain.AColor;

import java.util.HashMap;
import java.util.List;

/**
 * Created by owen on 2017/5/10.
 */

public class ColorListAdapter extends ArrayAdapter<AColor> {

    private int resourceId;
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

    public ColorListAdapter(Context context, int resourceId, List<AColor> colors) {
        super(context, resourceId, colors);
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AColor color = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.colorImg = (ImageView) view.findViewById(R.id.items_take_color_value);
            viewHolder.colorText = (TextView) view.findViewById(R.id.item_take_color_string);
            view.setTag(viewHolder);//将viewHolder存储在view中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//重新获取viewHolder
        }
        final RadioButton radio=(RadioButton) view.findViewById(R.id.color_radio);
        final SharedPreferences finalPreferences = preferences;
        viewHolder.rb_state = radio;
        viewHolder.rb_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalPreferences.edit().putString("pref_text_color", getItem(position).getColorValue()).apply();
                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                states.put(String.valueOf(position), radio.isChecked());
                ColorListAdapter.this.notifyDataSetChanged();
            }
        });

        boolean res = false;
        if (states.get(String.valueOf(position)) == null
                || states.get(String.valueOf(position)) == false) {
            res = false;
            states.put(String.valueOf(position), false);
        } else
            res = true;

        viewHolder.rb_state.setChecked(res);

        viewHolder.colorImg.setBackgroundColor(Color.parseColor(color.getColorValue()));
        viewHolder.colorText.setText(color.getColorString());
        return view;
    }

    class ViewHolder {
        ImageView colorImg;
        TextView colorText;
        RadioButton rb_state;
    }
}
