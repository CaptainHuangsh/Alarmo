package com.example.testtest.provider;

import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by owenh on 2016/9/9.
 */

public class ViewPagerAdapter extends android.support.v4.view.PagerAdapter {
    List<View> viewList;

    public ViewPagerAdapter(List<View> lists){
        viewList = lists;
    }

    //get size
    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //destroy Item
    @Override
    public void destroyItem(View view, int position, Object object  ){
        ((ViewPager) view).removeView(viewList.get(position));
    }

    //shilihua Item
    @Override
    public Object instantiateItem(View view, int postion){
        ((ViewPager) view).addView(viewList.get(postion),0);
        return viewList.get(postion);
    }
}
