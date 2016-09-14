package com.example.datatest.activity.provider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.datatest.R;

import java.util.List;

/**
 * Created by owenh on 2016/8/29.
 */

public class TestAdapter extends ArrayAdapter<TestI> {

    private int resourceId;

    public TestAdapter(Context context, int TestResourceId, List<TestI> objects) {
        super(context, TestResourceId, objects);
        resourceId = TestResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TestI testI = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView name = (TextView)view.findViewById(R.id.item_name);
        TextView price = (TextView)view.findViewById(R.id.item_price);
        TextView date = (TextView)view.findViewById(R.id.item_date);
        TextView page = (TextView)view.findViewById(R.id.item_page);
        name.setText(testI.getName());
        price.setText(testI.getPrice()+"");
        date.setText(testI.getDate());
        page.setText(testI.getPage()+"");
        return view;
    }
}
