package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.topad.R;
import com.topad.util.LogUtil;
import com.topad.view.customviews.MyGridView;
import com.topad.view.customviews.TitleView;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ${todo}<我有媒体>
 *
 * @author lht
 * @data: on 15/10/30 14:10
 */
public class MyMediaActivity extends BaseActivity implements View.OnClickListener {
    private static final String LTAG = MyMediaActivity.class.getSimpleName();
    /** 上下文 **/
    private Context mContext;
    /** 顶部布局 **/
    private TitleView mTitleView;
    /** 电视 **/
    private LinearLayout mLayTV;
    /** 广播 **/
    private LinearLayout mLayRadio;
    /** 报纸 **/
    private LinearLayout mLayOutdoor;
    /** 户外 **/
    private LinearLayout mLayNewspaper;
    /** 杂志 **/
    private LinearLayout mLayMgz;
    /** 网络 **/
    private LinearLayout mLayWeb;

    @Override
    public int setLayoutById() {
        mContext = this;
        return R.layout.activity_my_media;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    @Override
    public void initViews() {
        mTitleView = (TitleView) findViewById(R.id.title);
        mLayTV = (LinearLayout) findViewById(R.id.media_icon_tv);
        mLayRadio = (LinearLayout) findViewById(R.id.media_icon_radio);
        mLayOutdoor = (LinearLayout) findViewById(R.id.media_icon_newspaper);
        mLayNewspaper = (LinearLayout) findViewById(R.id.media_icon_outdoor);
        mLayMgz = (LinearLayout) findViewById(R.id.media_icon_mgz);
        mLayWeb = (LinearLayout) findViewById(R.id.media_icon_web);

        mLayTV.setOnClickListener(this);
        mLayRadio.setOnClickListener(this);
        mLayOutdoor.setOnClickListener(this);
        mLayNewspaper.setOnClickListener(this);
        mLayMgz.setOnClickListener(this);
        mLayWeb.setOnClickListener(this);
    }

    @Override
    public void initData() {

        mTitleView.setTitle("我有媒体");
        mTitleView.setLeftClickListener(new TitleLeftOnClickListener());

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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        switch (v.getId()) {
            // 电视
            case R.id.media_icon_tv:
                intent = new Intent(MyMediaActivity.this, MediaReleaseActivity.class);
                intent.putExtra("category","1");
                startActivity(intent);
                break;
            // 广播
            case R.id.media_icon_radio:
                intent = new Intent(MyMediaActivity.this, MediaReleaseActivity.class);
                intent.putExtra("category","2");
                startActivity(intent);
                break;
            // 报纸
            case R.id.media_icon_newspaper:
                intent = new Intent(MyMediaActivity.this, MediaReleaseActivity.class);
                intent.putExtra("category","3");
                startActivity(intent);
                break;
            // 户外
            case R.id.media_icon_outdoor:
                intent = new Intent(MyMediaActivity.this, MediaReleaseActivity.class);
                intent.putExtra("category","4");
                startActivity(intent);
                break;
            // 杂志
            case R.id.media_icon_mgz:
                intent = new Intent(MyMediaActivity.this, MediaReleaseActivity.class);
                intent.putExtra("category","5");
                startActivity(intent);
                break;
            // 网络
            case R.id.media_icon_web:
                intent = new Intent(MyMediaActivity.this, MediaReleaseActivity.class);
                intent.putExtra("category","6");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
