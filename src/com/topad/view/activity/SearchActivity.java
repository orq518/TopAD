package com.topad.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topad.R;
import com.topad.util.LogUtil;
import com.topad.view.customviews.TitleView;

/**
 * 主界面
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener{

    /** title布局 **/
    private TitleView mTitle;
    LinearLayout layout_voice,layout_keyboard;
    int searchType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutById() {
        return R.layout.activity_search;
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
        layout_voice= (LinearLayout) findViewById(R.id.layout_voice);
        layout_keyboard= (LinearLayout) findViewById(R.id.layout_keyboard);
        searchType=getIntent().getIntExtra("searchtype",0);
        LogUtil.d("ouou","searchType:"+searchType);

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
            case R.id.ic_voice:
                layout_voice.setVisibility(View.GONE);
                layout_keyboard.setVisibility(View.VISIBLE);
                break;
            case R.id.ic_keyboard://广播
                layout_keyboard.setVisibility(View.GONE);
                layout_voice.setVisibility(View.VISIBLE);
                break;
//            case R.id.newspaper_layout://报纸
//                break;
//            case R.id.outdoor_layout://户外
//                break;
//            case R.id.magazine_layout://杂志
//                break;
//            case R.id.net_layout://网络
//                break;
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
