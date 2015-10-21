package com.topad.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by njf on 15-10-20.
 * 版本更新实体类
 */
public class VersionUpdateUtil {

    private Dialog updateDialog;

    public void doUpdate(final Activity context) {

//        new VersionUpdateModel().getData(context, new NetCallBack() {
//            @Override
//            public <T> void onSuccess(T t) {
//                if (t instanceof VersionUpdateBean){
//                    final VersionUpdateBean date = (VersionUpdateBean)t;
//                    if (date.isNewUpdateVersion == 1){
//                        //有版本更新
//                        if (date.isForceUpdateVersion == 1){
//                            //强制升级
//                            final Dialog dialog = new Dialog(context,
//                                    R.style.noTitleDialog);
//                            DialogContentView contentView = new DialogContentView(
//                                    context);
//                            contentView.setTitle("发现新版本");
//                            contentView.setMessage(date.version_tip);
//                            contentView.setOkListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    goToUpdate(context, date.updateVersionUrl);
//                                    dialog.dismiss();
//                                }
//                            }, "更新");
//
//                            dialog.setContentView(contentView.getView());
//                            dialog.setCancelable(false);
//                            dialog.show();
//                        }
//                        else{
//                            //建议升级
//                            updateDialog = DialogManager.createAlertDialog(context,
//                                    0, "发现新版本", date.version_tip,
//                                    new DialogManager.AlertDialogListener() {
//                                        @Override
//                                        public void onDialogOK(int id) {
//                                            updateDialog.dismiss();
//                                            goToUpdate(context, date.updateVersionUrl);
//                                        }
//
//                                        @Override
//                                        public void onDialogCancel(int id) {
//                                            // TODO Auto-generated method stub
//                                            updateDialog.dismiss();
//                                        }
//                                    });
//                            updateDialog.setCancelable(false);
//                            updateDialog.show();
//                        }
//                    }
//                    else{
//                        Utils.showToast(context,"当前已经是最新版本");
//                    }
//                }
//            }
//
//            @Override
//            public <T> void onFail(T t) {
//                if (t instanceof BaseBean){
//                    BaseBean date = (BaseBean)t;
//                    Utils.showToast(context, date.getRespErrorMsg());
//                }
//            }
//        });
    }

    /**
     * 跳转下载页，更新应用
     * @param url 更新链接
     */
    public static void goToUpdate(Context context,String url){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }
}
