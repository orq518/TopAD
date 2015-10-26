package com.topad.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.topad.R;
import com.topad.bean.SearchListBean;
import com.topad.util.AudioRecorder;
import com.topad.util.LogUtil;
import com.topad.view.customviews.TitleView;

import java.io.File;
import java.io.IOException;

/**
 * 主界面
 */
public class OutDoorSearchActivity extends BaseActivity implements View.OnClickListener {

    /**
     * title布局
     **/
    private TitleView mTitle;
    LinearLayout search_selected_layout, layout_voice, layout_keyboard,outdoor_search_item;
    int searchType;
    Button record;
    ImageView add_type;
    private AudioRecorder mr;
    private MediaPlayer mediaPlayer;
    private Dialog dialog;
    private static int MAX_TIME = 15;
    private static int MIX_TIME = 1;

    private static int RECORD_NO = 0;
    private static int RECORD_ING = 1;
    private static int RECODE_ED = 2;

    private static int RECODE_STATE = 0;

    private static float recodeTime = 0.0f;
    private static double voiceValue = 0.0;
    private ImageView dialog_img;// ֵ
    private Thread recordThread;
    String pathString = "ad/voice.amr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutById() {
        return R.layout.activity_outdoor_search;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    @Override
    public void initViews() {
        searchType = getIntent().getIntExtra("searchtype", 0);
        LogUtil.d("ouou", "searchType:" + searchType);
        // 顶部布局
        mTitle = (TitleView) findViewById(R.id.title);
        // 设置顶部布局
        mTitle.setTitle(getString(R.string.main_title));
        mTitle.setLeftClickListener(new TitleLeftOnClickListener());
        add_type= (ImageView) findViewById(R.id.add_type);
        add_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_selected_layout.getChildCount()<3) {
                    outdoor_search_item.setVisibility(View.VISIBLE);
                }

            }
        });
        search_selected_layout = (LinearLayout) findViewById(R.id.search_selected_layout);
        outdoor_search_item=(LinearLayout) findViewById(R.id.outdoor_search_item);
        layout_voice = (LinearLayout) findViewById(R.id.layout_voice);
        layout_keyboard = (LinearLayout) findViewById(R.id.layout_keyboard);
        record = (Button) findViewById(R.id.record_bt);
        record.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        record.setText("正在录音");
                        if (RECODE_STATE != RECORD_ING) {
                            scanOldFile();
                            mr = new AudioRecorder("voice");
                            RECODE_STATE = RECORD_ING;
                            showVoiceDialog();
                            try {
                                mr.start();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            mythread();
                        }


                        break;
                    case MotionEvent.ACTION_UP:

                        if (RECODE_STATE == RECORD_ING) {
                            RECODE_STATE = RECODE_ED;
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            try {
                                mr.stop();
                                voiceValue = 0.0;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (recodeTime < MIX_TIME) {
                                showWarnToast();
                                record.setText("按住说话");
                                RECODE_STATE = RECORD_NO;
                            } else {
                                record.setText("按住说话");
                            }
                        }

                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.select_media_type:
                Intent intent = new Intent(OutDoorSearchActivity.this, OutDoorSearchListActivity.class);
                startActivityForResult(intent, OUTDOORLIST);
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

    /**
     * 搜索以前的录音，删除
     */
    void scanOldFile() {
        File file = new File(Environment
                .getExternalStorageDirectory(), pathString);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 弹出录音的dialog
     */
    void showVoiceDialog() {
        dialog = new Dialog(this, R.style.DialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.my_dialog);
        dialog_img = (ImageView) dialog.findViewById(R.id.dialog_img);
        dialog.show();
    }

    void showWarnToast() {
        Toast toast = new Toast(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(20, 20, 20, 20);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.voice_to_short);

        TextView mTv = new TextView(this);
        mTv.setText("时间不能少于1s");
        mTv.setTextSize(14);
        mTv.setTextColor(Color.WHITE);
        //mTv.setPadding(0, 10, 0, 0);

        linearLayout.addView(imageView);
        linearLayout.addView(mTv);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundResource(R.drawable.record_bg);

        toast.setView(linearLayout);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 获取录音路径
     *
     * @return
     */
    private String getAmrPath() {
        File file = new File(Environment
                .getExternalStorageDirectory(), pathString);
        return file.getAbsolutePath();
    }

    /**
     * 创建录音的线程
     */
    void mythread() {
        recordThread = new Thread(ImgThread);
        recordThread.start();
    }

    /**
     * 设置录音的状态图片
     */
    void setDialogImage() {
        if (voiceValue < 200.0) {
            dialog_img.setImageResource(R.drawable.record_animate_01);
        } else if (voiceValue > 200.0 && voiceValue < 400) {
            dialog_img.setImageResource(R.drawable.record_animate_02);
        } else if (voiceValue > 400.0 && voiceValue < 800) {
            dialog_img.setImageResource(R.drawable.record_animate_03);
        } else if (voiceValue > 800.0 && voiceValue < 1600) {
            dialog_img.setImageResource(R.drawable.record_animate_04);
        } else if (voiceValue > 1600.0 && voiceValue < 3200) {
            dialog_img.setImageResource(R.drawable.record_animate_05);
        } else if (voiceValue > 3200.0 && voiceValue < 5000) {
            dialog_img.setImageResource(R.drawable.record_animate_06);
        } else if (voiceValue > 5000.0 && voiceValue < 7000) {
            dialog_img.setImageResource(R.drawable.record_animate_07);
        } else if (voiceValue > 7000.0 && voiceValue < 10000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_08);
        } else if (voiceValue > 10000.0 && voiceValue < 14000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_09);
        } else if (voiceValue > 14000.0 && voiceValue < 17000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_10);
        } else if (voiceValue > 17000.0 && voiceValue < 20000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_11);
        } else if (voiceValue > 20000.0 && voiceValue < 24000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_12);
        } else if (voiceValue > 24000.0 && voiceValue < 28000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_13);
        } else if (voiceValue > 28000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_14);
        }
    }

    private Runnable ImgThread = new Runnable() {

        @Override
        public void run() {
            recodeTime = 0.0f;
            while (RECODE_STATE == RECORD_ING) {
                if (recodeTime >= MAX_TIME && MAX_TIME != 0) {
                    imgHandle.sendEmptyMessage(0);
                } else {
                    try {
                        Thread.sleep(200);
                        recodeTime += 0.2;
                        Log.i("song", "recodeTime" + recodeTime);
                        if (RECODE_STATE == RECORD_ING) {
                            voiceValue = mr.getAmplitude();
                            imgHandle.sendEmptyMessage(1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Handler imgHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case 0:
                        if (RECODE_STATE == RECORD_ING) {
                            RECODE_STATE = RECODE_ED;
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            try {
                                mr.stop();
                                voiceValue = 0.0;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (recodeTime < 1.0) {
                                showWarnToast();
//                                record.setText("");
                                RECODE_STATE = RECORD_NO;
                            } else {
//                                record.setText("");
                            }
                        }
                        break;
                    case 1:
                        setDialogImage();
                        break;
                    default:
                        break;
                }

            }
        };
    };
    int OUTDOORLIST = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OUTDOORLIST && resultCode == RESULT_OK && data != null) {
            //媒体类型
            SearchListBean bean = (SearchListBean) data.getSerializableExtra("data");

            RelativeLayout outdoor_search_selected = (RelativeLayout) getLayoutInflater().inflate(R.layout.outdoor_search_selected_item, null);
            TextView city= (TextView) outdoor_search_selected.findViewById(R.id.city);
            TextView media_name= (TextView) outdoor_search_selected.findViewById(R.id.media_name);
            TextView media_type= (TextView) outdoor_search_selected.findViewById(R.id.media_type);
            ImageView delete= (ImageView) outdoor_search_selected.findViewById(R.id.delete);
            media_name.setText(bean.name);
            media_type.setText(bean.type);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout fatherLayout = (RelativeLayout) v.getParent();
                    search_selected_layout.removeView(fatherLayout);
                    if (search_selected_layout.getChildCount()==0) {
                        outdoor_search_item.setVisibility(View.VISIBLE);
                    }
                }
            });
            search_selected_layout.addView(outdoor_search_selected);
            outdoor_search_item.setVisibility(View.GONE);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
