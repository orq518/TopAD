package com.topad.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * The author 欧瑞强 on 2015/10/29.
 * todo
 */
public class RecordMediaPlayer {
    String pathString;
    static RecordMediaPlayer mRecordMediaPlayer = null;
    MediaPlayer mediaPlayer = new MediaPlayer();
    public static RecordMediaPlayer getInstance() {
        if (mRecordMediaPlayer == null) {
            mRecordMediaPlayer = new RecordMediaPlayer();
        }
        return mRecordMediaPlayer;
    }



    public void play(String path) {
        if(mediaPlayer.isPlaying()){
            stop();
        }
        if(path.equals(pathString)){
            return;
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (Exception e) {
        }
        mediaPlayer.start();
        pathString=path;
    }

    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    public void deleteFile(String path) {
        File file = new File(Environment
                .getExternalStorageDirectory(), path);
        if (file.exists()) {
            file.delete();
        }
    }
}
