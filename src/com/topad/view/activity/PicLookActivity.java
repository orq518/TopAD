package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.topad.R;
import com.topad.util.PictureUtil;
import com.topad.util.Utils;
import com.topad.view.customviews.TitleView;

/**
 * @data: on 15/11/2 16:35
 */
public class PicLookActivity extends BaseActivity implements OnClickListener {
    /**
     * 上下文
     **/
    private Context mContext;
    ImageView im_icon;
    String pathString;

    @Override
    public int setLayoutById() {
        mContext = this;
        return R.layout.activity_piclook;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    @Override
    public void initViews() {
        im_icon = (ImageView) findViewById(R.id.im_icon);
        pathString = getIntent().getStringExtra("picpath");
        if (!Utils.isEmpty(pathString)) {
            Bitmap image = PictureUtil
                    .getSmallBitmap(pathString);
            im_icon.setImageBitmap(image);
        }
    }

    @Override
    public void initData() {

    }

}
