package com.example.admin.ccb.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import www.ccb.com.common.base.BaseFragment;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.example.admin.ccb.utils.ResCcb;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment2 extends BaseFragment {

    private MyAdapter2 myAdapter;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_fragment1,container,false);
    }

    private RecyclerView recyclerView;
    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        myAdapter = new MyAdapter2(R.layout.item_shop2);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void loadData() {
        myAdapter.setNewData(ResCcb.getGoodsImages());
    }

    @Override
    public void initListener() {

    }
    class MyAdapter2 extends BaseQuickAdapter<String,BaseViewHolder> {

        public MyAdapter2(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv,"水洗小脚牛仔"+helper.getAdapterPosition()+"码")
            .setText(R.id.tv2,"高腰牛仔裤女春秋2018最新款ins超火的裤子韩版近身小脚九分裤显瘦")
            .setText(R.id.tv3,"￥:"+100+helper.getAdapterPosition());
            GlideImageUtils.Display(mContext,item,(ImageView) helper.getView(R.id.iv));
        }
    }
}
