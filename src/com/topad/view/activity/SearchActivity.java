package com.topad.view.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.topad.R;
import com.topad.util.AudioRecorder;
import com.topad.util.LogUtil;
import com.topad.view.customviews.TitleView;

import java.io.File;
import java.io.IOException;

/**
 * 主界面
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener{

    /** title布局 **/
    private TitleView mTitle;
    LinearLayout layout_voice,layout_keyboard;
    int searchType;
    Button record;
    private AudioRecorder mr;
    private MediaPlayer mediaPlayer;
    private Dialog dialog;
    private static int MAX_TIME = 15;    //�¼��ʱ�䣬��λ�룬0Ϊ��ʱ������
    private static int MIX_TIME = 1;     //���¼��ʱ�䣬��λ�룬0Ϊ��ʱ�����ƣ�������Ϊ1

    private static int RECORD_NO = 0;  //����¼��
    private static int RECORD_ING = 1;   //����¼��
    private static int RECODE_ED = 2;   //���¼��

    private static int RECODE_STATE = 0;      //¼����״̬

    private static float recodeTime=0.0f;    //¼����ʱ��
    private static double voiceValue=0.0;    //��˷��ȡ������
    private ImageView dialog_img;// ֵ
    private Thread recordThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutById() {
        return R.layout.activity_search;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    @Override
    public void initViews() {
        searchType=getIntent().getIntExtra("searchtype",0);
        LogUtil.d("ouou","searchType:"+searchType);
        // 顶部布局
        mTitle = (TitleView) findViewById(R.id.title);
        // 设置顶部布局
        mTitle.setTitle(getString(R.string.main_title));
        mTitle.setLeftClickListener(new TitleLeftOnClickListener());
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
                                record.setText("不能低于多长时间");
                                RECODE_STATE=RECORD_NO;
                            }else{
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
    //ɾ�����ļ�
    void scanOldFile(){
        File file = new File(Environment
                .getExternalStorageDirectory(), "my/voice.amr");
        if(file.exists()){
            file.delete();
        }
    }

    //¼��ʱ��ʾDialog
    void showVoiceDialog(){
        dialog = new Dialog(this,R.style.DialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.my_dialog);
        dialog_img=(ImageView)dialog.findViewById(R.id.dialog_img);
        dialog.show();
    }

    //¼��ʱ��̫��ʱToast��ʾ
    void showWarnToast(){
        Toast toast = new Toast(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(20, 20, 20, 20);

        // ����һ��ImageView
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.voice_to_short); // ͼ��

        TextView mTv = new TextView(this);
        mTv.setText("ʱ��̫��   ¼��ʧ��");
        mTv.setTextSize(14);
        mTv.setTextColor(Color.WHITE);//������ɫ
        //mTv.setPadding(0, 10, 0, 0);

        // ��ImageView��ToastView�ϲ���Layout��
        linearLayout.addView(imageView);
        linearLayout.addView(mTv);
        linearLayout.setGravity(Gravity.CENTER);//���ݾ���
        linearLayout.setBackgroundResource(R.drawable.record_bg);//�����Զ���toast�ı���

        toast.setView(linearLayout);
        toast.setGravity(Gravity.CENTER, 0,0);//���λ��Ϊ�м�     100Ϊ������100dp
        toast.show();
    }

    //��ȡ�ļ��ֻ�·��
    private String getAmrPath(){
        File file = new File(Environment
                .getExternalStorageDirectory(), "my/voice.amr");
        return file.getAbsolutePath();
    }

    //¼����ʱ�߳�
    void mythread(){
        recordThread = new Thread(ImgThread);
        recordThread.start();
    }

    //¼��DialogͼƬ��������С�л�
    void setDialogImage(){
        if (voiceValue < 200.0) {
            dialog_img.setImageResource(R.drawable.record_animate_01);
        }else if (voiceValue > 200.0 && voiceValue < 400) {
            dialog_img.setImageResource(R.drawable.record_animate_02);
        }else if (voiceValue > 400.0 && voiceValue < 800) {
            dialog_img.setImageResource(R.drawable.record_animate_03);
        }else if (voiceValue > 800.0 && voiceValue < 1600) {
            dialog_img.setImageResource(R.drawable.record_animate_04);
        }else if (voiceValue > 1600.0 && voiceValue < 3200) {
            dialog_img.setImageResource(R.drawable.record_animate_05);
        }else if (voiceValue > 3200.0 && voiceValue < 5000) {
            dialog_img.setImageResource(R.drawable.record_animate_06);
        }else if (voiceValue > 5000.0 && voiceValue < 7000) {
            dialog_img.setImageResource(R.drawable.record_animate_07);
        }else if (voiceValue > 7000.0 && voiceValue < 10000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_08);
        }else if (voiceValue > 10000.0 && voiceValue < 14000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_09);
        }else if (voiceValue > 14000.0 && voiceValue < 17000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_10);
        }else if (voiceValue > 17000.0 && voiceValue < 20000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_11);
        }else if (voiceValue > 20000.0 && voiceValue < 24000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_12);
        }else if (voiceValue > 24000.0 && voiceValue < 28000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_13);
        }else if (voiceValue > 28000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_14);
        }
    }

    //¼���߳�
    private Runnable ImgThread = new Runnable() {

        @Override
        public void run() {
            recodeTime = 0.0f;
            while (RECODE_STATE==RECORD_ING) {
                if (recodeTime >= MAX_TIME && MAX_TIME != 0) {
                    imgHandle.sendEmptyMessage(0);
                }else{
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
                        //¼������15���Զ�ֹͣ
                        if (RECODE_STATE == RECORD_ING) {
                            RECODE_STATE=RECODE_ED;
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
                                record.setText("��ס��ʼ¼��");
                                RECODE_STATE=RECORD_NO;
                            }else{
                                record.setText("¼�����!�������¼��");
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
}
