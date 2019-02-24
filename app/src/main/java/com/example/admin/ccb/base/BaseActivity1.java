package com.example.admin.ccb.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.admin.ccb.R;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

import www.ccb.com.common.utils.LogUtils;
import www.ccb.com.common.utils.ToastUtils;

public abstract class BaseActivity1 extends AppCompatActivity {

    public Context mContext;
    private Gson gson;
    public Bundle savedInstanceState;
    public ImmersionBar mImmersionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResource());
        this.savedInstanceState = savedInstanceState;
//        mImmersionBar = ImmersionBar.with(this);
//        mImmersionBar.init();  //所有子类都将继承这些相同的属性
        mContext = this;
        gson = new Gson();
        initView();
        initData();
        initList();
    }

    public abstract int getContentViewResource();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initList();
    public void UpTitle(String title){
        if (findViewById(R.id.vBar) == null)return;
//        mImmersionBar.titleBar(R.id.vBar).statusBarDarkFont(true, 0.2f).init();
        findViewById(R.id.tvTitleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ((TextView)findViewById(R.id.tvTitleBar)).setText(title == null?"":title);
    }

    public void okGetRequest(final String url){
        OkGo.<String>get(url).execute(new StringCallback() {
            @Override
            public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                super.onStart(request);
            }

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                    okResponseSuccess(url, response.body());
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                okResponseError(url, response.body());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                okResponseFinish(url);
            }
        });
    }

    /**
     * OK网络请求 不需要传mobileLogin和JSESSIONID
     *
     * @param httpurl      请求URl 也用来标记
     * @param params       请求参数
     * @param clazz        返回的Bean对象
     * @param DialogMsg    弹出Dialog的文字消息
     * @param isShowDialog 是否弹出Dialog 默认弹出
     */
    public void okPostRequest(final String what, final String httpurl, HttpParams params, final Class clazz, final String DialogMsg, final boolean isShowDialog) {
        final String url = httpurl + "";
        params.put("mobileLogin", true);
        OkGo.<String>post(url).params(params).execute(new StringCallback() {
            @Override
            public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                super.onStart(request);
//                if (isShowDialog) showCbDialog(DialogMsg);
                okResponseStart(httpurl + what);
            }

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                LogUtils.out(httpurl + what + "请求结果:__", response.body());
                if (clazz == null) {
                    okResponseSuccess(httpurl + what, response.body());
                } else {
                    try {
                        Object bean = gson.fromJson(response.body(), clazz);
                        okResponseSuccess(httpurl + what, bean);
                    } catch (Exception e) {
                        okResponseSuccess(httpurl + what, null);
                        ToastUtils.GsonExtremely();
                        LogUtils.e("异常信息：" + e.toString());
                    }
                }
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                LogUtils.out(httpurl + what + "请求结果:__", response.body());
                okResponseError(httpurl + what, response.body());
                ToastUtils.failNetRequest();
            }

            @Override
            public void onFinish() {
                super.onFinish();
//                if (isShowDialog) dismissCbDialog();
                okResponseFinish(httpurl + what);
            }
        });
    }

    /**
     * OK网络请求成功回调
     *
     * @param whit 请求标记
     * @param t    返回结果
     */
    protected void okResponseSuccess(String whit, Object t) {
    }

    /**
     * OK网络请求失败（错误）回调
     *
     * @param whit
     * @param body
     */
    protected void okResponseError(String whit, String body) {
    }

    /**
     * OK网络开始请求回调
     * @param flag
     */
    protected void okResponseStart(String flag) {
    }

    /**
     * OK网络请求完成回调
     * @param flag
     */
    protected void okResponseFinish(String flag) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
}
