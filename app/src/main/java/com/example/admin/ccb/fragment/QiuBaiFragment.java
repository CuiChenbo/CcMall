package com.example.admin.ccb.fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import com.example.admin.ccb.bean.QiuBaiBean;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.Random;

import www.ccb.com.common.base.BaseCacheFragment;
import www.ccb.com.common.utils.GsonUtils;
import www.ccb.com.common.utils.UrlFactory;

public class QiuBaiFragment extends BaseCacheFragment {
    private RecyclerView mRv;
    private QiuBaiAdapter mAdapter;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_douban, container, false);
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.title).setVisibility(View.GONE);
        mRv = view.findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new QiuBaiAdapter((R.layout.item_qiubai_text));
        mRv.setAdapter(mAdapter);
    }

    class QiuBaiAdapter extends BaseQuickAdapter<QiuBaiBean.ItemsBean, BaseViewHolder> {

        public QiuBaiAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, QiuBaiBean.ItemsBean item) {
            helper.setText(R.id.tv, item.getContent());
            ImageView iv = helper.getView(R.id.iv);
            if (item.getUser() != null) {
                helper.setText(R.id.tvt, item.getUser().getLogin());
                GlideImageUtils.DisplayCircle(getActivity(), item.getUser().getThumb(), iv);
            } else {
                helper.setText(R.id.tvt, "糗友");
                GlideImageUtils.display(getActivity(), R.mipmap.emptyimage, iv);
            }

        }
    }


    private int page = 1;
    private int count = 12;

    private Random random = new Random();
    @Override
    public void loadData() {
        HttpParams params = new HttpParams();
        params.put("type", "text");
        params.put("page", page);
        params.put("count", count);
        params.put("readarticles", "[12360"+random.nextInt(9999)+"]");
        okGetRequest("QiuBai", UrlFactory.QiuBaiUrl, params);
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
                params.put("type", "text");
                params.put("page", (++page));
                params.put("count", count);
                params.put("readarticles", "[12360"+random.nextInt(9999)+"]");
                okGetRequest("QiuBai", UrlFactory.QiuBaiUrl, params);
            }
        }, mRv);
    }


    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);
        if (!TextUtils.equals(whit, "QiuBai")) return;
        try {
            QiuBaiBean datas = GsonUtils.fromJson(t.toString(), QiuBaiBean.class);
            mAdapter.addData(datas.getItems());
            if (datas.getItems() != null && datas.getItems().size() > 0) {
                mAdapter.loadMoreComplete();
            } else {
                mAdapter.loadMoreEnd();
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            mAdapter.loadMoreFail();
        }
    }

    @Override
    protected void okResponseError(String flag, String error) {
        super.okResponseFinish(flag);
        TextView tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText("卧槽,加载失败了");
        mAdapter.setEmptyView(tv);
    }
}
