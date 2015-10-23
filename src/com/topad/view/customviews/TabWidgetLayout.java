package com.topad.view.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.topad.R;
import com.topad.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * The author 欧瑞强 on 2015/7/16.
 * todo 首頁底部TAB控件
 */
public class TabWidgetLayout extends LinearLayout {

    // 存放底部菜单的各个文字CheckedTextView
    private List<CheckedTextView> mCheckedList = new ArrayList<CheckedTextView>();
    // 存放底部菜单每项View
    private List<CheckedTextView> mIconCheckedList = new ArrayList<CheckedTextView>();
    // 存放底部菜单每项View
    private List<View> mViewList = new ArrayList<View>();
    // 存放指示点
    private List<ImageView> mIndicateImgs = new ArrayList<ImageView>();

    // 底部菜单的文字数组
    private String[] mLabels;
    Context mContext;
    int height;
    public int TOUCH=0, ONRESUME=1;
    public int  tabSelected;
    /**
     * 底部的icon
     */
    private int[] mDrawableIds = new int[]{R.drawable.bg_home,
            R.drawable.bg_category, R.drawable.bg_collect,};

    public TabWidgetLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 初始化控件
        init(context);
    }

    public TabWidgetLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabWidgetLayout(Context context) {
        super(context);
        init(context);
    }

    /**
     * 初始化控件
     */
    private void init(final Context context) {
        mContext = context;
        mLabels = getResources().getStringArray(R.array.tab_name);
        this.setOrientation(LinearLayout.HORIZONTAL);

        LayoutInflater inflater = LayoutInflater.from(context);

        int size = mLabels.length;
        for (int i = 0; i < size; i++) {
            final int index = i;
            // 每个tab对应的layout
            RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.tab_item, null);

            CheckedTextView itemName = (CheckedTextView) view.findViewById(R.id.item_name);
            CheckedTextView itemIcon = (CheckedTextView) view.findViewById(R.id.item_icon);
            itemIcon.setBackgroundResource(mDrawableIds[i]);
            itemName.setText(mLabels[i]);

            // 指示点ImageView，如有版本更新需要显示
            ImageView indicateImg = (ImageView) view.findViewById(R.id.indicate_img);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f);
            params.gravity = Gravity.CENTER;
            this.addView(view, params);

            // CheckedTextView设置索引作为tag，以便后续更改颜色、图片等
            itemName.setTag(index);
            // 将CheckedTextView添加到list中，便于操作
            mIconCheckedList.add(itemIcon);
            mCheckedList.add(itemName);
            // 将指示图片加到list，便于控制显示隐藏
            mIndicateImgs.add(indicateImg);
            // 将各个tab的View添加到list
            mViewList.add(view);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 设置底部图片和文字的显示
                    setTabsDisplay(context, index);
                    if (null != mTabListener) {
                        // tab项被选中的回调事件
                        mTabListener.onTabSelected(index,TOUCH);
                    }
                }
            });

        }
        setTabsDisplay(mContext, 0);
    }

    /**
     * 设置指示点(红点)的显示
     * @param context
     * @param position 显示位置 索引
     * @param visible  是否显示，如果false，则都不显示
     */
    public void setIndicateDisplay(Context context, int position,
                                   boolean visible) {
        int size = mIndicateImgs.size();
        if (size <= position) {
            return;
        }
        ImageView indicateImg = mIndicateImgs.get(position);
        indicateImg.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置底部导航中图片显示状态和字体颜色
     */
    public void setTabsDisplay(Context context, int index) {
        int size = mCheckedList.size();
        for (int i = 0; i < size; i++) {
            CheckedTextView checkedTextView = mCheckedList.get(i);
            CheckedTextView checkedIcon = mIconCheckedList.get(i);

            if ((Integer) (checkedTextView.getTag()) == index) {
                LogUtil.d("//>" + mLabels[index] + " is selected...");
                checkedTextView.setChecked(true);
                checkedIcon.setChecked(true);
                checkedTextView.setTextColor(mContext.getResources().getColor(R.color.white));//#F7587B
                mViewList.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.act_home_tab_blue_normal));//#F0F1F2
            } else {
                checkedTextView.setChecked(false);
                checkedIcon.setChecked(false);
                checkedTextView.setTextColor(mContext.getResources().getColor(R.color.gray));//#130C0E
                mViewList.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.act_home_tab_blue_normal));//#FAFAFA
            }
        }
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//
//        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        if (widthSpecMode != MeasureSpec.EXACTLY) {
//            widthSpecSize = 0;
//        }
//
//        if (heightSpecMode != MeasureSpec.EXACTLY) {
//            heightSpecSize = 0;
//        }
//
//        if (widthSpecMode == MeasureSpec.UNSPECIFIED
//                || heightSpecMode == MeasureSpec.UNSPECIFIED) {
//        }
//
//        // 控件的最大高度，就是下边tab的背景最大高度
//        int width;
//        int height;
//        width = Math.max(getMeasuredWidth(), widthSpecSize);
//        height = Math.max(this.getBackground().getIntrinsicHeight(),
//                heightSpecSize);
//        setMeasuredDimension(width, height);
//    }

    // 回调接口，用于获取tab的选中状态
    private OnTabSelectedListener mTabListener;

    public interface OnTabSelectedListener {
        void onTabSelected(int index,int from);
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        this.mTabListener = listener;
    }

}
