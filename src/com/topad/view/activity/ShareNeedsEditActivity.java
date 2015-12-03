package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topad.R;
import com.topad.amap.PoiKeywordSearchActivity;
import com.topad.util.LogUtil;
import com.topad.util.PictureUtil;
import com.topad.util.RecordMediaPlayer;
import com.topad.util.RecordTools;
import com.topad.util.SystemBarTintManager;
import com.topad.util.Utils;
import com.topad.view.customviews.PickDatePopwindow;
import com.topad.view.customviews.TitleView;
import com.topad.view.interfaces.IDatePick;
import com.topad.view.interfaces.IRecordFinish;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布需求编辑界面
 */
public class ShareNeedsEditActivity extends BaseActivity implements IRecordFinish, View.OnClickListener, IDatePick {

    /**
     * title布局
     **/
    private TitleView mTitle;
    MediaAdapter adapter;
    TextView data_pic;
    RelativeLayout bottom_layout;

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

    GridView add_detail_gridview;
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
        add_detail_gridview = (GridView) findViewById(R.id.add_detail_gridview);
        adapter = new MediaAdapter(this);
        add_detail_gridview.setAdapter(adapter);
        add_detail_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MeidaType meidaType = meidaTypeList.get(position);
                if (meidaType.type.equals("1")) {//图片
                    Intent intent = new Intent(ShareNeedsEditActivity.this, PicLookActivity.class);
                    intent.putExtra("picpath", meidaType.pathString);
                    startActivity(intent);
                } else {
                    RecordMediaPlayer player = RecordMediaPlayer.getInstance();
                    player.play(meidaType.pathString);
                }
            }
        });
        bottom_layout = (RelativeLayout) findViewById(R.id.bottom_layout);
        data_pic = (TextView) findViewById(R.id.data_pic);
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
                RecordTools recordTools = new RecordTools(ShareNeedsEditActivity.this, ShareNeedsEditActivity.this);
                recordTools.showVoiceDialog();
                break;
            case R.id.data_pic:
                PickDatePopwindow datePick = new PickDatePopwindow(ShareNeedsEditActivity.this);
                datePick.registeDatePick(this);
                datePick.showAtLocation(bottom_layout,
                        Gravity.BOTTOM, 0, 0);
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
     *
     * @param voicePath
     */
    @Override
    public void RecondSuccess(String voicePath) {
        MeidaType meidaType = new MeidaType();
        meidaType.type = "2";
        meidaType.pathString = voicePath;
        meidaTypeList.add(meidaType);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setDate(String dateString) {
        if (!Utils.isEmpty(dateString)) {
            data_pic.setText(dateString);
        }
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
            case PICKPHOTO:
                if (data != null) {
                    LogUtil.d("ouou", "#####path:" + data.getStringExtra("path"));
                    String picPath = data.getStringExtra("path");
                    if (!Utils.isEmpty(picPath)) {
                        Bitmap image = PictureUtil
                                .getSmallBitmap(picPath);
                        if (image != null) {
                            MeidaType meidaType = new MeidaType();
                            meidaType.type = "1";
                            meidaType.bitmap = image;
                            meidaType.pathString = picPath;
                            meidaTypeList.add(meidaType);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
                break;
            default:
                break;

        }
    }

//public void addpic(Bitmap bitmap){

    //
//
//}
    class MeidaType {
        /**
         * 1：图片  2：语音
         */
        String type;
        Bitmap bitmap;
        String pathString;
    }

    private List<MeidaType> meidaTypeList = new ArrayList<MeidaType>();

    //自定义适配器
    class MediaAdapter extends BaseAdapter {
        private LayoutInflater inflater;


        public MediaAdapter(Context context) {
            super();

            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (null != meidaTypeList) {
                return meidaTypeList.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return meidaTypeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.media_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.play = (ImageView) convertView.findViewById(R.id.play);
                viewHolder.delete = (ImageView) convertView.findViewById(R.id.delete);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            MeidaType meidaType = meidaTypeList.get(position);
            if (meidaType.type.equals("1")) {//图片
                viewHolder.play.setImageBitmap(meidaType.bitmap);
            } else {
                viewHolder.play.setImageResource(R.drawable.voice_play);
            }
            viewHolder.delete.setTag(meidaType.pathString);
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tag = (String) v.getTag();
                    int index = -1;
                    MeidaType curType = null;
                    for (int i = 0; i < meidaTypeList.size(); i++) {
                        if (tag.equals(meidaTypeList.get(i).pathString)) {
                            curType = meidaTypeList.get(i);
                            index = i;

                            break;
                        }
                    }
                    if (curType != null && index >= 0 && curType.type.equals("2")) {
                        RecordMediaPlayer player = RecordMediaPlayer.getInstance();
                        player.deleteFile(curType.pathString);
                    }
                    meidaTypeList.remove(index);
                    adapter.notifyDataSetChanged();


                }
            });

            return convertView;
        }

    }

    class ViewHolder {
        public ImageView play;
        public ImageView delete;
    }
}


