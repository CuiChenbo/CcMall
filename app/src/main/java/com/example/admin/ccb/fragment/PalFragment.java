package com.example.admin.ccb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.ccb.R;
import com.example.admin.ccb.activity.GoodsInfoActivity;
import com.example.admin.ccb.adapter.HomeAdapter;
import com.example.admin.ccb.bean.homeGoodsBean;
import com.example.admin.ccb.utils.ResCcb;

import java.util.ArrayList;
import java.util.Random;

import www.ccb.com.common.base.BaseCacheFragment;
import www.ccb.com.common.base.BaseFragment;

public class PalFragment extends BaseCacheFragment {


    private HomeAdapter homeAdapter;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview,container,false);
    }

    private RecyclerView rv;
    @Override
    public void initView(View view) {
        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL, false));
        homeAdapter = new HomeAdapter(R.layout.home_item);
        rv.setAdapter(homeAdapter);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        rv.scrollToPosition(0);
    }

    @Override
    public void loadData() {
        Random random = new Random();
        homeGoodsBean homeGoods = new homeGoodsBean();
        homeGoods.datas = new ArrayList<>();
        for (int i = 0; i < ResCcb.getDatas1().size(); i++) {  //添加条目
            homeGoodsBean.Data data = new homeGoodsBean.Data();
            data.icon = ResCcb.getIconImages().get(random.nextInt(ResCcb.getIconImages().size()));
            data.content = ResCcb.getDatas1().get(i);
            data.title = ResCcb.getDatas1().get(i).substring(ResCcb.getDatas1().size()-10,ResCcb.getDatas1().size()-1);
            int jj = random.nextInt(11)+1;
            data.images = new ArrayList<>();
            for (int j = 0; j < jj+1; j++) {  //添加条目图片
                homeGoodsBean.Data.PicList pics = new homeGoodsBean.Data.PicList();
                pics.pic = ResCcb.getGoodsImages().get(random.nextInt(ResCcb.getGoodsImages().size()-1));
                data.images.add(pics);
            }
            homeGoods.datas.add(data);
        }
        homeAdapter.setNewData(homeGoods.datas);
    }

    @Override
    public void initListener() {
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, GoodsInfoActivity.class));
            }
        });
    }
}
