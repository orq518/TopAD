package com.topad.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topad.R;
import com.topad.view.customviews.TitleView;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    /**
     * title布局
     **/
    private TitleView mTitle;

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
        // 顶部布局
        mTitle = (TitleView) findViewById(R.id.title);
        // 设置顶部布局
        mTitle.setTitle(getString(R.string.main_title));
        mTitle.setLeftVisiable(false);

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

}
