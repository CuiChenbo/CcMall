package com.example.admin.ccb.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.luck.picture.lib.entity.LocalMedia;

/**
 * @Author cuiChenBo
 * Created by zz on 2018/3/8 13:31.
 * 　　class explain:
 * 　　　　update:       upAuthor:      explain:
 */

public class SelectPhotoAdapter extends BaseQuickAdapter<LocalMedia,BaseViewHolder> {
    public SelectPhotoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalMedia item) {
        GlideImageUtils.display(mContext,item.getCompressPath(),(ImageView) helper.getView(R.id.iv_pic));
    }
}
