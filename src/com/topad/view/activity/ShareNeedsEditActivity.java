package com.topad.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.topad.R;
import com.topad.amap.PoiKeywordSearchActivity;
import com.topad.util.SystemBarTintManager;
import com.topad.view.customviews.TitleView;

/**
 * 发布需求编辑界面
 */
public class ShareNeedsEditActivity extends BaseActivity implements View.OnClickListener{

    /** title布局 **/
    private TitleView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutById() {
        return R.layout.activity_needs_edit;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }
    private void applySelectedColor() {
        int color = Color.argb(0, Color.red(0), Color.green(0), Color.blue(0));
        mTintManager.setTintColor(color);
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
        mTitle.setTitle(getString(R.string.main_title));
        mTitle.setLeftClickListener(new TitleLeftOnClickListener());
        mTitle.setRightImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ShareNeedsEditActivity.this,  PoiKeywordSearchActivity.class);
                startActivity(intent);
            }
        }, R.drawable.bt_search);
    }

    @Override
    public void initData() {

    }
    @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ShareNeedsEditActivity.this, NeedsListActivity.class);
        super.onClick(v);
        switch (v.getId()) {
//            case R.id.tab01:
//                break;
            default:
                break;
        }
        startActivity(intent);
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
