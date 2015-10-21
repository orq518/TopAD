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

import com.topad.R;
import com.topad.TopADApplication;
import com.topad.util.ActivityCollector;
import com.topad.util.LogUtil;
import com.topad.util.SystemBarTintManager;
import com.topad.util.Utils;
import com.topad.view.customviews.TabWidgetLayout;
import com.topad.view.fragment.BaseFragment;
import com.topad.view.fragment.WalletFragment;


/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements
        TabWidgetLayout.OnTabSelectedListener {

    // 财富fragment索引值
    public static final int HOME_TAB_INDEX_0 = 0;
    // 生活fragment索引值
    public static final int HOME_TAB_INDEX_1 = 1;
    // 卡卷fragment索引值
    public static final int HOME_TAB_INDEX_2 = 2;
    // 我的fragment索引值
    public static final int HOME_TAB_INDEX_3 = 3;
    /**
     * 索引
     */
    private static int mIndex = HOME_TAB_INDEX_0;
    /**
     * 要跳转的index
     */
    static int preIndex;

    private TabWidgetLayout mTabWidget;

    private WalletFragment mHomeFragment0;
    private WalletFragment mHomeFragment1;
    private WalletFragment mHomeFragment2;
    private WalletFragment mHomeFragment3;
    private FragmentManager mFragmentManager;

    /** 设置系统bar **/
    private SystemBarTintManager mTintManager;

    public static void LaunchSelf(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void LaunchSelfWithNewTask(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        applySelectedColor();
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
        mFragmentManager = getSupportFragmentManager();
        TopADApplication.getSelf().setFragmentManager(mFragmentManager);
        mTabWidget = (TabWidgetLayout) findViewById(R.id.tab_widget);
        mTabWidget.setOnTabSelectedListener(this);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    Handler mHandler = new Handler() {
    };

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("mIndex:" + mIndex);
        onTabSelected(mIndex,mTabWidget.ONRESUME);
        mTabWidget.setTabsDisplay(this, mIndex);
//        mTabWidget.setIndicateDisplay(this, 3, true);
        if (mFragmentManager != null && mFragmentManager.getFragments() != null && mFragmentManager.getFragments().size() > 0) {
            setFragmentVerisiable((BaseFragment) mFragmentManager.findFragmentByTag(mIndex + ""));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setFragmentVerisiable(final BaseFragment fagment) {
        if(mHandler==null){
            mHandler = new Handler() {
            };
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fagment.setVisible(true);
            }
        }, 300);
    }

    @Override
    public void onTabSelected(int index,int type) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        switch (index) {
            case HOME_TAB_INDEX_0:
                preIndex = HOME_TAB_INDEX_0;
                hideFragments(transaction);
                if (null == mHomeFragment0) {
                    mHomeFragment0 = new WalletFragment();
                    transaction.add(R.id.center_layout, mHomeFragment0, "0");
                } else {
                    transaction.show(mHomeFragment0);
                }
                setFragmentVerisiable(mHomeFragment0);

                break;
            case HOME_TAB_INDEX_1:
                preIndex = HOME_TAB_INDEX_1;
                hideFragments(transaction);
                if (null == mHomeFragment1) {
                    mHomeFragment1 = new WalletFragment();
                    transaction.add(R.id.center_layout, mHomeFragment1, "1");
                } else {
                    transaction.show(mHomeFragment1);
                }
                setFragmentVerisiable(mHomeFragment1);
                break;


            case HOME_TAB_INDEX_2:
                preIndex = HOME_TAB_INDEX_2;

                hideFragments(transaction);
                if (null == mHomeFragment2) {
                    mHomeFragment2 = new WalletFragment();
                    transaction.add(R.id.center_layout, mHomeFragment2, "2");
                } else {
                    transaction.show(mHomeFragment2);
                }
                setFragmentVerisiable(mHomeFragment2);
                break;
            case HOME_TAB_INDEX_3:
                preIndex = HOME_TAB_INDEX_3;
                hideFragments(transaction);
                if (null == mHomeFragment3) {
                    mHomeFragment3 = new WalletFragment();
                    transaction.add(R.id.center_layout, mHomeFragment3, "3");
                } else {
                    transaction.show(mHomeFragment3);
                }
                setFragmentVerisiable(mHomeFragment3);
                break;
            default:
                break;
        }
        mIndex = index;
        transaction.commitAllowingStateLoss();
    }




    private void hideFragments(FragmentTransaction transaction) {
        if (null != mHomeFragment0) {
            transaction.hide(mHomeFragment0);
        }
        if (null != mHomeFragment1) {
            transaction.hide(mHomeFragment1);
        }
        if (null != mHomeFragment2) {
            transaction.hide(mHomeFragment2);
        }
        if (null != mHomeFragment3) {
            transaction.hide(mHomeFragment3);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 自己记录fragment的位置,防止activity被系统回收时，fragment错乱的问题
        // super.onSaveInstanceState(outState);
        outState.putInt("index", mIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // super.onRestoreInstanceState(savedInstanceState);
        mIndex = savedInstanceState.getInt("index");
    }

    long mBackTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (mIndex) {
            case HOME_TAB_INDEX_1:
                break;
        }
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (System.currentTimeMillis() - mBackTime < 1500) {
                ActivityCollector.finishAll();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }, 1000);
//                android.os.Process.killProcess(android.os.Process.myPid());
            } else {
                mBackTime = System.currentTimeMillis();
                Utils.showToast(this, "再按一次离开" + getResources().getString(R.string.app_name));
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (mIndex) {
            case HOME_TAB_INDEX_0:
                mHomeFragment0.onActivityResult(requestCode, resultCode, data);
                break;
            case HOME_TAB_INDEX_1:
                mHomeFragment1.onActivityResult(requestCode, resultCode, data);
                break;
            case HOME_TAB_INDEX_2:
                mHomeFragment2.onActivityResult(requestCode, resultCode, data);
                break;
            case HOME_TAB_INDEX_3:
                mHomeFragment3.onActivityResult(requestCode, resultCode, data);
                break;
            default:
                break;
        }
    }

    public static void setIndexByPre() {
        mIndex = preIndex;
    }
    public static void setIndexInit() {
        mIndex = HOME_TAB_INDEX_0;
    }

    /**
     * 这是系统bar颜色
     */
    private void applySelectedColor() {
        int color = Color.argb(0, Color.red(0), Color.green(0), Color.blue(0));
        mTintManager.setTintColor(color);
    }


    /**
     * 通过index获取主页面中的某个fragment
     * @param index  0-3
     * @return
     */
    public BaseFragment getFragmentByIndex(int index){
        if(index == 0){
            return mHomeFragment0;
        }else if(index ==1){
            return mHomeFragment1;
        }else if(index ==2){
            return mHomeFragment2;
        }else if(index ==3){
            return mHomeFragment3;
        }
        return null;
    }
}
