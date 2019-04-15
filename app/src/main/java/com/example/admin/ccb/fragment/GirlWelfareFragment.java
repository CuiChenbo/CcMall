package com.example.admin.ccb.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import com.example.admin.ccb.activity.BaseWebViewActivity;
import com.example.admin.ccb.bean.GankBean;
import com.example.admin.ccb.bean.NewsTopBean;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.example.admin.ccb.utils.PhotoShowDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import www.ccb.com.common.base.BaseCacheFragment;
import www.ccb.com.common.utils.UiUtils;
import www.ccb.com.common.utils.UrlFactory;
import www.ccb.com.common.widget.recyclerview.DividerGridItemDecoration;

/**
 *Ccb simple {@link Fragment} subclass.
 */
public class GirlWelfareFragment extends BaseCacheFragment {

    private WelfaerAdapter welfaerAdapter;
    class WelfaerAdapter extends BaseQuickAdapter<GankBean.ResultsBean,BaseViewHolder>{

        public WelfaerAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, GankBean.ResultsBean item) {
            ImageView iv = helper.getView(R.id.iv);
           ViewGroup.LayoutParams layoutparams = iv.getLayoutParams();
           layoutparams.height = UiUtils.dp2px( item.viewHeight);
           iv.setLayoutParams(layoutparams);
            GlideImageUtils.display(mContext,item.getUrl(),iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<String> photos = new ArrayList<>();
                    for (int i = helper.getAdapterPosition(); i < mData.size(); i++) {
                        if (i < 0) continue;
                        photos.add(mData.get(i).getUrl());
                        if (photos.size() > 9){break;}
                    }
                    PhotoShowDialog.get(mContext,photos,0).show();
                }
            });
        }
    }

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview,container,false);
    }

    private RecyclerView mRv;
    @Override
    public void initView(View view) {
        mRv = view.findViewById(R.id.rv);
        StaggeredGridLayoutManager rlm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRv.setLayoutManager(rlm);
        welfaerAdapter = new WelfaerAdapter(R.layout.item_image_h200dp);
        mRv.setAdapter(welfaerAdapter);
        mRv.addItemDecoration(new DividerGridItemDecoration(mContext,R.drawable.recyclerview_divider));
    }
    int page = 1;
    @Override
    public void loadData() {
        okGetRequest("1" , UrlFactory.DataUrl,Arrays.asList("福利","20",String.valueOf(page)));
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mRv.scrollToPosition(0);
    }

    @Override
    public void initListener() {
        welfaerAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                okGetRequest("1" , UrlFactory.DataUrl,Arrays.asList("福利","20",String.valueOf(++page)));
            }
        },mRv);
    }

    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);
        if (TextUtils.equals("1",whit)){
            welfaerAdapter.loadMoreComplete();
            try{
                GankBean datas = new Gson().fromJson((String)t,GankBean.class);
                if (!datas.isError()){
                    Random random = new Random();
                    for (int i = 0; i < datas.getResults().size(); i++) {
                        datas.getResults().get(i).viewHeight = 200 + random.nextInt(100);
                    }
                    welfaerAdapter.addData(datas.getResults());
                }
            }catch (Exception e){
                return;
            }
        }
    }

    @Override
    protected void okResponseError(String whit, String body) {
        super.okResponseError(whit, body);
        welfaerAdapter.loadMoreFail();
    }
}
