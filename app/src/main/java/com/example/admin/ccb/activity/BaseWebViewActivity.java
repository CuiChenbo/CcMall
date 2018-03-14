package com.example.admin.ccb.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.admin.ccb.R;
import com.example.admin.ccb.utils.ToastUtils;

/**
 * @Author ccb
 * Created by zz on 2017/6/5 10:29.
 * 　　class explain:
 * 　　　　update:       upAuthor:      explain:
 */

public class BaseWebViewActivity extends BaseActivity {
    private ProgressBar progressBar;


    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})

    @Override
    public int getContentViewResource() {
       return R.layout.activity_base_webview;
    }
   private String loadUrl;
    @Override
    public void initView() {

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        WebView webView = (WebView) findViewById(R.id.webView);
        loadUrl = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(loadUrl)){loadUrl = "https://github.com/CuiChenbo/CcMall";}
        webView.loadUrl(loadUrl);
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavaScriptObject(this), "android"); //和h5的js调用
        //不使用缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                UpTitle(view.getTitle());
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initList() {

    }

    public class JavaScriptObject{
        public JavaScriptObject(Activity activity) {

        }
        @JavascriptInterface
        public void setToken(String token){
        }
        @JavascriptInterface
        public void definedShare(String ShareJson){
        }
    }

    class MyWebChromeClient extends WebChromeClient {

        /**
         * 处理加载进度
         *
         * @param view
         * @param newProgress
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(newProgress);
            if (progressBar.getProgress() == progressBar.getMax()) {
                progressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }

        /**
         * alert弹框
         *
         * @return
         */
        @Override
        public boolean onJsAlert(WebView view, String url, final String message, JsResult result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast(mContext,message);
                }
            });
            result.confirm();
            return true;
        }
    }
}
