package com.topad.view.customviews.pullToRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.topad.R;

/**
 * 这个类封装了下拉刷新的布局
 * 
 */
public class FooterLoadingLayout extends LoadingLayout {
    /**进度条*/
    private ProgressBar mProgressBar;
    /** 显示的文本 */
    private TextView mHintView;
    
    /**
     * 构造方法
     * 
     * @param context context
     */
    public FooterLoadingLayout(Context context) {
        super(context);
        init(context);
    }

    /**
     * 构造方法
     * 
     * @param context context
     * @param attrs attrs
     */
    public FooterLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化
     * 
     * @param context context
     */
    private void init(Context context) {
        mProgressBar = (ProgressBar) findViewById(R.id.pull_to_load_progress);
        mHintView = (TextView) findViewById(R.id.pull_to_load_text);
        
        setState(State.RESET);
    }
    
    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.refresh_footer, null);
        return container;
    }

    @Override
    public void setLastUpdatedLabel(CharSequence label) {
    }

    @Override
    public int getContentSize() {
        View view = findViewById(R.id.pull_to_refresh_footer);
        if (null != view) {
            return view.getHeight();
        }
        
        return (int) (getResources().getDisplayMetrics().density * 40);
    }
    
    @Override
    protected void onStateChanged(State curState, State oldState) {
        mProgressBar.setVisibility(View.GONE);
        mHintView.setVisibility(View.INVISIBLE);
        
        super.onStateChanged(curState, oldState);
    }
    
    @Override
    protected void onReset() {
        mHintView.setText(R.string.my_listview_header_hint_loading);
    }

    @Override
    protected void onPullToRefresh() {
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText(R.string.my_listview_header_hint_loading);
    }

    @Override
    protected void onReleaseToRefresh() {
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText(R.string.my_listview_header_hint_ready);
    }

    @Override
    protected void onRefreshing() {
        mProgressBar.setVisibility(View.VISIBLE);
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText(R.string.my_listview_header_hint_loading);
    }
    
    @Override
    protected void onNoMoreData() {
        mHintView.setVisibility(View.VISIBLE);
//        mHintView.setText(R.string.pushmsg_center_no_more_msg);
        mHintView.setText("没有更多数据");
    }
}
