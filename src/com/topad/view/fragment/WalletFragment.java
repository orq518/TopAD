package com.topad.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topad.R;
import com.topad.util.LogUtil;
import com.topad.view.customviews.WalletTitleView;

/**
 * Created by ouruiqiang on 2015/10/16.
 * File for what:
 * ps:
 */
public class WalletFragment extends BaseFragment implements  View.OnClickListener{

    private String tag = "WalletFragment";
    private View mRootView;
    RelativeLayout mMainWalletLaout;
    RelativeLayout mNotIdentityWalletLaout;
    View mWalletView;
    private Context mCtx;
    private TextView mRefersh;

    @Override
    public String getFragmentName() {
        return tag;
    }

    /**
     * ***生命周期******
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCtx = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mRootView) {
            mRootView = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_wallet_layout, null);
//            mRootView = getLayoutInflater(savedInstanceState).inflate(R.layout.activity_my_invite, null);
            WalletTitleView mTitleView = (WalletTitleView) mRootView.findViewById(R.id.title);
            mTitleView.setTitle("生活");
            mTitleView.setLeftVisiable(false);

        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //钱包的
        initView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        mMainWalletLaout = (RelativeLayout) mRootView.findViewById(R.id.wallet_lin_main);

    }


    @Override
    public void setVisible(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //钱包的逻辑
        LogUtil.d("是否显示：" + isVisibleToUser + "    isNeedRefresh:" + isNeedRefresh);
        if (isVisibleToUser && isNeedRefresh) {
            isNeedRefresh = false;
            mWalletView = null;
        }

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 刷新重试
//            case R.id.not_identity_tv:
//                // 未认证就去联网获取认证信息
//                break;

        }
    }
}
