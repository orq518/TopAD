package com.topad.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;

import com.topad.R;
import com.topad.amap.PoiKeywordSearchActivity;
import com.topad.util.RecordTools;
import com.topad.util.SystemBarTintManager;
import com.topad.view.customviews.TitleView;
import com.topad.view.interfaces.IRecordFinish;

/**
 * 发布需求编辑界面
 */
public class ShareNeedsEditActivity extends BaseActivity implements IRecordFinish,View.OnClickListener {

    /**
     * title布局
     **/
    private TitleView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutById() {
        return R.layout.activity_needs_edit;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    private void applySelectedColor() {
        int color = Color.argb(0, Color.red(0), Color.green(0), Color.blue(0));
        mTintManager.setTintColor(color);
    }

    /**
     * 沉浸式状态栏
     **/
    private SystemBarTintManager mTintManager;

    @Override
    public void initViews() {
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        applySelectedColor();
        // 顶部布局
        mTitle = (TitleView) findViewById(R.id.title);
        // 设置顶部布局
        mTitle.setTitle("发布需求");
        mTitle.setLeftClickListener(new TitleLeftOnClickListener());
        mTitle.setRightImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShareNeedsEditActivity.this, PoiKeywordSearchActivity.class);
                startActivity(intent);
            }
        }, R.drawable.bt_search);
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    final int PICKPHOTO = 1;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.carame:
                //使用startActivityForResult启动SelectPicPopupWindow当返回到此Activity的时候就会调用onActivityResult函数
                Intent intent1 = new Intent(ShareNeedsEditActivity.this,
                        SelectPicPopupWindow.class);
                startActivityForResult(intent1, PICKPHOTO);
                break;
            case R.id.voice:
                RecordTools recordTools=new RecordTools(ShareNeedsEditActivity.this,ShareNeedsEditActivity.this);
                recordTools.showVoiceDialog();
                break;

            default:
                break;
        }
    }

    @Override
    public void onBack() {
        finish();
    }

    /**
     * 录音完成
     * @param voicePath
     */
    @Override
    public void RecondSuccess(String voicePath) {

    }

    /**
     * 设置底部布局
     */

    /**
     * 顶部布局--左按钮事件监听
     */
    public class TitleLeftOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            onBack();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {
            case 1:
                if (data != null) {
                    //取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
                    Uri mImageCaptureUri = data.getData();
                    //返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
                    if (mImageCaptureUri != null) {
                        Bitmap image;
                        try {
                            //这个方法是根据Uri获取Bitmap图片的静态方法
                            image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);
//                            if (image != null) {
//                                photo.setImageBitmap(image);
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            //这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
                            Bitmap image = extras.getParcelable("data");
//                            if (image != null) {
//                                photo.setImageBitmap(image);
//                            }
                        }
                    }

                }
                break;
            default:
                break;

        }
    }
}
