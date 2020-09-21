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
import com.example.admin.ccb.utils.ResDatas;

import java.util.ArrayList;
import java.util.Random;

import www.ccb.com.common.base.BaseCacheFragment;

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
        for (int i = 0; i < ResDatas.getDatas1().size(); i++) {  //添加条目
            homeGoodsBean.Data data = new homeGoodsBean.Data();
            data.icon = ResDatas.getIconImages().get(random.nextInt(ResDatas.getIconImages().size()));
            data.content = ResDatas.getDatas1().get(i);
            data.title = ResDatas.getDatas().get(random.nextInt(ResDatas.getDatas().size()));
            int picCount;
            switch (i){
                case 1:
                    picCount = 1;
                    break;
                case 2:
                    picCount = 2;
                    break;
                case 3:
                    picCount = 4;
                    break;
               default:
                   picCount = random.nextInt(ResDatas.getGoodsImages().size());
                    break;
            }
            if (picCount == 0) picCount = 1;
            data.images = new ArrayList<>();
            for (int j = 0; j < picCount; j++) {  //添加条目图片
                homeGoodsBean.Data.PicList pics = new homeGoodsBean.Data.PicList();
                pics.src = ResDatas.getGoodsImages().get(random.nextInt(ResDatas.getGoodsImages().size()-1));
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
