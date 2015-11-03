package com.topad;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.topad.util.LogUtil;
import com.topad.util.Utils;
import com.topad.view.activity.MainActivity;
import com.topad.view.fragment.BaseFragment;

import java.util.List;

/**
 * The author ou on 2015/7/15.
 */
public class TopADApplication extends Application {
    private static Context context;
    static int[] screenDispaly;
    private static PackageInfo mPackageInfo;


    //    public static boolean mHomeNeedRefresh;// 首页是否需要刷新,true：回到首页需要刷新，false:不需刷新
    private String token;// 标识是否登录状态
    private String identity;// 标识是否实名认证
    private String bindCard;// 标识是否绑卡

    private Handler handler = new Handler();
    FragmentManager mFragmentManager;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        LogUtil.d("#####退出");
    }

    public static Context getContext() {
        return context;
    }


    public PackageInfo getPackageInfo() {
        if (mPackageInfo == null) {
            mPackageInfo = Utils.getPackageInfo(this);
        }
        return mPackageInfo;
    }

    public static TopADApplication getSelf() {
        return (TopADApplication) context;
    }


    /**
     * 获取屏幕分辨率
     *
     * @return
     */
    public static int[] getScreenDispaly() {
        if (screenDispaly == null) {
            return screenDispaly = Utils.getScreenDispaly(context);
        } else {
            return screenDispaly;
        }
    }






}
