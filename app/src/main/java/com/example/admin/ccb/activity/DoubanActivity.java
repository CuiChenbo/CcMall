package com.example.admin.ccb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import com.example.admin.ccb.bean.DoubanBean;
import com.example.admin.ccb.bean.GankBean;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.example.admin.ccb.utils.PhotoShowDialog;
import com.lzy.okgo.model.HttpParams;

import java.util.HashMap;

import www.ccb.com.common.base.BaseActivity;
import www.ccb.com.common.utils.GsonUtils;
import www.ccb.com.common.utils.ToastUtils;
import www.ccb.com.common.utils.UrlFactory;

public class DoubanActivity extends BaseActivity {

    @Override
    public int getContentViewResource() {
        return R.layout.activity_douban;
    }

    private RecyclerView mRv;
    private DoubanAdapter mAdapter;

    class DoubanAdapter extends BaseQuickAdapter<DoubanBean.SubjectsBean,BaseViewHolder> {

        public DoubanAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, DoubanBean.SubjectsBean item) {
            String casts = "演员:\t";
            for (int i = 0; i < item.getCasts().size(); i++) {
                casts = casts + item.getCasts().get(i).getName()+"\t";
            }
            String genres = "类型:\t";
            for (int i = 0; i < item.getGenres().size(); i++) {
                genres = genres + item.getGenres().get(i)+"\t";
            }
            helper.setText(R.id.tv1,item.getTitle())
            .setText(R.id.tv2,"豆瓣评分:\t"+item.getRating().getAverage())
            .setText(R.id.tv3,casts)
            .setText(R.id.tv4,genres);
            ImageView iv = helper.getView(R.id.iv);
            GlideImageUtils.Display(mContext,item.getImages().getMedium(),iv);
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

    @Override
    protected void initView() {
     UpTitle("电影排行榜");
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new DoubanAdapter((R.layout.item_douban_top));
        mRv.setAdapter(mAdapter);
    }

    private int page = 0;
    @Override
    protected void initData() {
        HttpParams params = new HttpParams();
        params.put("start",page);
        params.put("count",10);
        okPostRequest("Top",UrlFactory.DoubanDataTopUrl,params,DoubanBean.class,null,true);
    }

    @Override
    protected void initList() {
         mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
             @Override
             public void onLoadMoreRequested() {
                 HttpParams params = new HttpParams();
                 params.put("start",page);
                 params.put("count",10);
                 okPostRequest("Top",UrlFactory.DoubanDataTopUrl,params,DoubanBean.class);
             }
         },mRv);
    }

    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);
        if (!TextUtils.equals(whit,"Top"))return;
        mAdapter.loadMoreComplete();
        try{
            DoubanBean datas = (DoubanBean) t;
            mAdapter.addData(datas.getSubjects());
            page = page + 10;
        }catch (Exception e){
           e.fillInStackTrace();
        }
    }

    @Override
    protected void okResponseFinish(String flag) {
        super.okResponseFinish(flag);
    }
}
