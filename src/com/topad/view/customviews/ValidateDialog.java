package com.topad.view.customviews;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.topad.R;

/**
 * The author 欧瑞强 on 2015/7/29.
 * todo
 */
public class ValidateDialog extends Dialog {
    private Context mContext;
    public ValidateDialog(Context context) {
        super(context);
    }


    protected void init( Context ctx) {
        mContext = ctx;
        LayoutInflater inflate = LayoutInflater.from(mContext);
        View v = inflate.inflate(R.layout.ucf_dialog, null);
        setContentView(v);
        // 不能取消
        this.setCancelable(false);

    }
    public void show() {
        super.show();
    }

    public void dismiss() {
        super.dismiss();
    }
}
