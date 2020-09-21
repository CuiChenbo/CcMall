package com.example.admin.ccb.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.admin.ccb.utils.ResDatas;

public class ShopFragment1 extends BaseFragment {

    private MyAdapter myAdapter;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_fragment1,container,false);
    }

    private RecyclerView recyclerView;
    @Override
    public void initView(View view) {
      recyclerView = view.findViewById(R.id.rv);
      recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        myAdapter = new MyAdapter(R.layout.item_shop1);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void loadData() {
     myAdapter.setNewData(ResDatas.getGoodsImages());
    }

    @Override
    public void initListener() {

    }
    class MyAdapter extends BaseQuickAdapter<Integer,BaseViewHolder>{

        public MyAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Integer item) {
            helper.setText(R.id.tv,"水洗小脚牛仔"+helper.getAdapterPosition()+"码");
            GlideImageUtils.display(mContext,item,(ImageView) helper.getView(R.id.iv));
        }
    }
}
