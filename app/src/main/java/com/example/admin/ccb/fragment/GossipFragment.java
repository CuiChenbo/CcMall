package com.example.admin.ccb.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import com.example.admin.ccb.activity.BaseWebViewActivity;
import com.example.admin.ccb.bean.NewsTopBean;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.example.admin.ccb.utils.PhotoDgUtils;
import com.google.gson.JsonSyntaxException;

import www.ccb.com.common.base.BaseCacheFragment;
import www.ccb.com.common.utils.GsonUtils;
import www.ccb.com.common.utils.UrlFactory;

/**
 *Ccb simple {@link Fragment} subclass.
 */
public class GossipFragment extends BaseCacheFragment {

    private NewsTopAdapter mAdapter;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview,container,false);
    }

    private RecyclerView recyclerView;
    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mAdapter = new NewsTopAdapter(R.layout.item_newstop);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void loadData() {
        loadHttpData();
    }

    private int start = 0;
    private int count = 20;
    public void loadHttpData(){
        okGetRequest("NewsGossip",UrlFactory.NewsGossipDataUrl+start+"-"+count+".html");
    }

    @Override
    public void initListener() {
       mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
           @Override
           public void onLoadMoreRequested() {
               okGetRequest("NewsGossip",UrlFactory.NewsGossipDataUrl+start+"-"+count+".html");
           }
       },recyclerView);
    }

    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);
        if (TextUtils.equals(whit,"NewsGossip")){
            start = start + count;
            String json = String.valueOf(t);
            NewsTopBean datas = null;
            try{
                datas = GsonUtils.fromJson(json,NewsTopBean.class);
            }catch (JsonSyntaxException e){
                e.fillInStackTrace();
            }
           if (datas == null)return;
            mAdapter.addData(datas.getDatasGossip());
            if (datas.getDatasGossip().size() == 0) mAdapter.loadMoreEnd();
        }
    }

    @Override
    protected void okResponseFinish(String flag) {
        super.okResponseFinish(flag);
        mAdapter.loadMoreComplete();
    }

    class NewsTopAdapter extends BaseQuickAdapter<NewsTopBean.DatasBean,BaseViewHolder> {

        public NewsTopAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, NewsTopBean.DatasBean item) {
            helper.setText(R.id.tv,item.getTitle())
            .setText(R.id.tv2,item.getDigest())
            .setText(R.id.tv3,item.getSource()+" - "+item.getMtime());
            GlideImageUtils.display(mContext,item.getImgsrc(),(ImageView) helper.getView(R.id.iv));
            helper.setOnClickListener(R.id.llItem, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(item.getUrl())){
                        PhotoDgUtils.show(mContext,item.getImgsrc());
                        return;
                    }
                    Intent intent = new Intent(mContext,BaseWebViewActivity.class);
                    intent.putExtra("url",item.getUrl());
                    startActivity(intent);
                }
            });
        }
    }
}
