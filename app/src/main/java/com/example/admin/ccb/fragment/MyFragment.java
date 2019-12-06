package com.example.admin.ccb.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.admin.ccb.R;
import com.example.admin.ccb.activity.BaseWebViewActivity;
import com.example.admin.ccb.activity.DoubanActivity;
import com.example.admin.ccb.activity.MapActivity;
import com.example.admin.ccb.activity.SelecePhotoActivity;
import com.example.admin.ccb.activity.TVShowsActivity;
import www.ccb.com.common.base.BaseFragment;

import com.example.admin.ccb.activity.TestActivity;
import com.example.admin.ccb.utils.GlideImageUtils;

import java.util.Calendar;
import java.util.Date;

import www.ccb.com.common.utils.ToastUtils;

/**
 * CCB simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {


    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }
    private ImageView ivPhoto;
    private TextView tvSelectTime,tvSelectLocation,tvKefu,tvRes,tvSet;
    @Override
    public void initView(View view) {
        tvSelectTime = view.findViewById(R.id.tvSelectTime);
        ivPhoto = view.findViewById(R.id.ivPhoto);
        tvKefu = view.findViewById(R.id.tvKefu);
        tvRes = view.findViewById(R.id.tvRes);
        tvSet = view.findViewById(R.id.tvSet);
        tvSelectLocation = view.findViewById(R.id.tvSelectLocation);
    }

    @Override
    public void loadData() {
        GlideImageUtils.DisplayCircle(mContext,"http://img4.duitang.com/uploads/item/201209/20/20120920160424_QH2jt.thumb.600_0.jpeg",ivPhoto);
    }

    @Override
    public void initListener() {
        ivPhoto.setOnClickListener(this);
        tvSelectTime.setOnClickListener(this);
        tvSelectLocation.setOnClickListener(this);
        tvKefu.setOnClickListener(this);
        tvRes.setOnClickListener(this);
        tvSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivPhoto:
                startActivity(new Intent(mContext, SelecePhotoActivity.class));
                break;
            case R.id.tvSelectTime:
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        tvSelectTime.setText(date+"");
                    }
                }).build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.tvSelectLocation:
                startActivity(new Intent(mContext, MapActivity.class));
                break;
            case R.id.tvRes:
                startActivity(new Intent(mContext, BaseWebViewActivity.class));
                break;
            case R.id.tvKefu:
                if (checkApkExist(mContext, "com.tencent.mobileqq")){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+891789193+"&version=1")));
                }else{
                    ToastUtils.showToast(mContext,"您还未安装QQ应用");
                }
                break;
            case R.id.tvSet:
                startActivity(new Intent(mContext, TestActivity.class));
                break;
        }
    }

    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
