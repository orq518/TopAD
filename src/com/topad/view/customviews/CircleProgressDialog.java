package com.topad.view.customviews;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.topad.R;

/**
 * ${todo}<loading进度条>
 *
 * @author lht
 * @data: on 15/7/31 16:22
 */
public class CircleProgressDialog extends Dialog {
    private Context context;
    protected TextView mLoadingText;
    private AnimationDrawable mLoadingAnimation;
    private View mLoading;
    private Handler mHandler = new Handler();


    public CircleProgressDialog(Context context) {
        super(context);
        init(context);
        this.context = context;
    }

    public CircleProgressDialog(Context context, int theme) {
        super(context, theme);
        init(context);
        this.context = context;
    }

    protected void init(Context ctx) {
        LayoutInflater inflate = LayoutInflater.from(ctx);
        mLoading = inflate.inflate(R.layout.loading_layout, null);
        setContentView(mLoading);
        this.getWindow().setLayout(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        mLoadingText = (TextView) findViewById(R.id.loading_text);
        mLoadingAnimation = (AnimationDrawable) mLoading
                .findViewById(R.id.progress).getBackground();


        // 不能取消
        this.setCancelable(false);
        // 按搜索键也不会影响进度条显示
        this.setOnKeyListener(new OnKeyListener() {

            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH
                        && event.getRepeatCount() == 0) {
                    return true;
                }
                return false;
            }
        });
    }

    public void setMessage(String str) {
        mLoadingText.setText(str);
//		mLoadingText.setVisibility(View.VISIBLE);
    }

    protected void showAnimation(final AnimationDrawable anim, boolean start) {
        if (null == anim) {
            return;
        }
        if (start) {
            if (false == anim.isRunning()) {
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        anim.start();
                    }
                }, 50);

            }
        } else {
            anim.stop();
        }
    }

    public void show() {
        super.show();
        mLoading.setVisibility(View.VISIBLE);
        showAnimation(mLoadingAnimation, true);
    }

    public void dismiss() {
        try {
            super.dismiss();
            mLoading.setVisibility(View.GONE);
            showAnimation(mLoadingAnimation, false);
        } catch (Exception e) {

        }

    }

}
