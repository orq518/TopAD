package com.topad.view.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topad.util.LogUtil;

/**
 * Fragment基类
 *
 * @author dewyze
 */
public abstract class BaseFragment extends Fragment {

    public boolean isNeedRefresh;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.d(getFragmentName() + ": onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(getFragmentName() + ": onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtil.d(getFragmentName() + ": onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.d(getFragmentName() + ":  onViewCreated()");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d(getFragmentName() + ": onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d(getFragmentName() + ": onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(getFragmentName() + ":  onResume()");

    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(getFragmentName() + ":  onPause()");

    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d(getFragmentName() + ": onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d(getFragmentName() + ":  onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(getFragmentName() + ":  onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.d(getFragmentName() + ": onDetach()");
    }
    public void setVisible(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.d(this+"  是否显示：" + isVisibleToUser + "    isNeedRefresh:" + isNeedRefresh);
    }
    /**
     * fragment name
     */
    public abstract String getFragmentName();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
    }


}
