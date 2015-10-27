package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topad.R;
import com.topad.util.SystemBarTintManager;
import com.topad.view.customviews.TitleView;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String LTAG = MainActivity.class.getSimpleName();
    /** 上下文 **/
    private Context mContext;
    /**
     * title布局
     **/
    private TitleView mTitle;
    /**
     * 我的媒体
     */
    private ImageView mMyMedia;
    /**
     * 发布需求
     */
    private ImageView mReleaseDemand;
    /**
     * 我要抢单
     */
    private ImageView mGrabSingle;
    /**
     * 广告创意
     */
    private LinearLayout mAdvertisingCreativEe;
    /**
     * 营销策略
     */
    private LinearLayout mMarketingStrategy;
    /**
     * 影视广告
     */
    private LinearLayout mTVC;
    /**
     * 动漫创作
     */
    private LinearLayout mAnimeCreate;

    /**
     * 沉浸式状态栏
     */
    private SystemBarTintManager mTintManager;

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
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        applySelectedColor();

        // 顶部布局
        mTitle = (TitleView) findViewById(R.id.title);
        mMyMedia = (ImageView) findViewById(R.id.my_media);
        mReleaseDemand = (ImageView) findViewById(R.id.release_demand);
        mGrabSingle = (ImageView) findViewById(R.id.grab_single);
        mAdvertisingCreativEe = (LinearLayout) findViewById(R.id.advertising_creative_layout);
        mMarketingStrategy = (LinearLayout) findViewById(R.id.marketing_strategy_layout);
        mTVC = (LinearLayout) findViewById(R.id.tvc_layout);
        mAnimeCreate = (LinearLayout) findViewById(R.id.anime_create_layout);

        // 设置顶部布局
        mTitle.setTitle(getString(R.string.main_title));
        mTitle.setLeftVisiable(false);

        mMyMedia.setOnClickListener(this);
        mReleaseDemand.setOnClickListener(this);
        mGrabSingle.setOnClickListener(this);
        mAdvertisingCreativEe.setOnClickListener(this);
        mMarketingStrategy.setOnClickListener(this);
        mTVC.setOnClickListener(this);
        mAnimeCreate.setOnClickListener(this);

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
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_layout://电视
                intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("searchtype",0);
                startActivity(intent);
                break;

            case R.id.broadcast_layout://广播
                intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("searchtype",1);
                startActivity(intent);
                break;

            case R.id.newspaper_layout://报纸
                intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("searchtype",2);
                startActivity(intent);
                break;

            case R.id.outdoor_layout://户外
                intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("searchtype",3);
                startActivity(intent);
                break;

            case R.id.magazine_layout://杂志
                intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("searchtype",4);
                startActivity(intent);
                break;

            case R.id.net_layout://网络
                intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("searchtype",5);
                startActivity(intent);
                break;

            case R.id.advertising_creative_layout://广告创意
                intent = new Intent(MainActivity.this, AdvertisingServiceActivity.class);
                intent.putExtra("category", "1");
                startActivity(intent);
                break;

            case R.id.marketing_strategy_layout://营销策略
                intent = new Intent(MainActivity.this, AdvertisingServiceActivity.class);
                intent.putExtra("category", "2");
                startActivity(intent);
                break;

            case R.id.tvc_layout://影视广告
                intent = new Intent(MainActivity.this, AdvertisingServiceActivity.class);
                intent.putExtra("category", "3");
                startActivity(intent);
                break;

            case R.id.anime_create_layout://动漫创作
                intent = new Intent(MainActivity.this, AdvertisingServiceActivity.class);
                intent.putExtra("category", "4");
                startActivity(intent);
                break;

            case R.id.my_media://我的媒体
                break;

            case R.id.release_demand://发布需求
                intent = new Intent(MainActivity.this, ShareNeedsActivity.class);
                startActivity(intent);
                break;

            case R.id.grab_single://我要抢单
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
     * 顶部布局--左按钮事件监听
     */
    public class TitleLeftOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        }

    }

    private void applySelectedColor() {
        int color = Color.argb(0, Color.red(0), Color.green(0), Color.blue(0));
        mTintManager.setTintColor(color);
    }

}
