package com.example.owenh.alarmo.util;

import android.media.MediaPlayer;
import android.os.Environment;

import java.io.File;

import static android.R.attr.start;

/**
 * Created by owenh on 2016/8/5.
 */

public class RingUtil {

    private MediaPlayer mediaPlayer = new MediaPlayer();

    public void Ring() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(),
                    "ring0.mp3");
            mediaPlayer.setDataSource((file.getPath()));//指定音频文件
            mediaPlayer.prepare();//让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ringStartOrStop() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        } else {
            mediaPlayer.reset();
        }
    }
}
