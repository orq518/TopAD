package com.topad.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.topad.R;
import com.topad.util.LogUtil;
import com.topad.util.SystemBarTintManager;
import com.topad.util.Utils;
import com.topad.view.customviews.CircleProgressDialog;
import com.topad.view.customviews.TitleView;

import org.apache.http.util.EncodingUtils;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends BaseActivity {

    private TitleView mTitleView;
    private String mUrl;
    private WebView mWebView;
    protected CircleProgressDialog mReadingProgress;

    private String method = "get";// 访问方式默认为get
    String title;

    /**
     * @param context
     * @param url     web page地址
     * @param title   页面标题
     * @param method  http访问方法
     * @param data    网络访问附带参数， 例如 post 访问需要 body
     */
    public static void LaunchSelf(Context context, String url, String title, String method, String data) {
        Intent intent = new Intent();
        intent.setClass(context, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("method", method);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    /**
     * @param context
     * @param url     web page地址
     * @param title   页面标题
     */
    public static void LaunchSelf(Context context, String url, String title) {
        LogUtil.d("url:" + url);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("method", "get");
        intent.putExtra("data", "");
        context.startActivity(intent);
    }
    @Override
    public int setLayoutById() {
        return R.layout.webview_activity;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }
    /** 沉浸式状态栏 **/
    private SystemBarTintManager mTintManager;
    private void applySelectedColor() {
        int color = Color.argb(0, Color.red(0), Color.green(0), Color.blue(0));
        mTintManager.setTintColor(color);
    }
    @Override
    public void initViews() {
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        applySelectedColor();

        title = getIntent().getStringExtra("title");
        mTitleView = (TitleView) findViewById(R.id.title);
        mTitleView.setTitle(title);
        mTitleView.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });


        mReadingProgress = new CircleProgressDialog(this, R.style.loading_dialog);
        if (mReadingProgress != null) {
            mReadingProgress.setMessage("正在加载，请稍候...");
            mReadingProgress.setCancelable(true);
            mReadingProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mWebView.stopLoading();
                }
            });
        }

        mWebView = (WebView) findViewById(R.id.webview);

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                LogUtil.d("地址shouldOverrideUrlLoading:"+url);
//                hongdouh5andnative://showanimation?plusrate=2&time=1&finalrate=10.4


                    mWebView.loadUrl(url);

                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                handler.proceed(); // 忽略证书
            }

            public void onPageStarted(WebView view, String url, Bitmap b) {
                super.onPageStarted(view, url, b);
                if (!Utils.isNetworkConnected(WebViewActivity.this)) {
                    // 无网络
                    if (mReadingProgress != null) {
                        mReadingProgress.dismiss();
                        mReadingProgress = null;
                    }
                } else {
                    if (mReadingProgress == null) {
                        mReadingProgress = new CircleProgressDialog(WebViewActivity.this, R.style.loading_dialog);
                        mReadingProgress.setMessage("正在加载，请稍候...");
                        mReadingProgress.setCancelable(true);
                        mReadingProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {

                            @Override
                            public void onCancel(DialogInterface dialog) {
                                mWebView.stopLoading();
                            }
                        });
                        mReadingProgress.show();
                    }
                }

            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mReadingProgress != null) {
                    mReadingProgress.dismiss();
                    mReadingProgress = null;

                }

            }

            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                if (mReadingProgress != null) {
                    mReadingProgress.dismiss();
                    mReadingProgress = null;
                }
            }
        });

        WebChromeClient mwebclient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                LogUtil.d("ouou", "^^^title:" + title);
                if (!Utils.isEmpty(title)) {
                    mTitleView.setTitle(title);
                } else {
                    if(title!=null) {
                        mTitleView.setTitle(title);
                    }else{
                        mTitleView.setTitle("");
                    }
                }

            }

        };
        // 设置setWebChromeClient对象
        mWebView.setWebChromeClient(mwebclient);

        mWebView.getSettings().setJavaScriptEnabled(true);
        WebSettings ws = mWebView.getSettings();
        ws.setAllowFileAccess(true);// 设置允许访问文件数据
        // 设置是否可缩放
        ws.setBuiltInZoomControls(true);
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
//		String cachPath = this.getCacheDir().getAbsolutePath() + "/";
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        LogUtil.d("ouou", "文件路径："+dir);
        ws.setAppCacheEnabled(true);
        ws.setAppCachePath(dir);
        ws.setDatabaseEnabled(true);
        ws.setDatabasePath(dir);
        ws.setDomStorageEnabled(true);
        ws.setGeolocationEnabled(true);
        ws.setGeolocationDatabasePath(dir);
        // mWebView.setBackgroundColor(0x00000000);
        // 设置滚动条样式，解决网页白边的bug
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        if (!Utils.isNetworkConnected(this)) {
            // 无网络
            Utils.showToast(this, this.getString(R.string.no_connection));
            mWebView.setVisibility(View.GONE);
            if (mReadingProgress != null) {
                mReadingProgress.dismiss();
                mReadingProgress = null;
            }
        } else {
            mWebView.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void initData() {
        method = getIntent().getStringExtra("method");
        mUrl = getIntent().getStringExtra("url");
//        mUrl="http://sale.jd.com/app/act/V0i4OHg6I3.html";
        LogUtil.d("mUrl:" + mUrl);
        if ("post".equals(method)) {
            byte[] postBytes = EncodingUtils.getBytes(getIntent().getStringExtra("data"), "BASE64");
            mWebView.postUrl(mUrl, postBytes);
        } else {
            // 加上header
//			mWebView.loadUrl(mUrl, CommonUtil.getWebViewHttpHeaders(getContext()));
//			mUrl = "http://www.baidu.com";	//for test
            mWebView.loadUrl(mUrl);
            if (mReadingProgress != null) {
                mReadingProgress.show();
            }
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            mTitleView.setTitle(getIntent().getStringExtra("title"));
        } else {
            finish();
        }
    }

}
