package com.topad.view.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.topad.util.ActivityCollector;

/**
 * orq--kb
 */
public abstract class BaseActivity extends FragmentActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        ActivityCollector.addActivity(this);
        int layoutId = setLayoutById();
        if (layoutId != 0) {
            setContentView(layoutId);
        } else {
            setContentView(setLayoutByView());
        }


        initViews();

        initPresenter();

        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 通过layout的id
     *
     * @return
     */
    public abstract int setLayoutById();

    /**
     * 通过layout的view
     *
     * @return
     */
    public abstract View setLayoutByView();

    /**
     * 初始化view
     */
    public abstract void initViews();

    /**
     * 初始化Presenter
     */
    public abstract void initPresenter();

    /**
     * 初始化数据
     */
    public abstract void initData();

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 处理返回事件
     */
    public void onBack() {
    }
}
