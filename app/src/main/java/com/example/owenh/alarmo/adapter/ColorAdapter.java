package com.example.owenh.alarmo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.domain.AColor;

import java.util.List;

/**
 * Created by owen on 2017/5/10.
 */

public class ColorAdapter extends ArrayAdapter<AColor> {

    private int resourceId;

    public ColorAdapter(Context context, int resourceId, List<AColor> colors) {
        super(context,resourceId,colors);
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AColor color = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.colorImg = (ImageView)view.findViewById(R.id.items_take_color_value);;
            viewHolder.colorText = (TextView)view.findViewById(R.id.item_take_color_string);;
            view.setTag(viewHolder);//将viewHolder存储在view中
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//重新获取viewHolder
        }
        viewHolder.colorImg.setBackgroundColor(Color.parseColor(color.getColorValue()));
        viewHolder.colorText.setText(color.getColorString());
        /*View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView colorString = (TextView)view.findViewById(R.id.item_take_color_string);
        ImageView colorValue = (ImageView)view.findViewById(R.id.items_take_color_value);
        colorString.setText(color.getColorString());
        colorValue.setBackgroundColor(Color.parseColor(color.getColorValue()));*/
        return view;
    }

    class ViewHolder {
        ImageView colorImg;
        TextView colorText;
    }
}
