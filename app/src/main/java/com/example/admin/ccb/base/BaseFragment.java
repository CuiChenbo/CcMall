package com.example.admin.ccb.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.ccb.R;
import com.gyf.barlibrary.ImmersionBar;


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

    public void isTitleBar(boolean is,View v){
        if (is){
            mImmersionBar = ImmersionBar.with(this);  //可以为任意view;
            mImmersionBar.titleBar(v).statusBarDarkFont(true, 0.2f).init();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }
}