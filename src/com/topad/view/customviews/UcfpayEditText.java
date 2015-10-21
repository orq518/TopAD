package com.topad.view.customviews;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 
 * 
 * <PRE>
 *  类名：UcfpayEditText
 *  
 * 类简介：
 * 	    自定义输入控件
 *
 * </PRE>
 */
public class UcfpayEditText extends EditText {

	int mColor;

	public UcfpayEditText(Context context) {
		super(context);
		init(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public UcfpayEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public UcfpayEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context ctx) {

		this.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (event != null) {
					if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
						actionId = EditorInfo.IME_ACTION_DONE;

					}
				}

				if (actionId == EditorInfo.IME_ACTION_DONE) {
					InputMethodManager imm = (InputMethodManager) getContext()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(getWindowToken(), 0);

				}
				return false;
			}
		});

		mColor = this.getCurrentTextColor();

		this.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 获得焦点并且有输入时才显示删掉按钮
				clearError();
			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
		});
	}

	public void onError() {

		this.setTextColor(0xFFDD0000);
		//
		// TranslateAnimation myAnimation_Translate = new TranslateAnimation(0f,
		// 10.0f, 0f, 0f);
		// myAnimation_Translate.setDuration(300);
		// myAnimation_Translate.setInterpolator(new CycleInterpolator(4));
		//
		// this.startAnimation(myAnimation_Translate);
	}

	public void clearError() {

		this.setTextColor(mColor);
		//
		// TranslateAnimation myAnimation_Translate = new TranslateAnimation(0f,
		// 10.0f, 0f, 0f);
		// myAnimation_Translate.setDuration(300);
		// myAnimation_Translate.setInterpolator(new CycleInterpolator(4));
		//
		// this.startAnimation(myAnimation_Translate);
	}

}
