package com.topad.view.customviews;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.topad.R;


public class UcfDialog extends Dialog {

    private Context mContext;
    private TextView mTitle;
    private ImageView mExit;
    private TextView mContent;
    private TextView mConfirmBtn;
    private TextView mCancelBtn;
    private TextView mIKnow;
    LinearLayout  mBtLayout;
    int buttonNum=2;
    public UcfDialog(Context context, int theme,int bt_num,boolean isCancel) {
        super(context, theme);
        buttonNum=bt_num;
        init(context,isCancel);
    }

    protected void init( Context ctx,boolean isCancel) {
        mContext = ctx;
        LayoutInflater inflate = LayoutInflater.from(mContext);
        View v = inflate.inflate(R.layout.ucf_dialog, null);
        setContentView(v);
        mBtLayout= (LinearLayout) v.findViewById(R.id.mBtLayout);
        mTitle = (TextView) v.findViewById(R.id.mTitle);
        mExit = (ImageView) v.findViewById(R.id.mExit);
        mContent = (TextView) v.findViewById(R.id.mContent);
        mConfirmBtn = (TextView) v.findViewById(R.id.mConfirmBtn);
        mCancelBtn = (TextView) v.findViewById(R.id.mCancelBtn);
        mIKnow= (TextView) v.findViewById(R.id.mIKnow);
        // 不能取消
        this.setCancelable(isCancel);

    }

    public void setTitleText(String str) {
        mTitle.setText(str);
    }


    public void setContent(CharSequence str) {
        mContent.setText(str);
    }


    public void setContentLeft() {
        mContent.setGravity(Gravity.LEFT);
    }

    public void setExitBtn(View.OnClickListener ocl) {
        mExit.setOnClickListener(ocl);
    }

    public void setConfirmBtn(String text, View.OnClickListener ocl) {
        mBtLayout.setVisibility(View.VISIBLE);
        mIKnow.setVisibility(View.GONE);
        mConfirmBtn.setText(text);
        mConfirmBtn.setOnClickListener(ocl);
    }

    public void setCancelBtn(String text, View.OnClickListener ocl) {
        mBtLayout.setVisibility(View.VISIBLE);
        mIKnow.setVisibility(View.GONE);
        mCancelBtn.setText(text);
        mCancelBtn.setOnClickListener(ocl);
    }
    public void setSingleButton(String text, View.OnClickListener ocl) {
        mIKnow.setVisibility(View.VISIBLE);
        mBtLayout.setVisibility(View.GONE);
        mIKnow.setText(text);
        mIKnow.setOnClickListener(ocl);
    }
    public void show() {
        super.show();
    }

    public void dismiss() {
        super.dismiss();
    }

//	/**
//	 *
//	 * @Description: 设置需要单独颜色显示的文案,需要在setContent后调用
//	 * @param @param text 正文末尾文字
//	 * @param isPhone 是否需要拨叫电话
//	 * @return void
//	 */
//	public void setStyleText(final String text,final boolean isPhone, final String url,final String title) {
////		mPhone.setVisibility(View.GONE);
//		String wholeString = mContent.getText().toString() + text;
//		SpannableStringBuilder ssb = new SpannableStringBuilder(wholeString);
//		ssb.setSpan(new ForegroundColorSpan(Color.rgb(255, 114, 67)), mContent
//				.getText().length(), wholeString.length(),
//				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//
//
//		ssb.setSpan(new ClickableSpan() {
//
//			@Override
//			public void onClick(View widget) {
//				if(isPhone){
//					Uri telUri = Uri.parse("tel:" + text);
//					Intent intent = new Intent(Intent.ACTION_DIAL, telUri);
//					mContext.startActivity(intent);
//				}else{
//					Intent intent = new Intent(mContext, WebAppActivity.class);
//					intent.putExtra("title", title);
//					intent.putExtra("url", url);
//					mContext.startActivity(intent);
//				}
//			}
//		}, mContent.getText().length(), wholeString.length(),
//				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//		mContent.setMovementMethod(LinkMovementMethod.getInstance());
//
//
//		mContent.setText(ssb);
//	}

    public void setTitleVisible(boolean visible) {
        mTitle.setVisibility(visible ? View.VISIBLE : View.GONE);
        mTitle.invalidate();
    }

    public void setExitVisible(boolean visible) {
        mExit.setVisibility(visible ? View.VISIBLE : View.GONE);
        mExit.invalidate();
    }

}