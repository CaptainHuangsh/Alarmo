package com.example.owenh.alarmo.common;

/**
 * Created by owen on 2017/5/2.
 * 用于检测双击事件时间
 */

public class DoubleClick {
    /**
     * 双击退出检测, 阈值 1000ms
     */
    private static long mLastClick = 0L;
    private static final int THRESHOLD = 2000;//1000ms

    public static boolean check(){
        long now = System.currentTimeMillis();
        boolean b =  now - mLastClick < THRESHOLD;
        mLastClick = now;
        return b;
    }

}
