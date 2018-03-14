package com.example.admin.ccb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import com.example.admin.ccb.activity.ShopHomeActivity;
import com.example.admin.ccb.base.DefaultBaseAdapter;
import com.example.admin.ccb.bean.homeGoodsBean;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.example.admin.ccb.utils.PhotoShowDialog;
import com.example.admin.ccb.view.AllGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author cuiChenBo
 * Created by zz on 2018/3/6 11:28.
 * 　　class explain:
 * 　　　　update:       upAuthor:      explain:
 */


   public class HomeAdapter extends BaseQuickAdapter<homeGoodsBean.Data,BaseViewHolder> {

        public HomeAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, homeGoodsBean.Data item) {
          helper.setText(R.id.title,item.title)
                  .setText(R.id.content,item.content)
          .setOnClickListener(R.id.icon, new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  mContext.startActivity(new Intent(mContext, ShopHomeActivity.class));
              }
          });
            GlideImageUtils.Display(mContext,item.icon,(ImageView) helper.getView(R.id.icon));
           AllGridView rv = helper.getView(R.id.rvPic);
            PicAdapter ad =  new PicAdapter(mContext,item.images);
           rv.setAdapter(ad);
        }

        class PicAdapter extends DefaultBaseAdapter<homeGoodsBean.Data.PicList>{

            public PicAdapter(Context context, List<homeGoodsBean.Data.PicList> dataList) {
                super(context, dataList);
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = View.inflate(mContext,R.layout.item_pic,null);
                ImageView iv = v.findViewById(R.id.iv_pic);
                GlideImageUtils.Display(mContext,dataList.get(position).pic,iv);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        List iamges = new ArrayList();
                        for (int i = 0; i < dataList.size(); i++) {
                            iamges.add(dataList.get(i).pic);
                        }
                       new PhotoShowDialog(mContext,iamges,position).show();

                    }
                });
                return v;
            }
        }
}
