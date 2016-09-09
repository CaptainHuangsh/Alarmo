package com.example.testtest.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testtest.R;
import com.example.testtest.provider.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener{

    private ViewPager mViewPager;
    private List<View> mLists;
    private ViewPagerAdapter mViewPagerAdapter;
    private Bitmap mBitmap;
    private int offSet;
    private int currentItem;
    private Matrix matrix;
    private int bmWidth;
    private Animation animation;

    private ImageView mCursou;
    private TextView mTPage1;
    private TextView mTPage2;
    private TextView mTPage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        init();
        setListener();

    }

    public void init(){
        mLists = new ArrayList<View>();
        matrix = new Matrix();
        mLists.add(getLayoutInflater().inflate((R.layout.page1), null));
        mLists.add(getLayoutInflater().inflate((R.layout.page2), null));
        mLists.add(getLayoutInflater().inflate((R.layout.page3), null));
        initeCursor();
        mViewPagerAdapter = new ViewPagerAdapter(mLists);

        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // 当滑动式，顶部的imageView是通过animation缓慢的滑动
            @Override
            public void onPageSelected(int arg0) {
                switch (arg0) {
                    case 0:
                        if (currentItem == 1) {
                            animation = new TranslateAnimation(
                                    offSet * 2 + bmWidth, 0, 0, 0);
                        } else if (currentItem == 2) {
                            animation = new TranslateAnimation(offSet * 4 + 2
                                    * bmWidth, 0, 0, 0);
                        }
                        break;
                    case 1:
                        if (currentItem == 0) {
                            animation = new TranslateAnimation(0, offSet * 2
                                    + bmWidth, 0, 0);
                        } else if (currentItem == 2) {
                            animation = new TranslateAnimation(4 * offSet + 2
                                    * bmWidth, offSet * 2 + bmWidth, 0, 0);
                        }
                        break;
                    case 2:
                        if (currentItem == 0) {
                            animation = new TranslateAnimation(0, 4 * offSet + 2
                                    * bmWidth, 0, 0);
                        } else if (currentItem == 1) {
                            animation = new TranslateAnimation(
                                    offSet * 2 + bmWidth, 4 * offSet + 2 * bmWidth,
                                    0, 0);
                        }
                }
                currentItem = arg0;
                animation.setDuration(150); // 光标滑动速度
                animation.setFillAfter(true);
                mCursou.startAnimation(animation);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    private void initeCursor() {
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.cursor);
        bmWidth = mBitmap.getWidth();

        DisplayMetrics dm;
        dm = getResources().getDisplayMetrics();

        offSet = (dm.widthPixels - 3 * bmWidth) / 6;
        matrix.setTranslate(offSet, 0);
        mCursou.setImageMatrix(matrix); // 需要imageView的scaleType为matrix
        currentItem = 0;
    }

    public void findView(){
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCursou = (ImageView) findViewById(R.id.cursor);
        mTPage1 = (TextView) findViewById(R.id.top_page1);
        mTPage2 = (TextView) findViewById(R.id.top_page2);
        mTPage3 = (TextView) findViewById(R.id.top_page3);


    }

    public void setListener(){
        mTPage1.setOnClickListener(this);
        mTPage2.setOnClickListener(this);
        mTPage3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.top_page1:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.top_page2:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.top_page3:
                mViewPager.setCurrentItem(2);
                break;
        }

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/
}
