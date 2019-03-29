package com.example.admin.ccb.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.admin.ccb.utils.PhotoDgUtils;
import com.example.admin.ccb.utils.ResCcb;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import www.ccb.com.common.utils.ToastUtils;

/**
 * Ccb simple {@link Fragment} subclass.
 */
public class ClassifyRightFragment extends BaseFragment {


    private Rv1Adapter rv1Adapter;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classify_right, container, false);
    }
     private RecyclerView rv1;
    private String mTitle;
    private ImageView ivHead;
    @Override
    public void initView(View view) {
       rv1 = view.findViewById(R.id.rv1);
       rv1.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        rv1Adapter = new Rv1Adapter(R.layout.item_classify_rv1);
        rv1.setAdapter(rv1Adapter);
        View hview = View.inflate(mContext,R.layout.item_banner_125,null);
        ivHead = hview.findViewById(R.id.iv_pic);
        rv1Adapter.setHeaderView(hview);
    }
    private String datas[] = {"","","","","","","","","","","",""};
    private String datas2[] = {"","","","","",""};
    private int randomPosition;
    @Override
    public void loadData() {
        mTitle = getArguments().getString("Name");
        rv1Adapter.setNewData(Arrays.asList(datas));
        randomPosition = new Random().nextInt(ResCcb.getBannerImages().size()-1);
        GlideImageUtils.displayRoundCorner(mContext, ResCcb.getBannerImages().get(randomPosition),ivHead,10);
    }
    @Override
    public void initListener() {
        ClassifyFragment.setonListeners(new ClassifyFragment.Listeners() {
            @Override
            public void onClick() {
                loadData();
                rv1.scrollToPosition(0); //回到顶部 不需要可以注释
            }
        });
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoDgUtils.show(mContext,ResCcb.getBannerImages().get(randomPosition));
            }
        });
    }
    class Rv1Adapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public Rv1Adapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(final BaseViewHolder helper, String item) {
           helper.setText(R.id.tvTitle,mTitle+helper.getAdapterPosition());
           RecyclerView rv2 = helper.getView(R.id.rv2);
            rv2.setLayoutManager(new GridLayoutManager(mContext,3));
            Rv2Adapter rv2Adapter = new Rv2Adapter(R.layout.item_classify_rv2,Arrays.asList(datas2),helper.getAdapterPosition());
            rv2.setAdapter(rv2Adapter);
            helper.setOnClickListener(R.id.tvTitle, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showToast(mContext,mTitle+helper.getAdapterPosition());
                }
            });
        }
    }
    class Rv2Adapter extends BaseQuickAdapter<String,BaseViewHolder>{
        private int rv1Position;
        public Rv2Adapter(int layoutResId, @Nullable List<String> data, int adapterPosition) {
            super(layoutResId, data);
            rv1Position = adapterPosition;
        }

        @Override
        protected void convert(final BaseViewHolder helper, String item) {
             helper.setText(R.id.tvTitle,mTitle+rv1Position+"("+helper.getAdapterPosition()+")");
             if (helper.getAdapterPosition() == 0||helper.getAdapterPosition() == 3||helper.getAdapterPosition() == 5){
                 GlideImageUtils.display(mContext, ResCcb.getGoodsImages().get(new Random().nextInt(ResCcb.getGoodsImages().size()-1)),(ImageView)helper.getView(R.id.iv_photo));
             }else if (helper.getAdapterPosition() == 2){
                 helper.setImageResource(R.id.iv_photo,R.mipmap.spc4);
             }else if (helper.getAdapterPosition() == 4){
                 helper.setImageResource(R.id.iv_photo,R.mipmap.spc8);
             }else{
                 helper.setImageResource(R.id.iv_photo,R.mipmap.qq);
             }
             helper.setOnClickListener(R.id.iv_photo, new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     ToastUtils.showToast(mContext,mTitle+rv1Position+"("+helper.getAdapterPosition()+")");
                 }
             });
        }
    }
}
