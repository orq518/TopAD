package com.topad.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.topad.R;
import com.topad.view.interfaces.IRecordFinish;

import java.io.IOException;

/**
 * The author 欧瑞强 on 2015/10/28.
 * todo
 */
public class RecordTools {
    private AudioRecorder mr;
    private MediaPlayer mediaPlayer;
    private Dialog dialog;
    private static int MAX_TIME = 120;
    private static int MIX_TIME = 5;

    private static int RECORD_NO = 0;
    private static int RECORD_ING = 1;
    private static int RECODE_ED = 2;

    private static int RECODE_STATE = 0;

    private static float recodeTime = 0.0f;
    private static double voiceValue = 0.0;
    private ImageView dialog_img;// ֵ
    private Thread recordThread;
    Context mContext;
    IRecordFinish mRecordFinish;

    public RecordTools(Context context,IRecordFinish recordFinish) {
        mContext = context;
        mRecordFinish = recordFinish;
    }

    /**
     * //     * 搜索以前的录音，删除
     * //
     */
//    void scanOldFile() {
//        File file = new File(Environment
//                .getExternalStorageDirectory(), pathString);
//        if (file.exists()) {
//            file.delete();
//        }
//    }
    TextView tv_time;


    /**
     * 弹出录音的dialog
     */
    public void showVoiceDialog() {
        dialog = new Dialog(mContext, R.style.DialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.my_dialog);
        dialog_img = (ImageView) dialog.findViewById(R.id.dialog_img);
        tv_time = (TextView) dialog.findViewById(R.id.tv_time);
        final Button mConfirmBtn = (Button) dialog.findViewById(R.id.mConfirmBtn);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RECODE_STATE != RECORD_ING) {
                    mConfirmBtn.setText("停止录音");
//                    scanOldFile();
                    mr = new AudioRecorder("voice");
                    RECODE_STATE = RECORD_ING;
                    try {
                        mr.start();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    mythread();
                } else if (RECODE_STATE == RECORD_ING) {
                    mConfirmBtn.setText("开始录音");
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
                        showWarnToast("时间不能少于"+MIX_TIME+"s");
//                        record.setText("按住说话");
                        RECODE_STATE = RECORD_NO;
                    }else  if (recodeTime > MAX_TIME) {
                        showWarnToast("时间不能大于"+MAX_TIME+"s");
//                        record.setText("按住说话");
                        RECODE_STATE = RECORD_NO;
                    } else {
                        showWarnToast("录音成功");
                        mRecordFinish.RecondSuccess(mr.getPath());
//                        record.setText("按住说话");
                    }
                }
            }
        });
        Button mCancelBtn = (Button) dialog.findViewById(R.id.mCancelBtn);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog_img = (ImageView) dialog.findViewById(R.id.dialog_img);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (RECODE_STATE == RECORD_ING) {
                    mConfirmBtn.setText("开始录音");
                    RECODE_STATE = RECODE_ED;
                    try {
                        mr.stop();
                        voiceValue = 0.0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        dialog.show();
    }

    void showWarnToast(String tips) {
        Toast toast = new Toast(mContext);
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(20, 20, 20, 20);

        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.drawable.voice_to_short);

        TextView mTv = new TextView(mContext);
        mTv.setText(tips);
        mTv.setTextSize(14);
        mTv.setTextColor(Color.WHITE);
        //mTv.setPadding(0, 10, 0, 0);
        linearLayout.addView(imageView);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.addView(mTv,layoutParams);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundResource(R.drawable.record_bg);

        toast.setView(linearLayout);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
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

    float lastrecodeTime;
    private Runnable ImgThread = new Runnable() {

        @Override
        public void run() {
            recodeTime = 0.0f;
            lastrecodeTime = 0;
            while (RECODE_STATE == RECORD_ING) {
                if (recodeTime >= MAX_TIME && MAX_TIME != 0) {
                    imgHandle.sendEmptyMessage(0);
                } else {
                    try {
                        Thread.sleep(200);
                        recodeTime += 0.2;
                        LogUtil.d("ouou", "recodeTime" + recodeTime);
                        if (RECODE_STATE == RECORD_ING) {
                            voiceValue = mr.getAmplitude();
                            imgHandle.sendEmptyMessage(1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (recodeTime - lastrecodeTime >= 1) {
                    imgHandle.sendEmptyMessage(2);
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

                            if (recodeTime < MIX_TIME) {
                                showWarnToast("时间不能小于"+MAX_TIME+"s");
//                                record.setText("");
                                RECODE_STATE = RECORD_NO;
                            }else if (recodeTime >MAX_TIME) {
                                showWarnToast("时间不能大于"+MAX_TIME+"s");
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
                    case 2:
                        lastrecodeTime = recodeTime;
                        int time = (int) recodeTime;
                        int minute = time / 60;
                        int second = time % 60;
                        String minuteString = "" + minute;
                        if (minuteString.length() < 2) {
                            minuteString = "0" + minuteString;
                        }
                        String secondString = "" + second;
                        if (secondString.length() < 2) {
                            secondString = "0" + secondString;
                        }
                        tv_time.setText(minuteString + ":" + secondString);
                        break;
                    default:
                        break;
                }

            }
        };
    };


}
