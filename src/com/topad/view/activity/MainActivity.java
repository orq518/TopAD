package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topad.R;
import com.topad.TopADApplication;
import com.topad.util.ActivityCollector;
import com.topad.util.LogUtil;
import com.topad.util.SystemBarTintManager;
import com.topad.util.Utils;
import com.topad.view.customviews.TabWidgetLayout;
import com.topad.view.customviews.TitleView;
import com.topad.view.fragment.BaseFragment;
import com.topad.view.fragment.MyMediaFragment;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{

    /** title布局 **/
    private TitleView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutById() {
        return R.layout.activity_home;
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
        mTitle.setRightTextVBackgroud(getResources().getDrawable(R.drawable.app_icon));
        mTitle.setLeftClickListener(new TitleLeftOnClickListener());

        setBottomLayout();

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
//            case R.id.version_update:{
//                break;
//            default:{
//                break;
        }
    }

    @Override
    public void onBack() {
        finish();
    }

    /**
     * 设置底部布局
     */
    public void setBottomLayout() {
        RelativeLayout my_media = (RelativeLayout) findViewById(R.id.my_media);
        TextView item_key1 = (TextView) my_media.findViewById(R.id.name);
        item_key1.setText("我的媒体");

        RelativeLayout release_demand = (RelativeLayout) findViewById(R.id.release_demand);
        TextView item_key2 = (TextView) release_demand.findViewById(R.id.name);
        item_key2.setText("发布需求");

        RelativeLayout grab_single = (RelativeLayout) findViewById(R.id.grab_single);
        TextView item_key3 = (TextView) grab_single.findViewById(R.id.name);
        item_key3.setText("我要抢单");
    }

    /**
     * 顶部布局--左按钮事件监听
     */
    public class TitleLeftOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        }

    }

}
