package www.ccb.com.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

import www.ccb.com.common.utils.GsonUtils;
import www.ccb.com.common.utils.LogUtils;
import www.ccb.com.common.utils.ToastUtils;
import www.ccb.com.common.widget.dialog.CbLoadingDialog;


public abstract class BaseFragment extends Fragment {

    private boolean isVisible;                  //是否可见状态
    private boolean isPrepared;                 //标志位，View已经初始化完成。
    protected LayoutInflater inflater;
    public ImmersionBar mImmersionBar;
    public Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        mContext = getActivity();
        View view = initContentView(inflater, container, savedInstanceState);
        initView(view);
        loadData();
        initListener();
        isPrepared = true;
        return view;
    }

    public void start(Class clazz){
        startActivity(new Intent(mContext,clazz));
    }

    public void isTitleBar(boolean is,View v){
        if (is){
            mImmersionBar = ImmersionBar.with(this);  //可以为任意view;
            mImmersionBar.titleBarMarginTop(v).statusBarDarkFont(true, 0.2f).init();
        }
    }

    /** 如果是与ViewPager一起使用，调用的是setUserVisibleHint */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }

        if (!hidden && mImmersionBar != null)
            mImmersionBar.init();
    }

    protected void onVisible() {
    }
    protected void onInvisible() {
    }
    protected abstract View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    public abstract void initView(View view);
    public abstract void loadData();
    public abstract void initListener();

    public void okGetRequest(String url){okGetRequest(null,url,null);}
    public void okGetRequest(String with , String url){okGetRequest(with,url,null);}

    public void okGetRequest(String with , String url , List<String> params){
        if (TextUtils.isEmpty(with))with = url;
        String finalWith = with;
        if (params!=null){
            for (int i = 0; i < params.size(); i++) {
                url = url+"/"+params.get(i);
            }
        }
        OkGo.<String>get(url).execute(new StringCallback() {
            @Override
            public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                super.onStart(request);
            }

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                LogUtils.out(finalWith + "请求结果:__", response.body());
                okResponseSuccess(finalWith, response.body());
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                okResponseError(finalWith, response.body());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                okResponseFinish(finalWith);
            }
        });
    }

    public void okPostRequest(final String what, final String httpurl, HttpParams params, final Class clazz){
        okPostRequest(what,httpurl,params,clazz,null,false);
    }

    /**
     * OK网络请求
     *
     * @param httpurl      请求URl 也用来标记
     * @param params       请求参数
     * @param clazz        返回的Bean对象
     * @param DialogMsg    弹出Dialog的文字消息
     * @param isShowDialog 是否弹出Dialog 默认弹出
     */
    public void okPostRequest(String what, String httpurl, HttpParams params, final Class clazz, final String DialogMsg, final boolean isShowDialog) {
        final String url = httpurl;
        final String finalWhat = TextUtils.isEmpty(what) ? url : what;
        OkGo.<String>post(url).params(params).execute(new StringCallback() {
            @Override
            public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                super.onStart(request);
                if (isShowDialog) showProgressDialog(DialogMsg);
                okResponseStart(finalWhat);
            }

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                LogUtils.out(finalWhat + "请求结果:__", response.body());
                if (clazz == null) {
                    okResponseSuccess(finalWhat, response.body());
                } else {
                    try {
                        Object bean = GsonUtils.fromJson(response.body(), clazz);
                        okResponseSuccess(finalWhat, bean);
                    } catch (Exception e) {
                        okResponseSuccess(finalWhat, null);
                        ToastUtils.GsonExtremely();
                        LogUtils.e("异常信息：" + e.toString());
                    }
                }
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                LogUtils.out(finalWhat + "请求结果:__", response.body());
                okResponseError(finalWhat, response.body());
                ToastUtils.failNetRequest();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (isShowDialog) dismissProgressDialog();
                okResponseFinish(finalWhat);
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

private CbLoadingDialog mProgressDialog;
    public void showProgressDialog(String msg) {
        if (this.mProgressDialog == null)
            this.mProgressDialog = new CbLoadingDialog(mContext);
        this.mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (this.mProgressDialog != null)
            this.mProgressDialog.dismiss();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }
}