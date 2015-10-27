package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.topad.R;
import com.topad.util.ImageManager;
import com.topad.util.Utils;
import com.topad.view.customviews.CycleImageLayout;
import com.topad.view.customviews.TitleView;

import java.util.ArrayList;

/**
 * ${todo}<案例>
 *
 * @author lht
 * @data: on 15/10/27 14:40
 */
public class ADSCaseActivity extends BaseActivity implements View.OnClickListener,
        CycleImageLayout.ImageCycleViewListener {
    private static final String LTAG = ADSCaseActivity.class.getSimpleName();
    /** 上下文 **/
    private Context mContext;
    /** 顶部布局 **/
    private TitleView mTitleView;
    /** 内容 **/
    private TextView mContent;
    /** 轮播banner **/
    private CycleImageLayout mBannerView;

    /** 标题 **/
    private String title;
    /** 数据 **/
    private ArrayList<String> imageUrlList;

    @Override
    public int setLayoutById() {
        mContext = this;
        return R.layout.activity_ads_case;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    @Override
    public void initViews() {
        mTitleView = (TitleView) findViewById(R.id.title);
        mBannerView = (CycleImageLayout) findViewById(R.id.ad_view);
        mContent = (TextView) findViewById(R.id.tv_case_introduce_content);
        imageUrlList = new ArrayList<String>();
    }

    @Override
    public void initData() {
        // 接收数据
        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
        }

        showView();
    }

    /**
     * 显示数据
     */
    private void showView() {
        // 设置顶部标题布局
        if (!Utils.isEmpty(title)) {
            mTitleView.setTitle(title);
        }
        mTitleView.setLeftClickListener(new TitleLeftOnClickListener());

        mBannerView.setImageResources(setData(), null, this);

    }

    @Override
    public void displayImage(String imageURL, ImageView imageView) {
        ImageManager.getInstance(mContext).getBitmap(imageURL,
                new ImageManager.ImageCallBack() {
                    @Override
                    public void loadImage(ImageView imageView, Bitmap bitmap) {
                        if (bitmap != null && imageView != null) {
                            imageView.setImageBitmap(bitmap);
                            imageView
                                    .setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                    }
                }, imageView);
    }

    @Override
    public void onImageClick(int position, View imageView) {

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

    public ArrayList<String> setData(){
        for(int i = 0; i<2; i++){
            imageUrlList.add("http://api.hongdoulicai.com/assets/images/banner/1.png");
        }
        return imageUrlList;
    }
}
