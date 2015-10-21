package com.topad.view.customviews;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.topad.R;

/**
 */
public class MyListViewFooter extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;

	private final int ROTATE_ANIM_DURATION = 180;

	private Context mContext;
	private View mContentView;
	private View mProgressBar;
	private ImageView mArrowImageView;
	private TextView mHintView;//,mTextV_loading;
	private Animation mRotateUpAnim,mRotateDownAnim;
	
	public MyListViewFooter(Context context) {
		super(context);
		initView(context);
	}
	
	public MyListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	
	public void setState(int state) {
		mHintView.setVisibility(View.INVISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
//		mArrowImageView.setVisibility(View.INVISIBLE);
//		mTextV_loading.setVisibility(View.INVISIBLE);   
		mHintView.setVisibility(View.INVISIBLE);
		if (state == STATE_READY) {
//			mArrowImageView.setVisibility(View.VISIBLE);
//			mArrowImageView.clearAnimation();
//			mArrowImageView.startAnimation(mRotateUpAnim);
			mHintView.setText(R.string.my_listview_footer_hint_ready);
			mHintView.setVisibility(View.VISIBLE);
		} else if (state == STATE_LOADING) {
			mHintView.setText(R.string.my_listview_header_hint_loading);
			mHintView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.VISIBLE);
//			mTextV_loading.setVisibility(View.VISIBLE);
		} else {
			mHintView.setText(R.string.my_listview_footer_hint_normal);
			mHintView.setVisibility(View.VISIBLE);
//			mArrowImageView.setVisibility(View.VISIBLE);
//			mArrowImageView.clearAnimation();
//			mArrowImageView.startAnimation(mRotateDownAnim);
		}
	}
	
	public void setBottomMargin(int height) {
		if (height < 0) return ;
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}
	
	public int getBottomMargin() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		return lp.bottomMargin;
	}
	
	
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
//		mTextV_loading.setVisibility(View.GONE);
	}
	
	public void loading() {
//		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
//		mTextV_loading.setVisibility(View.VISIBLE);
	}
	
	public void hide() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}
	
	public void show() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}
	
	private void initView(Context context) {
		mContext = context;
		mContentView = LayoutInflater.from(mContext).inflate(R.layout.refresh_footer, null);
		addView(mContentView);
		mContentView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//		mContentView = moreView.findViewById(R.id.pull_to_refresh_footer);

		mProgressBar = mContentView.findViewById(R.id.pull_to_load_progress);
//		mTextV_loading = (TextView) findViewById(R.id.TextV_loading);
		mHintView = (TextView)mContentView.findViewById(R.id.pull_to_load_text);
		mArrowImageView = (ImageView) mContentView.findViewById(R.id.pull_to_load_image);

		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}
	
}
