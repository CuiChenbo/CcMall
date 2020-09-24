package com.example.admin.ccb.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import com.example.admin.ccb.activity.ShopHomeActivity;
import com.example.admin.ccb.bean.QiuBaiImgBean;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.example.admin.ccb.utils.PhotoShowDialog;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

import www.ccb.com.common.base.DefaultBaseAdapter;

/**
 * @Author cuiChenBo
 * Created by zz on 2018/3/6 11:28.
 * 　　class explain:
 * 　　　　update:       upAuthor:      explain:
 */


   public class QiuBaiImgAdapter extends BaseQuickAdapter<QiuBaiImgBean.ItemsBean,BaseViewHolder> {

        public QiuBaiImgAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, QiuBaiImgBean.ItemsBean item) {
            ImageView iv = helper.getView(R.id.icon);
            if (item.getUser() != null){
                helper.setText(R.id.title,item.getUser().getLogin());
                GlideImageUtils.display(mContext , item.getUser().getThumb() , iv);
            }else {
                helper.setText(R.id.title,"未命名");
                GlideImageUtils.display(mContext ,R.mipmap.emptyimage , iv);
            }
            helper.setText(R.id.content,item.getContent());
           NineGridView nineGrid = helper.getView(R.id.nineGrid);
            ArrayList<ImageInfo> imageInfo;
            if (item.getAttachments() != null){ //是否有图片组
                imageInfo = new ArrayList<>();
                for (QiuBaiImgBean.ItemsBean.AttachmentsBean imageDetail : item.getAttachments()) {
                    ImageInfo info = new ImageInfo();
                    if (TextUtils.equals(imageDetail.getFormat() , "image")) { //是否是静态图
                        info.setBigImageUrl("http://" + imageDetail.getHigh_url());
                        info.setThumbnailUrl("http://" + imageDetail.getLow_url());
                    }else {
                        info.setBigImageUrl("http://" + imageDetail.getPic_url());
                        info.setThumbnailUrl("http://" + imageDetail.getPic_url());
                    }
                    imageInfo.add(info);
                }
            }else {
                imageInfo = new ArrayList<>();
                ImageInfo info = new ImageInfo();
                info.setBigImageUrl(item.getOrigin_url());
                info.setThumbnailUrl(item.getLow_url());
                imageInfo.add(info);
            }
            nineGrid.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
        }
}
