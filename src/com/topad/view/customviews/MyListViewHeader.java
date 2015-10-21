package com.topad.view.customviews;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.topad.R;

/**
 */
public class MyListViewHeader extends LinearLayout {
	
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;

	private final int ROTATE_ANIM_DURATION = 180;

	private LinearLayout mContainer;
	private ImageView mArrowImageView;
	private ProgressBar mProgressBar;
//	private ImageView mProgressBar;
	private TextView mHintTextView;
	private int mState = STATE_NORMAL;
	private Animation mRotateUpAnim,mRotateAnim_loading;
	private Animation mRotateDownAnim;
	private ViewGroup moreViewContainer;//listview的header中出下拉刷新之外的view

	public MyListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	public MyListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		LayoutParams lp = new LayoutParams(
				LayoutParams.FILL_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.refresh_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);

		mArrowImageView = (ImageView)findViewById(R.id.pull_to_refresh_image);
		mHintTextView = (TextView)findViewById(R.id.pull_to_refresh_text);
		
		mProgressBar = (ProgressBar) findViewById(R.id.pull_to_refresh_progress);
		
		mRotateAnim_loading = new RotateAnimation(0, 36000, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateAnim_loading.setRepeatMode(Animation.RESTART);
		mRotateAnim_loading.setDuration(100000);
		mRotateAnim_loading.setInterpolator(new LinearInterpolator());//
//		mProgressBar.startAnimation(mRotateAnim_loading);
		
		
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
		moreViewContainer = (ViewGroup) findViewById(R.id.LinearL_header_more);
	}

	public void setState(int state) {
		if (state == mState) return ;
		
		if (state == STATE_REFRESHING) {
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.INVISIBLE);
			mProgressBar.setVisibility(View.VISIBLE);mProgressBar.setAnimation(mRotateAnim_loading);
			mProgressBar.startAnimation(mRotateAnim_loading);
			
		} else {
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.INVISIBLE);
			mProgressBar.clearAnimation();
		}
		
		switch(state){
		case STATE_NORMAL:
			if (mState == STATE_READY) {
				mArrowImageView.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFRESHING) {
				mArrowImageView.clearAnimation();
			}
			mHintTextView.setText(R.string.my_listview_header_hint_normal);
			break;
		case STATE_READY:
			if (mState != STATE_READY) {
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mRotateUpAnim);
				mHintTextView.setText(R.string.my_listview_header_hint_ready);
			}
			break;
		case STATE_REFRESHING:
			mHintTextView.setText(R.string.my_listview_header_hint_loading);
			break;
			default:
		}
		
		mState = state;
	}
	
	
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return mContainer.getHeight();
	}

	public int getMoreViewHeight(){
		return moreViewContainer.getHeight();
	}
	
	/**
	 * 当前header的状态
	 * @return
	 */
	public int getCurState(){
		return mState;
	}
	/**
	 * header中添加更多的view
	 * @param view
	 */
	public void addMoreView(View view,ViewGroup.LayoutParams params){
		moreViewContainer.setVisibility(View.VISIBLE);
		moreViewContainer.addView(view,params);
	}
}
