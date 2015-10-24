package com.topad.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.topad.R;
import com.topad.view.customviews.TitleView;

/**
 * 发布需求编辑界面
 */
public class ShareNeedsEditActivity extends BaseActivity implements View.OnClickListener{

    /** title布局 **/
    private TitleView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutById() {
        return R.layout.activity_needs_edit;
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
        mTitle.setLeftClickListener(new TitleLeftOnClickListener());

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
        Intent intent = new Intent(ShareNeedsEditActivity.this, NeedsListActivity.class);
        super.onClick(v);
        switch (v.getId()) {
//            case R.id.tab01:
//                break;
            default:
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onBack() {
        finish();
    }

    /**
     * 设置底部布局
     */

    /**
     * 顶部布局--左按钮事件监听
     */
    public class TitleLeftOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            onBack();
        }

    }


}
