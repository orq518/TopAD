package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.topad.R;
import com.topad.util.RecordMediaPlayer;
import com.topad.util.RecordTools;
import com.topad.util.Utils;
import com.topad.view.customviews.TitleView;
import com.topad.view.interfaces.IRecordFinish;

/**
 * ${todo}<媒体发布>
 *     category＝ 电视－1、广播－2、报纸－3、户外－4、杂志－5、网络－6
 * @author lht
 * @data: on 15/10/30 16:09
 */
public class MediaReleaseActivity extends BaseActivity implements OnClickListener, IRecordFinish {
    private static final String LTAG = MediaReleaseActivity.class.getSimpleName();
    /** 上下文 **/
    private Context mContext;
    /** 顶部布局 **/
    private TitleView mTitleView;
    /** 选择媒体类别 **/
    private RelativeLayout mLaySelectMedia;
    /** 地址 **/
    private RelativeLayout mLayAddressMedia;
    /** 地址 **/
    private RelativeLayout mLayProveMedia;
    /** 语音 **/
    private LinearLayout mLayVoice;
    /** 键盘 **/
    private LinearLayout mLayKeyboard;
    /** 语音播放 **/
    private LinearLayout mVoiceLayout;
    /** 户外 **/
    private EditText mETOutdoor;
    /** 电视台 **/
    private EditText mETTV;
    /** 栏目 **/
    private EditText mETColumn;
    /** 报纸 **/
    private EditText mETNewpager;
    /** 详情 **/
    private EditText mETDetails;
    /** 添加 **/
    private ImageView mETAdd;
    /** 语音 **/
    private ImageView mIVVoice;
    /** 键盘 **/
    private ImageView mIVKeyboard;
    /** 提交 **/
    private Button mETSubmit;
    /** 录音 **/
    private Button mRecord;

    /** 类别 **/
    private String category;


    @Override
    public int setLayoutById() {
        mContext = this;
        return R.layout.activity_media_release;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    @Override
    public void initViews() {
        // 接收数据
        Intent intent = getIntent();
        if (intent != null) {
            category = intent.getStringExtra("category");
        }

        // 顶部标题布局
        mTitleView = (TitleView) findViewById(R.id.title);
        mLaySelectMedia = (RelativeLayout) findViewById(R.id.lay_select_media);
        mLayAddressMedia = (RelativeLayout) findViewById(R.id.lay_address_media);
        mLayProveMedia = (RelativeLayout) findViewById(R.id.lay_prove_media);
        mLayVoice = (LinearLayout) findViewById(R.id.layout_voice);
        mLayKeyboard = (LinearLayout) findViewById(R.id.layout_keyboard);
        mVoiceLayout = (LinearLayout) findViewById(R.id.voice_layout);
        mETOutdoor = (EditText) findViewById(R.id.et_outdoor);
        mETTV = (EditText) findViewById(R.id.et_tv);
        mETColumn = (EditText) findViewById(R.id.et_column);
        mETNewpager = (EditText) findViewById(R.id.et_newpager);
        mETDetails = (EditText) findViewById(R.id.et_details);
        mETAdd = (ImageView) findViewById(R.id.iv_add);
        mIVVoice = (ImageView) findViewById(R.id.ic_voice);
        mIVKeyboard = (ImageView) findViewById(R.id.ic_keyboard);
        mETSubmit = (Button) findViewById(R.id.bt_submit_release);
        mRecord = (Button) findViewById(R.id.record_bt);

        if(!Utils.isEmpty(category)){
            if(category.equals("1")){
                mTitleView.setTitle("电视媒体发布");
                mETOutdoor.setVisibility(View.GONE);
                mETTV.setVisibility(View.VISIBLE);
                mETColumn.setVisibility(View.VISIBLE);
                mETNewpager.setVisibility(View.GONE);
            }else if(category.equals("2")){
                mTitleView.setTitle("广播媒体发布");
                mETOutdoor.setVisibility(View.GONE);
                mETTV.setVisibility(View.VISIBLE);
                mETColumn.setVisibility(View.VISIBLE);
                mETNewpager.setVisibility(View.GONE);
            }else if(category.equals("3")){
                mTitleView.setTitle("报纸媒体发布");
                mETOutdoor.setVisibility(View.GONE);
                mETTV.setVisibility(View.GONE);
                mETColumn.setVisibility(View.GONE);
                mETNewpager.setVisibility(View.VISIBLE);
            }else if(category.equals("4")){
                mTitleView.setTitle("户外媒体发布");
                mETOutdoor.setVisibility(View.VISIBLE);
                mETTV.setVisibility(View.GONE);
                mETColumn.setVisibility(View.GONE);
                mETNewpager.setVisibility(View.GONE);

            }else if(category.equals("5")){
                mTitleView.setTitle("杂志媒体发布");
                mETOutdoor.setVisibility(View.GONE);
                mETTV.setVisibility(View.GONE);
                mETColumn.setVisibility(View.GONE);
                mETNewpager.setVisibility(View.VISIBLE);
            }else if(category.equals("6")){
                mTitleView.setTitle("网络媒体发布");
                mETOutdoor.setVisibility(View.GONE);
                mETTV.setVisibility(View.GONE);
                mETColumn.setVisibility(View.GONE);
                mETNewpager.setVisibility(View.VISIBLE);
            }
        }
        mTitleView.setLeftClickListener(new TitleLeftOnClickListener());


        mLayVoice.setOnClickListener(this);
        mLayKeyboard.setOnClickListener(this);
        mLaySelectMedia.setOnClickListener(this);
        mLayAddressMedia.setOnClickListener(this);
        mLayProveMedia.setOnClickListener(this);
        mETAdd.setOnClickListener(this);
        mETSubmit.setOnClickListener(this);
        mIVVoice.setOnClickListener(this);
        mIVKeyboard.setOnClickListener(this);
        mRecord.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    /**
     * 录音成功
     * @param voicePath
     */
    @Override
    public void RecondSuccess(String voicePath) {
        final RelativeLayout voiceLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.media_layout, null);
        voiceLayout.setTag(voicePath);
        ImageView play= (ImageView) voiceLayout.findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordMediaPlayer player = RecordMediaPlayer.getInstance();
                player.play((String) voiceLayout.getTag());
            }
        });
        ImageView delete= (ImageView) voiceLayout.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mVoiceLayout.addView(voiceLayout);
        mLayKeyboard.setVisibility(View.GONE);
    }


    /**
     * 顶部布局--左按钮事件监听
     */
    public class TitleLeftOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            // 选择媒体类别
            case R.id.lay_select_media:

                break;
            // 地址-- 定位进入地图
            case R.id.lay_address_media:
                Intent intent = new Intent(MediaReleaseActivity.this, LocationMapActivity.class);
                startActivity(intent);
                break;

            // 键盘
            case R.id.ic_keyboard:
                mLayKeyboard.setVisibility(View.GONE);
                mLayVoice.setVisibility(View.VISIBLE);
                mRecord.setText("按住说话");
                break;

            // 证明
            case R.id.lay_prove_media:

                break;

            // 语音
            case R.id.ic_voice:
                mLayVoice.setVisibility(View.GONE);
                mLayKeyboard.setVisibility(View.VISIBLE);
                break;

            // 录音
            case R.id.record_bt:
                RecordTools recordTools = new RecordTools(mContext, MediaReleaseActivity.this);
                recordTools.showVoiceDialog();
                break;

            // 添加
            case R.id.iv_add:

                break;

            // 提交
            case R.id.bt_submit_release:

                break;

            default:
                break;
        }
    }
}
