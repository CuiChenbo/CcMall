package com.example.admin.ccb.fragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.ccb.R;
import com.example.admin.ccb.activity.GoodsInfoActivity;
import com.example.admin.ccb.activity.SearchActivity;
import com.example.admin.ccb.activity.VideoPlayerDouActivity;
import com.example.admin.ccb.activity.VideoPlayerListActivity;
import com.example.admin.ccb.activity.VideoPlayerListAutoActivity;
import com.example.admin.ccb.adapter.HomeAdapter;
import com.example.admin.ccb.adapter.HomeMenuAdapter;
import com.example.admin.ccb.base.BaseFragment;
import com.example.admin.ccb.bean.homeGoodsBean;
import com.example.admin.ccb.bean.homeMenuBean;
import com.example.admin.ccb.utils.DialogUtils;
import com.example.admin.ccb.utils.GlideImageLoader;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.example.admin.ccb.utils.ResCcb;
import com.example.admin.ccb.view.UPMarqueeView;
import com.gyf.barlibrary.ImmersionBar;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import www.ccb.com.common.utils.UiUtils;
import www.ccb.com.common.widget.dialog.SingleSelectDialog;

/**
 * CCB simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    private HomeAdapter rvAp;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    private RecyclerView rv;
    private View headerView;
    private Banner banner;
    private RelativeLayout llChange;
    private UPMarqueeView upView;
    private RecyclerView menuRecyclerView;
    private View vScan;
    private ImageView ivAd;
    @Override
    public void initView(View view) {
        llChange = view.findViewById(R.id.llChange);
        rv = view.findViewById(R.id.rv);
        vScan = view.findViewById(R.id.vScan);
        rv.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL, false));
        rvAp = new HomeAdapter(R.layout.home_item);
        rv.setAdapter(rvAp);
        headerView = View.inflate(mContext,R.layout.header_banner,null);
        banner = headerView.findViewById(R.id.hBanner);
        upView = headerView.findViewById(R.id.upView);
        ivAd = headerView.findViewById(R.id.ivImg);
        menuRecyclerView = headerView.findViewById(R.id.menuRecyclerView);
        rvAp.setHeaderView(headerView);
        height = UiUtils.dp2px(mContext,200-45);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.titleBar(llChange).statusBarDarkFont(false).init();
    }

    private int height;  // 滑动到什么地方完全变色
    private int ScrollUnm = 0;  //滑动的距离总和

    private final int REQUEST_CODE = 106;
    private final int MANIFESTPERMISSIONCAMERA = 17;
    @Override
    public void initListener() {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                ScrollUnm = ScrollUnm + dy; //滑动距离总合
                if (ScrollUnm<=0){  //在顶部时完全透明
                    llChange.setBackgroundColor(Color.argb((int) 0, 255,41,76));
                }else if (ScrollUnm>0&&ScrollUnm<= height){  //在滑动高度中时，设置透明度百分比（当前高度/总高度）
                    double d = (double) ScrollUnm / height;
                    double alpha = (d*255);
                    llChange.setBackgroundColor(Color.argb((int) alpha, 255,41,76));
                }else{ //滑出总高度 完全不透明
                    llChange.setBackgroundColor(Color.argb((int) 255, 255,41,76));
                }
            }
        });
        rvAp.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, GoodsInfoActivity.class));
            }
        });
        vScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) { //检查用户是否打开相机权限
                    //没有打开
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA))) {  //用户是否禁止使用相机权限
                        // 帮跳转到该应用的设置界面，让用户手动授权
                        DialogUtils.showDialogForIosStyle(mContext, " 提示", "开启相机权限后才能使用扫一扫功能", "确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                                DialogUtils.dissmissDialog();
                            }
                        }, true);
                    } else {
                        //请求相机权限
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, MANIFESTPERMISSIONCAMERA);
                    }
                } else {
                    Intent intent = new Intent(mContext, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
            });
        llChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, SearchActivity.class));
            }
        });

        ivAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] datas = {"抖音Style","列表Style","自动播放列表Style"};
                new SingleSelectDialog.Builder(mContext).setItems(datas, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                          if (i == 0){
                              startActivity(new Intent(mContext, VideoPlayerDouActivity.class));
                          }else if(i == 1){
                              startActivity(new Intent(mContext, VideoPlayerListActivity.class));
                          }else {
                              startActivity(new Intent(mContext, VideoPlayerListAutoActivity.class));
                          }
                    }
                }).show();
            }
        });

    }
    private homeGoodsBean homeGoods;
    @Override
    public void loadData() {
        Random random = new Random();
        homeGoods = new homeGoodsBean();
        homeGoods.datas = new ArrayList<>();
        for (int i = 0; i < ResCcb.getDatas1().size(); i++) {  //添加条目
            homeGoodsBean.Data data = new homeGoodsBean.Data();
            data.icon = ResCcb.getMenuImages().get(random.nextInt(ResCcb.getMenuImages().size()));
            data.content = ResCcb.getDatas1().get(i);
            data.title = ResCcb.getDatas1().get(i).substring(ResCcb.getDatas1().size()-10,ResCcb.getDatas1().size()-1);
            int jj = random.nextInt(8)+1;
            data.images = new ArrayList<>();
            for (int j = 0; j < jj+1; j++) {  //添加条目图片
                homeGoodsBean.Data.PicList pics = new homeGoodsBean.Data.PicList();
                pics.pic = ResCcb.getGoodsImages().get(random.nextInt(ResCcb.getGoodsImages().size()-1));
                data.images.add(pics);
            }
            homeGoods.datas.add(data);
        }
        rvAp.setNewData(homeGoods.datas);
        GlideImageUtils.displayGif(mContext,R.mipmap.gif1,ivAd);
        setBannerOneDatas();
        setUpView(ResCcb.getDatas());
        setMenu(ResCcb.getMenus());
    }

    private void setBannerOneDatas() {

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
            banner.setImages(ResCcb.getBannerRes());
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {

                }
            });
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    public void setUpView(List<String> datas) {
        List<String> data = new ArrayList<>();
        List<View> views = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            data.add(datas.get(i));
        }
        for (int i = 0; i < data.size(); i++) {
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_upview, null);
            TextView tv = (TextView) moreView.findViewById(R.id.tv);
            tv.setText(Html.fromHtml(data.get(i).toString()));
            views.add(moreView);
        }
        upView.setViews(views);
    }

    public void setMenu(List<String> menu) {
        menuRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 5));
        HomeMenuAdapter hap = new HomeMenuAdapter(R.layout.item_homemenu);
        menuRecyclerView.setAdapter(hap);
        homeMenuBean b = new homeMenuBean();
        b.datas = new ArrayList<>();
        for (int i = 0; i < ResCcb.getMenus().size(); i++) {
            homeMenuBean.Data datas = new homeMenuBean.Data();
            datas.icon = ResCcb.menuimages[i];
            datas.title = ResCcb.getMenus().get(i);
            b.datas.add(datas);
        }
        hap.setNewData(b.datas);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    DialogUtils.showDialogForIosStyle(mContext, "结果提示", result, "好", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogUtils.dissmissDialog();
                        }
                    }, false);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    DialogUtils.showDialogForIosStyle(mContext, "提示", "扫码失败", "好", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogUtils.dissmissDialog();
                        }
                    }, false);
                }
            }
        }
    }
}
