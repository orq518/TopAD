package com.topad.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import com.topad.R;


public class ProgressBarUtils {

    private static Dialog loadingDialog;
    private static View view;

    /**
     * @param ac
     * @param allowCancel 是否可以取消
     */
    public static Dialog startProgressDialog(Context ac, boolean allowCancel) {

        if (loadingDialog != null && loadingDialog.isShowing()) {
            return loadingDialog;
        } else {
            loadingDialog = createProgressDialog(ac, allowCancel);
        }
        return loadingDialog;
    }

    private static Dialog createProgressDialog(Context ac, boolean allowCancel) {
        Dialog dlg = new AlertDialog.Builder(ac).create();
        dlg.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dlg.setCancelable(false);
        dlg.show();
        View layout = View.inflate(ac, R.layout.loadingdialog, null);
        dlg.getWindow().setContentView(layout);
        dlg.setCancelable(allowCancel);
        return dlg;
    }


    public static void closeProgressDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public static boolean isDialogShowing() {
        return (loadingDialog != null && loadingDialog.isShowing());
    }
}



