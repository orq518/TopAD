package com.topad.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.topad.R;
import com.topad.view.customviews.DialogContentView;
import com.topad.view.customviews.UcfDialog;

/**
 * The author 欧瑞强 on 2015/7/28.
 * todo
 */
public class DialogManager {
    /**
     * @param ctx
     * @param msg        title 标题
     * @param msg        dialog 显示的内容
     * @param leftMsg    左侧按钮文案
     * @param rightMsg   右侧按钮文案
     * @param leftClick  左侧点击事件
     * @param rightClick 右侧点击事件
     * @param isCancel   是否可以返回键取消
     */
    public static void showDialog(Context ctx, String title, String msg, String leftMsg,
                                  String rightMsg, final View.OnClickListener leftClick,
                                  final View.OnClickListener rightClick, boolean isCancel,
                                  final View.OnClickListener exitClick) {
        int num = 2; // 两个按钮，我知道了
        if (leftClick == null || rightClick == null) {
            num = 1; // 一个按钮，我知道了
        }
        final UcfDialog dialog = new UcfDialog(ctx, R.style.dialogStyle, num, isCancel);
        if (Utils.isEmpty(title)) {
            dialog.setTitleVisible(false);
        } else {
            dialog.setTitleVisible(true);
            dialog.setTitleText(title);
        }
        if (!Utils.isEmpty(msg)) {
            dialog.setContent(msg);
        } else {
            dialog.setContent("");
        }
        // 两个按钮
        if (num == 2) {
            if (leftClick != null) {
                dialog.setCancelBtn(leftMsg, new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                        leftClick.onClick(arg0);
                    }
                });
            }
            if (rightClick != null) {
                dialog.setConfirmBtn(rightMsg, new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                        rightClick.onClick(arg0);
                    }
                });
            }
        }
        // 一个按钮
        else {
            if (leftClick != null) {
                dialog.setSingleButton(leftMsg, new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                        leftClick.onClick(arg0);
                    }
                });
            }
            if (rightClick != null) {
                dialog.setSingleButton(rightMsg, new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                        rightClick.onClick(arg0);
                    }
                });
            }
        }

        // 退出按钮--显示
        if (exitClick != null) {
            dialog.setExitVisible(true);
            dialog.setExitBtn(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    exitClick.onClick(view);
                }
            });

        }
        // 退出按钮--不显示
        else {
            dialog.setExitVisible(false);
        }
        try {
            dialog.show();
        } catch (Exception e) {
        }

    }

    public static Dialog createAlertDialog(final Activity ac, final int id, String title, String message, final AlertDialogListener listener) {
        final Dialog dialog = new Dialog(ac, R.style.noTitleDialog);
        DialogContentView dialogContentView = new DialogContentView(ac);
        dialogContentView.setTitle(title);
        dialogContentView.setMessage(message);
        dialogContentView.setOkListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (listener != null) {
                    listener.onDialogOK(id);
                }
            }
        }, "确认");

        dialogContentView.setCancelListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (listener != null) {
                    listener.onDialogCancel(id);
                }
            }
        }, "取消");
        dialog.setContentView(dialogContentView.getView());
        return dialog;
    }

    public interface AlertDialogListener {
        public void onDialogOK(int id);

        public void onDialogCancel(int id);
    }
}
