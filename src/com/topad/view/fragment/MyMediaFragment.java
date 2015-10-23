package com.topad.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.topad.R;
import com.topad.util.LogUtil;
import com.topad.view.customviews.TitleView;

/**
 * Created by ouruiqiang on 2015/10/16.
 * File for what:
 * ps:
 */
public class MyMediaFragment extends BaseFragment implements  View.OnClickListener{

    private String tag = "MyMediaFragment";
    private View mRootView;
    private RelativeLayout mMainWalletLaout;
    private Context mCtx;

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
            mRootView = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_my_media_layout, null);
            TitleView mTitleView = (TitleView) mRootView.findViewById(R.id.title);
            mTitleView.setTitle("生活");
            mTitleView.setLeftVisiable(false);

        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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

        LogUtil.d("是否显示：" + isVisibleToUser + "    isNeedRefresh:" + isNeedRefresh);
        if (isVisibleToUser && isNeedRefresh) {
            isNeedRefresh = false;
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
