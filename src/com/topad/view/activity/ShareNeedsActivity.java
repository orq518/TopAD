package com.topad.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.topad.R;
import com.topad.util.AudioRecorder;
import com.topad.util.LogUtil;
import com.topad.view.customviews.TitleView;

import java.io.File;
import java.io.IOException;

/**
 * 发布需求主界面
 */
public class ShareNeedsActivity extends BaseActivity implements View.OnClickListener{

    /** title布局 **/
    private TitleView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutById() {
        return R.layout.activity_release_needs;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    @Override
    public void initViews() {
        // 顶部布局
        mTitle = (TitleView) findViewById(R.id.title);
        // 设置顶部布局
        mTitle.setTitle(getString(R.string.main_title));
        mTitle.setLeftClickListener(new TitleLeftOnClickListener());

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tab01:
                Intent intent = new Intent(ShareNeedsActivity.this, SearchListActivity.class);
                startActivity(intent);
                break;
            case R.id.tab02:
                break;
            case R.id.tab03:
                break;
            case R.id.tab04:
                break;
            case R.id.tab05:
                break;
            case R.id.tab06:
                break;
            case R.id.tab07:
                break;
            case R.id.tab08:
                break;
            case R.id.tab09:
                break;
            case R.id.tab10:
                break;
            case R.id.tab11:
                break;
            case R.id.tab12:
                break;
            default:
                break;
        }
    }

    @Override
    public void onBack() {
        finish();
    }

    /**
     * 设置底部布局
     */

    /**
     * 顶部布局--左按钮事件监听
     */
    public class TitleLeftOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            onBack();
        }

    }


}
