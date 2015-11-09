package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.topad.R;
import com.topad.bean.SearchItemBean;
import com.topad.bean.SearchListBean;
import com.topad.util.LogUtil;
import com.topad.util.RecordMediaPlayer;
import com.topad.util.RecordTools;
import com.topad.util.SystemBarTintManager;
import com.topad.util.Utils;
import com.topad.view.customviews.TitleView;
import com.topad.view.interfaces.IRecordFinish;

import java.util.ArrayList;

/**
 * 主界面
 */
public class SearchActivity extends BaseActivity implements IRecordFinish, View.OnClickListener {

    /**
     * title布局
     **/
    private TitleView mTitle;
    LinearLayout search_selected_layout, layout_voice, layout_keyboard, outdoor_search_item;
    int searchType;
    Button record;
    ImageView add_type;
    TextView locationTV;
    /**
     * 沉浸式状态栏
     **/
    private SystemBarTintManager mTintManager;
    /**
     * 选择好的条件
     */
    ArrayList<SearchItemBean> itemBeans = new ArrayList<SearchItemBean>();
    LinearLayout voice_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    private void applySelectedColor() {
        int color = Color.argb(0, Color.red(0), Color.green(0), Color.blue(0));
        mTintManager.setTintColor(color);
    }

    @Override
    public int setLayoutById() {
        return R.layout.activity_outdoor_search;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    /**
     * 当前选择的临时条件
     */
    SearchItemBean curItem = new SearchItemBean();

    LinearLayout outdoor_search_layout = null;
    Context mContext;

    @Override
    public void initViews() {
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        applySelectedColor();


        searchType = getIntent().getIntExtra("searchtype", 0);
        LogUtil.d("ouou", "searchType:" + searchType);
        // 顶部布局
        mTitle = (TitleView) findViewById(R.id.title);
        // 设置顶部布局
        switch (searchType) {
            case 0://电视
                mTitle.setTitle("电视搜索");
                break;
            case 1://广播
                mTitle.setTitle("广播搜索");
                break;
            case 2://报纸
                mTitle.setTitle("报纸搜索");
                break;
            case 3://户外
                mTitle.setTitle("户外搜索");
                break;
            case 4://杂志
                mTitle.setTitle("杂志搜索");
                break;
            case 5://网络
                mTitle.setTitle("网络搜索");
                break;
        }

        mTitle.setLeftClickListener(new TitleLeftOnClickListener());
        add_type = (ImageView) findViewById(R.id.add_type);
        add_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加新的搜素条件
                if (search_selected_layout.getChildCount() >= 3) {
                    Toast.makeText(mContext, "您的搜索条件不能超过3个", Toast.LENGTH_SHORT).show();
                    return;
                }

                switch (searchType) {
                    case 0://电视
                    case 1://广播
                        EditText tv_type = (EditText) outdoor_search_layout.findViewById(R.id.tv_name);
                        curItem.name = tv_type.getText().toString();
                        EditText tv_program_name = (EditText) outdoor_search_layout.findViewById(R.id.tv_program_name);
                        curItem.type = tv_program_name.getText().toString();
                        if (Utils.isEmpty(curItem.name)) {
                            Toast.makeText(mContext, "请输入电视台名称", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (Utils.isEmpty(curItem.type)) {
                            Toast.makeText(mContext, "请输入栏目名称", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!Utils.isEmpty(curItem.name) && !Utils.isEmpty(curItem.type)) {
                            SearchItemBean bean = new SearchItemBean();
                            bean.locaion = curItem.locaion;
                            bean.name = curItem.name;
                            bean.type = curItem.type;
                            bean.voice = curItem.voice;
                            itemBeans.add(bean);

                            refreshSelectedData();
                            tv_type.setText("");
                            tv_program_name.setText("");
                            curItem.name = "";
                            curItem.type = "";
                        }


                        break;
                    case 2://报纸
                    case 4://杂志
                    case 5://网络
                        EditText et_name = (EditText) outdoor_search_layout.findViewById(R.id.et_name);
                        curItem.name = et_name.getText().toString();
                        if (Utils.isEmpty(curItem.name)) {
                            Toast.makeText(mContext, "请输入报纸名称", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!Utils.isEmpty(curItem.name) && !Utils.isEmpty(curItem.type)) {
                            SearchItemBean bean = new SearchItemBean();
                            bean.locaion = curItem.locaion;
                            bean.name = curItem.name;
                            bean.type = curItem.type;
                            bean.voice = curItem.voice;
                            itemBeans.add(bean);

                            refreshSelectedData();

                            TextView city = (TextView) outdoor_search_layout.findViewById(R.id.city_name);
//                            TextView media_name = (TextView) outdoor_search_layout.findViewById(R.id.media_name);
                            TextView media_type = (TextView) outdoor_search_layout.findViewById(R.id.media_type);
                            media_type.setText("");
                            et_name.setText("");
                            curItem.name = "";
                            curItem.type = "";
                        }


                        break;
                    case 3://户外
                        if (!Utils.isEmpty(curItem.name) && !Utils.isEmpty(curItem.type)) {
                            SearchItemBean bean = new SearchItemBean();
                            bean.locaion = curItem.locaion;
                            bean.name = curItem.name;
                            bean.type = curItem.type;
                            bean.voice = curItem.voice;
                            itemBeans.add(bean);

                            refreshSelectedData();

//                            locationTV = (TextView) outdoor_search_layout.findViewById(R.id.city_name);
                            TextView media_name = (TextView) outdoor_search_layout.findViewById(R.id.media_name);
                            TextView media_type = (TextView) outdoor_search_layout.findViewById(R.id.media_type);
                            media_name.setText("媒体名称");
                            media_type.setText("媒体类型");
                            locationTV.setText("选择城市");
                            curItem.name = "";
                            curItem.type = "";
                        }

                        break;

                }
                layout_keyboard.setVisibility(View.VISIBLE);

            }
        });
        search_selected_layout = (LinearLayout) findViewById(R.id.search_selected_layout);
        outdoor_search_item = (LinearLayout) findViewById(R.id.outdoor_search_item);


        switch (searchType) {
            case 0://电视
            case 1://广播
                outdoor_search_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.tv_search_item, null);
                break;
            case 2://报纸
            case 4://杂志
            case 5://网络
                outdoor_search_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.baozhi_search_item, null);
                break;
            case 3://户外
                outdoor_search_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.outdoor_search_item, null);
                locationTV = (TextView) outdoor_search_layout.findViewById(R.id.city_name);
                break;

        }
        outdoor_search_item.addView(outdoor_search_layout);

        layout_voice = (LinearLayout) findViewById(R.id.layout_voice);
        layout_keyboard = (LinearLayout) findViewById(R.id.layout_keyboard);
        record = (Button) findViewById(R.id.record_bt);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordTools recordTools = new RecordTools(mContext, SearchActivity.this);
                recordTools.showVoiceDialog();
            }
        });
        voice_layout = (LinearLayout) findViewById(R.id.voice_layout);
    }

    @Override
    public void initData() {

    }


    /**
     * 刷新选择好的条目
     */
    public void refreshSelectedData() {
        search_selected_layout.removeAllViews();
        for (int i = 0; i < itemBeans.size(); i++) {
            SearchItemBean itembean = itemBeans.get(i);
            switch (searchType) {
                case 0://电视
                case 1://广播
                    RelativeLayout tv_search_selected = (RelativeLayout) getLayoutInflater().inflate(R.layout.other_search_selected_item, null);
                    TextView tvName = (TextView) tv_search_selected.findViewById(R.id.city);
                    TextView tv_Type = (TextView) tv_search_selected.findViewById(R.id.media_name);
                    ImageView tv_delete = (ImageView) tv_search_selected.findViewById(R.id.delete);
                    tvName.setText(itembean.name);
                    tv_Type.setText(itembean.type);
                    tv_search_selected.setTag("" + i);
                    tv_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RelativeLayout fatherLayout = (RelativeLayout) v.getParent();
                            search_selected_layout.removeView(fatherLayout);
                            int index = Integer.parseInt((String) fatherLayout.getTag());
                            itemBeans.remove(index);
                            refreshSelectedData();
                        }
                    });
                    search_selected_layout.addView(tv_search_selected);
                    break;
                case 4://杂志
                case 5://网络
                case 2://报纸
                    RelativeLayout search_selected = (RelativeLayout) getLayoutInflater().inflate(R.layout.other_search_selected_item, null);
                    TextView baizhiName = (TextView) search_selected.findViewById(R.id.city);
                    TextView media_Type = (TextView) search_selected.findViewById(R.id.media_name);
                    ImageView delete2 = (ImageView) search_selected.findViewById(R.id.delete);
                    baizhiName.setText(itembean.name);
                    media_Type.setText(itembean.type);
                    search_selected.setTag("" + i);
                    delete2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RelativeLayout fatherLayout = (RelativeLayout) v.getParent();
                            search_selected_layout.removeView(fatherLayout);
                            int index = Integer.parseInt((String) fatherLayout.getTag());
                            itemBeans.remove(index);
                            refreshSelectedData();
                        }
                    });
                    search_selected_layout.addView(search_selected);
                    break;
                case 3://户外

                    RelativeLayout outdoor_search_selected = (RelativeLayout) getLayoutInflater().inflate(R.layout.outdoor_search_selected_item, null);
                    TextView city = (TextView) outdoor_search_selected.findViewById(R.id.city);
                    TextView media_name = (TextView) outdoor_search_selected.findViewById(R.id.media_name);
                    TextView media_type = (TextView) outdoor_search_selected.findViewById(R.id.media_type);
                    ImageView delete = (ImageView) outdoor_search_selected.findViewById(R.id.delete);
                    media_name.setText(itembean.name);
                    media_type.setText(itembean.type);
                    outdoor_search_selected.setTag("" + i);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RelativeLayout fatherLayout = (RelativeLayout) v.getParent();
                            search_selected_layout.removeView(fatherLayout);
                            int index = Integer.parseInt((String) fatherLayout.getTag());
                            itemBeans.remove(index);
                            refreshSelectedData();
                        }
                    });
                    search_selected_layout.addView(outdoor_search_selected);
//                    outdoor_search_item.setVisibility(View.GONE);


                    break;

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    int OUTDOORLIST = 1;
    int BAOZHILIST = 2;
    int TVLIST = 3;
    final int PICKCITY = 121;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OUTDOORLIST && resultCode == RESULT_OK && data != null) {
            //媒体类型
            SearchListBean bean = (SearchListBean) data.getSerializableExtra("data");
            TextView media_name = (TextView) outdoor_search_layout.findViewById(R.id.media_name);
            TextView media_type = (TextView) outdoor_search_layout.findViewById(R.id.media_type);
            media_name.setText(bean.name);
            media_type.setText(bean.type);
            curItem.name = bean.name;
            curItem.type = bean.type;

        } else if (requestCode == BAOZHILIST && resultCode == RESULT_OK && data != null) {
            String mediaType = data.getStringExtra("mediaType");
//            TextView media_name= (TextView) outdoor_search_layout.findViewById(R.id.media_name);
            TextView media_type = (TextView) outdoor_search_layout.findViewById(R.id.media_type);
            media_type.setText(mediaType);
            curItem.type = mediaType;


        } else if (requestCode == PICKCITY && resultCode == RESULT_OK && data != null) {
            if(locationTV!=null) {
                String cityString = data.getStringExtra("city");
                locationTV.setText(cityString);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.city_layout:
                Intent cityIntent = new Intent(SearchActivity.this, CitySelectActivity.class);
                startActivityForResult(cityIntent, PICKCITY);
                break;
            case R.id.select_media_type:
                if (searchType == 3) {//户外
                    Intent intent = new Intent(SearchActivity.this, OutDoorSearchListActivity.class);
                    startActivityForResult(intent, OUTDOORLIST);
                } else {
                    Intent intent = new Intent(SearchActivity.this, OtherSearchListActivity.class);
                    intent.putExtra("searchType", searchType);
                    startActivityForResult(intent, BAOZHILIST);
                }

                break;
            case R.id.ic_voice:
                layout_voice.setVisibility(View.GONE);
                layout_keyboard.setVisibility(View.VISIBLE);
                break;
            case R.id.ic_keyboard:
                layout_keyboard.setVisibility(View.GONE);
                layout_voice.setVisibility(View.VISIBLE);
                record.setText("按住说话");
                break;
//            case R.id.newspaper_layout://报纸
//                break;
//            case R.id.outdoor_layout://户外
//                break;
//            case R.id.magazine_layout://杂志
//                break;
//            case R.id.net_layout://网络
//                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBack() {
        finish();
    }

    @Override
    public void RecondSuccess(final String voicePath) {
        final RelativeLayout voiceLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.media_layout, null);
        voiceLayout.setTag(voicePath);
        ImageView play = (ImageView) voiceLayout.findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordMediaPlayer player = RecordMediaPlayer.getInstance();
                player.play((String) voiceLayout.getTag());
            }
        });
        ImageView delete = (ImageView) voiceLayout.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        voice_layout.addView(voiceLayout);
        layout_keyboard.setVisibility(View.GONE);
    }

    /**
     * 顶部布局--左按钮事件监听
     */
    public class TitleLeftOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            onBack();
        }

    }


}
