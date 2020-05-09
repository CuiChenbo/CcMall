package com.example.admin.ccb.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import com.example.admin.ccb.bean.GankBean;
import com.example.admin.ccb.bean.GankGirlV2Bean;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.example.admin.ccb.utils.PhotoShowDialog;
import com.example.admin.ccb.utils.PicTureUtils;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import www.ccb.com.common.base.BaseActivity;
import www.ccb.com.common.utils.ToastUtils;
import www.ccb.com.common.utils.UrlFactory;

public class GirlWelfareActivity extends BaseActivity {

    private RecyclerView mRv;
    private WelfaerAdapter welfaerAdapter;

    class WelfaerAdapter extends BaseQuickAdapter<GankGirlV2Bean.DataBean,BaseViewHolder>{

        public WelfaerAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, GankGirlV2Bean.DataBean item) {
            helper.setText(R.id.tv,item.getDesc())
            .setText(R.id.tvTitle , item.getAuthor());
            ImageView iv = helper.getView(R.id.iv);
            GlideImageUtils.display(mContext,item.getUrl(),iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotoShowDialog.get(mContext,item.getUrl()).show();
                }
            });
        }
    }

    @Override
    public int getContentViewResource() {
        return R.layout.activity_girl_welfare;
    }

    @Override
    protected void initView() {
//        UpTitle("给你点福利瞧瞧");
       mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        welfaerAdapter = new WelfaerAdapter((R.layout.item_image_full));
        mRv.setAdapter(welfaerAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        if (mRv.getOnFlingListener() == null) snapHelper.attachToRecyclerView(mRv);
    }

    int page = 1;
    private List<Object> params = Arrays.asList("page",page,"count","10");
    @Override
    protected void initData() {
        okGetRequest("1" , UrlFactory.GirlUrl,params,null,null,true);
    }

    @Override
    protected void initList() {
     welfaerAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
         @Override
         public void onLoadMoreRequested() {
             params.set(1 , ++page);
             okGetRequest("1" , UrlFactory.GirlUrl,params,null,null,true);
         }
     },mRv);
    }

    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);
        if (TextUtils.equals("1",whit)){
            welfaerAdapter.loadMoreComplete();
            try{
                GankGirlV2Bean datas = new Gson().fromJson((String)t, GankGirlV2Bean.class);
                if (datas.getData() != null){
                    welfaerAdapter.addData(datas.getData());
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
