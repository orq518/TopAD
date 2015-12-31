package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.topad.R;
import com.topad.util.LogUtil;
import com.topad.util.SystemBarTintManager;
import com.topad.view.customviews.TitleView;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    private static final String LTAG = MainActivity.class.getSimpleName();
    /** 上下文 **/
    private Context mContext;
    /** title布局 **/
    private TitleView mTitle;
    /** 我有媒体 **/
    private ImageView mMyMedia;
    /** 发布需求 **/
    private ImageView mReleaseDemand;
    /** 我要抢单 **/
    private ImageView mGrabSingle;
    /** 广告创意 **/
    private LinearLayout mAdvertisingCreativEe;
    /** 营销策略 **/
    private LinearLayout mMarketingStrategy;
    /** 影视广告 **/
    private LinearLayout mTVC;
    /** 动漫创作 **/
    private LinearLayout mAnimeCreate;
    /** 沉浸式状态栏 **/
    private SystemBarTintManager mTintManager;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    LinearLayout left_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutById() {
        return R.layout.activity_main_drawer;
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
        mTitle.setLeftVisiable(true);
        mTitle.setLeftIcon(R.drawable.left_media);
        mTitle.setLeftClickListener(new TitleLeftOnClickListener());
        mMyMedia.setOnClickListener(this);
        mReleaseDemand.setOnClickListener(this);
        mGrabSingle.setOnClickListener(this);
        mAdvertisingCreativEe.setOnClickListener(this);
        mMarketingStrategy.setOnClickListener(this);
        mTVC.setOnClickListener(this);
        mAnimeCreate.setOnClickListener(this);

        left_drawer = (LinearLayout) findViewById(R.id.left_drawer);
        // Set the list's click listener
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.left_media, R.string.app_name, R.string.app_name) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.syncState();
        initLeftMenu();
//
//        // Set the drawer toggle as the DrawerListener
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void initData() {

    }

    public void initLeftMenu() {
        findViewById(R.id.csmm).setOnTouchListener(this);
        findViewById(R.id.wszl).setOnTouchListener(this);
        findViewById(R.id.gsrz).setOnTouchListener(this);
        findViewById(R.id.cpsj).setOnTouchListener(this);
        findViewById(R.id.wdqd).setOnTouchListener(this);
        findViewById(R.id.wdxq).setOnTouchListener(this);
        findViewById(R.id.fbmt).setOnTouchListener(this);
        findViewById(R.id.wdqb).setOnTouchListener(this);
        findViewById(R.id.xtxx).setOnTouchListener(this);
        findViewById(R.id.quit).setOnTouchListener(this);
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
                intent.putExtra("searchtype", 0);
                startActivity(intent);
                break;

            case R.id.broadcast_layout://广播
                intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("searchtype", 1);
                startActivity(intent);
                break;

            case R.id.newspaper_layout://报纸
                intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("searchtype", 2);
                startActivity(intent);
                break;

            case R.id.outdoor_layout://户外
                intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("searchtype", 3);
                startActivity(intent);
                break;

            case R.id.magazine_layout://杂志
                intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("searchtype", 4);
                startActivity(intent);
                break;

            case R.id.net_layout://网络
                intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("searchtype", 5);
                startActivity(intent);
                break;

            case R.id.advertising_creative_layout://广告创意
                intent = new Intent(MainActivity.this, ADSListActivity.class);
                intent.putExtra("category", "1");
                startActivity(intent);
                break;

            case R.id.marketing_strategy_layout://营销策略
                intent = new Intent(MainActivity.this, ADSListActivity.class);
                intent.putExtra("category", "2");
                startActivity(intent);
                break;

            case R.id.tvc_layout://影视广告
                intent = new Intent(MainActivity.this, ADSListActivity.class);
                intent.putExtra("category", "3");
                startActivity(intent);
                break;

            case R.id.anime_create_layout://动漫创作
                intent = new Intent(MainActivity.this, ADSListActivity.class);
                intent.putExtra("category", "4");
                startActivity(intent);
                break;

            case R.id.my_media://我有媒体
                intent = new Intent(MainActivity.this, MyMediaActivity.class);
                startActivity(intent);
                break;

            case R.id.release_demand://发布需求
                intent = new Intent(MainActivity.this, ShareNeedsActivity.class);
                startActivity(intent);
                break;

            case R.id.grab_single://我要抢单
                intent = new Intent(MainActivity.this, GrabSingleActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public void onBack() {
        finish();
    }

    float moveXY;
    float lastX = 0.0f;
    float lastY = 0.0f;
    boolean isNeedUp;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isNeedUp = true;
                lastX = event.getX();
                lastY = event.getY();
                leftMenuTouch(v, true);
                break;
            case MotionEvent.ACTION_MOVE:
                float cx = event.getX();
                float cy = event.getY();
                moveXY = Math.abs(cx - lastX) + Math.abs(cy - lastY);
                LogUtil.d("moveXY:"+moveXY);
                if (moveXY > 30) {
                    leftMenuTouch(v, false);
                    isNeedUp = false;
                }

                break;
            case MotionEvent.ACTION_UP:
                if (isNeedUp) {
                    leftMenuTouch(v, false);
                }
                break;
        }
        return false;
    }

    public void leftMenuTouch(View v, boolean isPressed) {
        if (isPressed) {
            switch (v.getId()) {
                case R.id.csmm:
                    ((ImageView) (v.findViewById(R.id.im_csmm))).setImageResource(R.drawable.left_rewrite_blue);
                    ((TextView) (v.findViewById(R.id.tv_csmm))).setTextColor(getResources().getColor(R.color.act_home_tab_blue_normal));
                    break;
                case R.id.wszl:
                    ((ImageView) (v.findViewById(R.id.im_wszl))).setImageResource(R.drawable.left_wanshan_blue);
                    ((TextView) (v.findViewById(R.id.tv_wszl))).setTextColor(getResources().getColor(R.color.act_home_tab_blue_normal));
                    break;
                case R.id.gsrz:
                    ((ImageView) (v.findViewById(R.id.im_gsrz))).setImageResource(R.drawable.left_renzheng_blue);
                    ((TextView) (v.findViewById(R.id.tv_gsrz))).setTextColor(getResources().getColor(R.color.act_home_tab_blue_normal));
                    break;
                case R.id.cpsj:
                    ((ImageView) (v.findViewById(R.id.im_cpsj))).setImageResource(R.drawable.left_products_blue);
                    ((TextView) (v.findViewById(R.id.tv_cpsj))).setTextColor(getResources().getColor(R.color.act_home_tab_blue_normal));
                    break;
                case R.id.wdqd:
                    ((ImageView) (v.findViewById(R.id.im_wdqd))).setImageResource(R.drawable.left_qiangdan_blue);
                    ((TextView) (v.findViewById(R.id.tv_wdqd))).setTextColor(getResources().getColor(R.color.act_home_tab_blue_normal));
                    break;
                case R.id.wdxq:
                    ((ImageView) (v.findViewById(R.id.im_wdxq))).setImageResource(R.drawable.left_demands_blue);
                    ((TextView) (v.findViewById(R.id.tv_wdxq))).setTextColor(getResources().getColor(R.color.act_home_tab_blue_normal));
                    break;
                case R.id.fbmt:
                    ((ImageView) (v.findViewById(R.id.im_fbmt))).setImageResource(R.drawable.left_media_blue);
                    ((TextView) (v.findViewById(R.id.tv_fbmt))).setTextColor(getResources().getColor(R.color.act_home_tab_blue_normal));
                    break;
                case R.id.wdqb:
                    ((ImageView) (v.findViewById(R.id.im_wdqb))).setImageResource(R.drawable.left_wallet_blue);
                    ((TextView) (v.findViewById(R.id.tv_wdqb))).setTextColor(getResources().getColor(R.color.act_home_tab_blue_normal));
                    break;
                case R.id.xtxx:
                    ((ImageView) (v.findViewById(R.id.im_xtxx))).setImageResource(R.drawable.left_message_blue);
                    ((TextView) (v.findViewById(R.id.tv_xtxx))).setTextColor(getResources().getColor(R.color.act_home_tab_blue_normal));
                    break;
                case R.id.quit:
                    ((ImageView) (v.findViewById(R.id.im_quit))).setImageResource(R.drawable.left_zhux_blue);
                    ((TextView) (v.findViewById(R.id.tv_quit))).setTextColor(getResources().getColor(R.color.act_home_tab_blue_normal));
                    break;

            }
        } else {
            switch (v.getId()) {
                case R.id.csmm:
                    ((ImageView) (v.findViewById(R.id.im_csmm))).setImageResource(R.drawable.left_rewrite);
                    ((TextView) (v.findViewById(R.id.tv_csmm))).setTextColor(getResources().getColor(R.color.white));
                    break;
                case R.id.wszl:
                    ((ImageView) (v.findViewById(R.id.im_wszl))).setImageResource(R.drawable.left_wanshan);
                    ((TextView) (v.findViewById(R.id.tv_wszl))).setTextColor(getResources().getColor(R.color.white));
                    break;
                case R.id.gsrz:
                    ((ImageView) (v.findViewById(R.id.im_gsrz))).setImageResource(R.drawable.left_renzheng);
                    ((TextView) (v.findViewById(R.id.tv_gsrz))).setTextColor(getResources().getColor(R.color.white));
                    break;
                case R.id.cpsj:
                    ((ImageView) (v.findViewById(R.id.im_cpsj))).setImageResource(R.drawable.left_products);
                    ((TextView) (v.findViewById(R.id.tv_cpsj))).setTextColor(getResources().getColor(R.color.white));
                    break;
                case R.id.wdqd:
                    ((ImageView) (v.findViewById(R.id.im_wdqd))).setImageResource(R.drawable.left_qiangdan);
                    ((TextView) (v.findViewById(R.id.tv_wdqd))).setTextColor(getResources().getColor(R.color.white));
                    break;
                case R.id.wdxq:
                    ((ImageView) (v.findViewById(R.id.im_wdxq))).setImageResource(R.drawable.left_demands);
                    ((TextView) (v.findViewById(R.id.tv_wdxq))).setTextColor(getResources().getColor(R.color.white));
                    break;
                case R.id.fbmt:
                    ((ImageView) (v.findViewById(R.id.im_fbmt))).setImageResource(R.drawable.left_media);
                    ((TextView) (v.findViewById(R.id.tv_fbmt))).setTextColor(getResources().getColor(R.color.white));
                    break;
                case R.id.wdqb:
                    ((ImageView) (v.findViewById(R.id.im_wdqb))).setImageResource(R.drawable.left_wallet);
                    ((TextView) (v.findViewById(R.id.tv_wdqb))).setTextColor(getResources().getColor(R.color.white));
                    break;
                case R.id.xtxx:
                    ((ImageView) (v.findViewById(R.id.im_xtxx))).setImageResource(R.drawable.left_message);
                    ((TextView) (v.findViewById(R.id.tv_xtxx))).setTextColor(getResources().getColor(R.color.white));
                    break;
                case R.id.quit:
                    ((ImageView) (v.findViewById(R.id.im_quit))).setImageResource(R.drawable.left_zhux);
                    ((TextView) (v.findViewById(R.id.tv_quit))).setTextColor(getResources().getColor(R.color.white));
                    break;
            }
            leftMenuClick(v);
        }

    }

    public void leftMenuClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.csmm://重设密码
                intent = new Intent(MainActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.wszl://完善资料
                break;
            case R.id.gsrz://公司认证
                break;
            case R.id.cpsj://我的服务产品
                break;
            case R.id.wdqd://我的抢单
                break;
            case R.id.wdxq://我的需求
                break;
            case R.id.fbmt://我发布的媒体
                intent = new Intent(MainActivity.this, ADSListActivity.class);
                intent.putExtra("category", "1");//广告创意1营销策略2影视广告3动漫创作4
                startActivity(intent);
                break;
            case R.id.wdqb://我的钱包
                intent = new Intent(MainActivity.this, MyWalletActivity.class);
                startActivity(intent);
                break;
            case R.id.xtxx://系统消息
                intent = new Intent(MainActivity.this, SystemNewsActivity.class);
                startActivity(intent);
                break;
            case R.id.quit://注销
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

        }
    }

    /**
     * 顶部布局--左按钮事件监听
     */
    public class TitleLeftOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mDrawerLayout.openDrawer(left_drawer);
        }

    }

    private void applySelectedColor() {
        int color = Color.argb(0, Color.red(0), Color.green(0), Color.blue(0));
        mTintManager.setTintColor(color);
    }

}
