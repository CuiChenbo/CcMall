package com.example.admin.ccb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import com.example.admin.ccb.activity.BaseWebViewActivity;
import com.example.admin.ccb.bean.DoubanBean;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.example.admin.ccb.utils.PhotoShowDialog;
import com.lzy.okgo.model.HttpParams;

import www.ccb.com.common.base.BaseCacheFragment;
import www.ccb.com.common.base.BaseFragment;
import www.ccb.com.common.base.BaseHeadLayoutFragment;
import www.ccb.com.common.utils.UrlFactory;

public class DoubanInTheatersFragment extends BaseCacheFragment {
    private RecyclerView mRv;
    private DoubanAdapter mAdapter;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_douban,container,false);
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.title).setVisibility(View.GONE);
        mRv = view.findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new DoubanAdapter((R.layout.item_douban_top));
        mRv.setAdapter(mAdapter);
    }

    class DoubanAdapter extends BaseQuickAdapter<DoubanBean.SubjectsBean,BaseViewHolder> {

        public DoubanAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, DoubanBean.SubjectsBean item) {
            String casts = "演员: ";
            for (int i = 0; i < item.getCasts().size(); i++) {
                casts = casts + item.getCasts().get(i).getName()+" ";
            }
            String genres = "类型: ";
            for (int i = 0; i < item.getGenres().size(); i++) {
                genres = genres + item.getGenres().get(i)+" ";
            }
            helper.setText(R.id.tv1,item.getTitle())
            .setText(R.id.tv2,Html.fromHtml("<font color='#666666'>豆瓣评分: </font>"))
            .setText(R.id.tv2_,item.getRating().getAverage()+"")
            .setText(R.id.tv3,casts)
            .setText(R.id.tv4,genres);
            ImageView iv = helper.getView(R.id.iv);
            GlideImageUtils.display(mContext,item.getImages().getMedium(),iv);
            iv.setOnClickListener(view -> PhotoShowDialog.get(mContext,item.getImages().getLarge()).show());
            helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,BaseWebViewActivity.class);
                    intent.putExtra("url",item.getAlt());
                    startActivity(intent);
                }
            });
        }
    }


    private int start = 0;
    private int count = 18;
    @Override
    public void loadData() {
        HttpParams params = new HttpParams();
        params.put("start",start);
        params.put("count",count);
        okPostRequest("Top",UrlFactory.DoubanDataIn_theatersUrl,params,DoubanBean.class,null,true);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mRv.scrollToPosition(0);
    }

    @Override
    public void initListener() {
         mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
             @Override
             public void onLoadMoreRequested() {
                 HttpParams params = new HttpParams();
                 params.put("start",start);
                 params.put("count",count);
                 okPostRequest("Top",UrlFactory.DoubanDataIn_theatersUrl,params,DoubanBean.class);
             }
         },mRv);
    }


    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);
        if (!TextUtils.equals(whit,"Top"))return;
        try{
            DoubanBean datas = (DoubanBean) t;
            mAdapter.addData(datas.getSubjects());
            start = start + count;
            if (datas.getSubjects()!=null&&datas.getSubjects().size()>0){
                mAdapter.loadMoreComplete();
            }else{
                mAdapter.loadMoreEnd();
            }
        }catch (Exception e){
           e.fillInStackTrace();
            mAdapter.loadMoreFail();
        }
    }

    @Override
    protected void okResponseFinish(String flag) {
        super.okResponseFinish(flag);
    }
}
