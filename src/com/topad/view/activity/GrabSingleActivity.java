package com.topad.view.activity;

import android.graphics.Matrix;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.topad.R;
import com.topad.view.fragment.GrabSingleFragment;
import com.topad.view.fragment.SelectProjectFragmnet;


import java.util.ArrayList;
import java.util.List;

/**
 * ${todo}<我要抢单>
 *
 * @author lht
 * @data: on 15/10/27 17:31
 */
public class GrabSingleActivity extends BaseActivity implements View.OnClickListener {
    private static final String LTAG = GrabSingleActivity.class.getSimpleName();
    /** 上下文 **/
    private Context mContext;
    /** 页卡内容 **/
    private ViewPager viewPager;
    /** 动画图片 **/
    private ImageView imageView;
    /** 返回 **/
    private TextView mBack;
    /** 我要抢单 **/
    private TextView tvGrabSingle;
    /** 甄选项目 **/
    private TextView tvSelectProject;

    /** Tab页面列表 **/
    private List<Fragment> fragments;
    /** 动画图片偏移量 **/
    private int offset = 0;
    /** 当前页卡编号 **/
    private int currIndex = 0;
    /** 动画图片宽度 **/
    private int bmpW;
    private int selectedColor, unSelectedColor;
    /** 页卡总数 **/
    private static final int pageSize = 2;

    @Override
    public int setLayoutById() {
        mContext = this;
        return R.layout.activity_grab_single;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }
    @Override
    public void initViews() {
        selectedColor = getResources().getColor(R.color.white);
        unSelectedColor = getResources().getColor(R.color.gray);

        InitImageView();
        InitTextView();
        InitViewPager();
    }

    @Override
    public void initData() {

    }

    /**
     * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
     */
    private void InitImageView() {
        imageView = (ImageView) findViewById(R.id.iv_bottom_line);
        bmpW = imageView.getLayoutParams().width;// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / pageSize - bmpW) / 3;// 计算偏移量--(屏幕宽度/页卡总数-图片实际宽度)/3 = 偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置
    }

    /**
     * 初始化头标
     */
    private void InitTextView() {
        mBack = (TextView) findViewById(R.id.tv_back);
        tvGrabSingle = (TextView) findViewById(R.id.tv_grab_single);
        tvSelectProject = (TextView) findViewById(R.id.tv_select_project);

        tvGrabSingle.setTextColor(selectedColor);
        tvSelectProject.setTextColor(unSelectedColor);

        mBack.setOnClickListener(this);
        tvGrabSingle.setOnClickListener(new MyOnClickListener(0));
        tvSelectProject.setOnClickListener(new MyOnClickListener(1));
    }

    /**
     * 初始化Viewpager页
     */
    private void InitViewPager() {
        viewPager = (ViewPager) findViewById(R.id.vPager);
        fragments = new ArrayList<Fragment>();
        fragments.add(new GrabSingleFragment());
        fragments.add(new SelectProjectFragmnet());
        viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 头标点击监听
     */
    private class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {

            switch (index) {
                case 0:
                    tvGrabSingle.setTextColor(selectedColor);
                    tvSelectProject.setTextColor(unSelectedColor);
                    break;
                case 1:
                    tvSelectProject.setTextColor(selectedColor);
                    tvGrabSingle.setTextColor(unSelectedColor);
                    break;
            }
            viewPager.setCurrentItem(index);
        }

    }

    /**
     * 为选项卡绑定监听器
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
//		int two = one * 2;// 页卡1 -> 页卡3 偏移量

        public void onPageScrollStateChanged(int index) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int index) {
            Animation animation = new TranslateAnimation(one * currIndex, one
                    * index, 0, 0);// 显然这个比较简洁，只有一行代码。
            currIndex = index;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            imageView.startAnimation(animation);

            switch (index) {
                case 0:
                    tvGrabSingle.setTextColor(selectedColor);
                    tvSelectProject.setTextColor(unSelectedColor);
                    break;
                case 1:
                    tvSelectProject.setTextColor(selectedColor);
                    tvGrabSingle.setTextColor(unSelectedColor);
                    break;

            }
        }
    }

    /**
     * 定义适配器
     */
    class myPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        /**
         * 得到每个页面
         */
        @Override
        public Fragment getItem(int arg0) {
            return (fragmentList == null || fragmentList.size() == 0) ? null
                    : fragmentList.get(arg0);
        }

        /**
         * 每个页面的title
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        /**
         * 页面的总个数
         */
        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            // 返回
            case R.id.tv_back:
                finish();
                break;

            default:
                break;
        }
    }

}


