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
import android.view.KeyEvent;
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
import com.topad.util.SystemBarTintManager;
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
    /** 沉浸式状态栏 **/
    private SystemBarTintManager mTintManager;
    @Override
    public void initViews() {
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        applySelectedColor();
        // 顶部布局
        mTitle = (TitleView) findViewById(R.id.title);
        // 设置顶部布局
        mTitle.setTitle("发布需求");
        mTitle.setLeftClickListener(new TitleLeftOnClickListener());

    }
    private void applySelectedColor() {
        int color = Color.argb(0, Color.red(0), Color.green(0), Color.blue(0));
        mTintManager.setTintColor(color);
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
        Intent intent = new Intent(ShareNeedsActivity.this, NeedsListActivity.class);
        TextView tv= (TextView) v;
        intent.putExtra("title",tv.getText().toString());
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tab01:
                intent.putExtra("type",0);
                break;
            case R.id.tab02:
                intent.putExtra("type",1);
                break;
            case R.id.tab03:
                intent.putExtra("type",2);
                break;
            case R.id.tab04:
                intent.putExtra("type",3);
                break;
            case R.id.tab05:
                intent.putExtra("type",4);
                break;
            case R.id.tab06:
                intent.putExtra("type",5);
                break;
            case R.id.tab07:
                intent.putExtra("type",6);
                break;
            case R.id.tab08:
                intent.putExtra("type",7);
                break;
            case R.id.tab09:
                intent.putExtra("type",8);
                break;
            case R.id.tab10:
                intent.putExtra("type",9);
                break;
            case R.id.tab11:
                intent.putExtra("type",10);
                break;
            case R.id.tab12:
                intent.putExtra("type",11);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onBack() {
        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBack();
        }
        return super.onKeyDown(keyCode, event);
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
