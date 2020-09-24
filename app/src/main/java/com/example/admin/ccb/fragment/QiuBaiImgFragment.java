package com.example.admin.ccb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.ccb.R;
import com.example.admin.ccb.activity.GoodsInfoActivity;
import com.example.admin.ccb.adapter.HomeAdapter;
import com.example.admin.ccb.adapter.QiuBaiImgAdapter;
import com.example.admin.ccb.bean.QiuBaiImgBean;
import com.lzy.okgo.model.HttpParams;

import www.ccb.com.common.base.BaseCacheFragment;
import www.ccb.com.common.utils.GsonUtils;
import www.ccb.com.common.utils.UrlFactory;

public class QiuBaiImgFragment extends BaseCacheFragment {

    private final String TAG = "QIUBAIIM";
    private QiuBaiImgAdapter mAdapter;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview,container,false);
    }

    private RecyclerView rv;
    @Override
    public void initView(View view) {
        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL, false));
        mAdapter = new QiuBaiImgAdapter(R.layout.item_qiubai_img);
        rv.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        rv.scrollToPosition(0);
        loadData();
        mAdapter.setNewData(null);
    }

    private int page = 1;
    private int count = 12;
    @Override
    public void loadData() {
        HttpParams params = new HttpParams();
        params.put("page", page);
        params.put("count", count);
        okGetRequest(TAG , UrlFactory.QiuBaiImgUrl , params);
    }

    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);
        if (!TextUtils.equals(whit , TAG))return;
        try{
            QiuBaiImgBean datas = GsonUtils.fromJson(String.valueOf(t) , QiuBaiImgBean.class);
            if (datas.getItems() == null || datas.getItems().size() == 0){
                mAdapter.loadMoreEnd();
            }else {
                mAdapter.loadMoreComplete();
                mAdapter.addData(datas.getItems());
            }
        }catch (Exception e){
            mAdapter.loadMoreFail();
        }

    }

    @Override
    public void initListener() {
         mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
             @Override
             public void onLoadMoreRequested() {
                 HttpParams params = new HttpParams();
                 params.put("page", ++page);
                 params.put("count", count);
                 okGetRequest(TAG , UrlFactory.QiuBaiImgUrl , params);
             }
         } , rv);
    }
}
