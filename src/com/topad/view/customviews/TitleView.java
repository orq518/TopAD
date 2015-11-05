package com.topad.view.customviews;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topad.R;
import com.topad.util.Utils;
import com.topad.view.activity.LoginActivity;


/**
 * 标题栏
 */
public class TitleView extends RelativeLayout {
    private TextView mTitle;
    private ImageView mLeft;
    private TextView mRigh;
    private ImageView mRightImage;
    private Context mContext;
    private View v;

    /**
     * 下拉菜单
     **/
    private LinearLayout mTitleLayout;
    /**
     * 下拉悬浮框
     **/
    private PopupWindow mPopupWindow;
    /**
     * 下拉菜单文字
     **/
    private String[] mMenuStrings;
    /**
     * title
     **/
    private String mTitleString;

    public TitleView(Context context) {
        super(context);
        mContext = context;
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = mInflater.inflate(R.layout.title_view, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.addView(v, layoutParams);
        mTitle = (TextView) v.findViewById(R.id.tv_title);
        mLeft = (ImageView) v.findViewById(R.id.tv_back);
        mRigh = (TextView) v.findViewById(R.id.bt_right);
        mRightImage = (ImageView) v.findViewById(R.id.mRightImage);
        mTitleLayout = (LinearLayout) v.findViewById(R.id.title_layout);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
        mTitleString = title;
    }

    public void setTitle(int textId) {
        mTitle.setText(textId);
    }


    public void setLeftIcon(int resId) {
        mLeft.setImageResource(resId);
        mLeft.setVisibility(View.VISIBLE);
    }

    public void setLeftClickListener(OnClickListener lis) {
        mLeft.setOnClickListener(lis);
    }

    public void setLeftVisiable(boolean visible) {
        mLeft.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightVisiable(boolean visible) {
        mRigh.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightClickListener(OnClickListener lis, String text) {
        mRigh.setOnClickListener(lis);
        if (text != null) {
            mRigh.setText(text);
            mRigh.setVisibility(View.VISIBLE);
        } else {
            mRigh.setVisibility(View.GONE);
        }
    }

    public void setRightImageClickListener(OnClickListener lis, int drawid) {
        mRightImage.setOnClickListener(lis);
        if (drawid != 0) {
            mRightImage.setBackgroundResource(drawid);
            mRightImage.setVisibility(View.VISIBLE);
        } else {
            mRightImage.setVisibility(View.GONE);
        }
    }

    /**
     * 设置菜单是否显示
     *
     * @param menuStrings
     * @param view
     */
    public void setTopMenuVisibility(String right, String[] menuStrings, final View view) {
        this.mMenuStrings = menuStrings;
        if (!Utils.isEmpty(right)) {
            mRigh.setText(right);
            mRigh.setVisibility(View.VISIBLE);
            mRightImage.setVisibility(View.GONE);
            mRigh.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    showMenu(view);
                }
            });
        }
    }

    /**
     * 显示菜单
     */
    private void showMenu(View view) {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
            return;
        }
        if (mPopupWindow == null) {
            if (view != null) {
                mPopupWindow = new PopupWindow(view, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, true);
            }
        }

        mRigh.setText(mContext.getString(R.string.app_name));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAsDropDown(this);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            public void onDismiss() {
                mRigh.setText(mContext.getString(R.string.app_name));
            }
        });

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

    }

    /**
     * 更改标题
     *
     * @param index
     */
    public void changeTitle(int index) {
        setTitle(mMenuStrings[index]);
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    /**
     * 设置背景色
     *
     * @param color
     */
    public void mSetBackgroundColor(int color) {
        findViewById(R.id.layout_content).setBackgroundColor(color);
    }

    /**
     * 设置右边文本的背景
     *
     * @param mDrawable
     */
    public void setRightTextVBackgroud(Drawable mDrawable) {
        mRigh.setBackgroundDrawable(mDrawable);
    }

    /**
     * 设置title栏下方的分割线，显示or隐藏
     */
    public void setBottomLineState(int status) {
        findViewById(R.id.view_bottom_line).setVisibility(status);
    }


}