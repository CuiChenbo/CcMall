package com.example.admin.ccb.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.ccb.R;
import com.example.admin.ccb.activity.GirlWelfareActivity;
import com.example.admin.ccb.activity.SearchActivity;
import com.example.admin.ccb.activity.SplashActivity;
import com.example.admin.ccb.activity.TVShowsActivity;
import com.example.admin.ccb.activity.VideoPlayerDouActivity;
import com.example.admin.ccb.activity.VideoPlayerListActivity;
import com.example.admin.ccb.activity.VideoPlayerListAutoActivity;
import com.example.admin.ccb.adapter.HomeMenuAdapter;
import com.example.admin.ccb.bean.homeMenuBean;
import com.example.admin.ccb.utils.DialogUtils;
import com.example.admin.ccb.utils.GlideImageLoader;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.example.admin.ccb.utils.ResDatas;
import com.example.admin.ccb.view.NotConflictViewPager;
import com.example.admin.ccb.view.UPMarqueeView;
import com.gyf.barlibrary.ImmersionBar;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import www.ccb.com.common.base.BaseCacheFragment;
import www.ccb.com.common.base.BaseFragment;
import www.ccb.com.common.utils.ToastUtils;
import www.ccb.com.common.utils.UiUtils;
import www.ccb.com.common.widget.dialog.SingleSelectDialog;

public class HomeFragment extends BaseFragment {

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private List<String> mDataList = Arrays.asList("微商城","HAPPY", "Cc动态", "Cc精选", "微福利", "给你看个段子吧");
    private MagicIndicator indicator;
    private NotConflictViewPager mPager;
    private AppBarLayout appBarLayout;
    private SwipeRefreshLayout swipeRefresh;
    private Banner banner;
    private UPMarqueeView upView;
    private RecyclerView menuRecyclerView;
    private View vScan;
    private ImageView ivAd;
    private RelativeLayout llChange;
    private TextView tvSearch;

    @Override
    public void initView(View v) {
        appBarLayout = v.findViewById(R.id.appBarLayout);
        indicator = v.findViewById(R.id.Indicator);
        mPager = v.findViewById(R.id.vp);
        swipeRefresh = v.findViewById(R.id.swipe);
        banner = v.findViewById(R.id.hBanner);
        upView = v.findViewById(R.id.upView);
        ivAd = v.findViewById(R.id.ivImg);
        vScan = v.findViewById(R.id.vScan);
        menuRecyclerView = v.findViewById(R.id.menuRecyclerView);
        llChange = v.findViewById(R.id.llChange);
        tvSearch = v.findViewById(R.id.tvSearch);

        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.titleBar(llChange).statusBarDarkFont(false).init();

        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdjustMode(false); //是否充满屏幕
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            long currentTime = 0;

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                ClipPagerTitleView tv = new ClipPagerTitleView(context);
                tv.setText(mDataList.get(i));
                tv.setTextColor(Color.parseColor("#424242"));
                tv.setClipColor(Color.parseColor("#FF294C"));
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPager.setCurrentItem(i);

                        if (mPager.getCurrentItem() == i && System.currentTimeMillis() - currentTime < 300) {
                            mFragmentList.get(i).onRefresh();
                            currentTime = 0;
                        }
                        currentTime = System.currentTimeMillis();
                    }
                });
                return tv;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT); //下划线的长度和字体相同
                indicator.setColors(Color.parseColor("#FF294C"));//下划线颜色
                return indicator;
            }
        });
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator, mPager);
    }

    private List<BaseCacheFragment> mFragmentList;

    @Override
    public void loadData() {
        setBannerOneDatas();
        setUpView(ResDatas.getAds());
        setMenu(ResDatas.getMenus());
        GlideImageUtils.displayGif(mContext, R.mipmap.gif1, ivAd);
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new PalFragment());
        mFragmentList.add(new QiuBaiImgFragment());
        mFragmentList.add(new GossipFragment());
        mFragmentList.add(new NewsTopFragment());
        mFragmentList.add(new GirlWelfareFragment());
        mFragmentList.add(new QiuBaiFragment());
        mPager.setAdapter(new Myadapter(getFragmentManager()));
    }

    private final int REQUEST_CODE = 106;
    private final int MANIFESTPERMISSIONCAMERA = 17;
    private int height;  // 滑动到什么地方完全变色
    private int ScrollUnm = 0;  //滑动的距离总和

    @Override
    public void initListener() {
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.main_green), getResources().getColor(R.color.colorOrange), getResources().getColor(R.color.mainColor));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        ToastUtils.showCenterToast("然而什么都没有发生，哈哈哈哈嗝```");
                    }
                }, 3000);

            }
        });
        upView.setOnItemClickListener((position, view) -> {
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            intent.putExtra(SplashActivity.WEATHER , true);
            startActivity(intent);
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                indicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                indicator.onPageScrollStateChanged(state);
            }
        });
        mPager.setCurrentItem(0);

        height = UiUtils.dp2px(mContext, 200 - 45);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                ScrollUnm = -verticalOffset; //滑动距离总合
                if (ScrollUnm <= 0) {  //在顶部时完全透明
                    llChange.setBackgroundColor(Color.argb((int) 0, 255, 41, 76));
                } else if (ScrollUnm > 0 && ScrollUnm <= height) {  //在滑动高度中时，设置透明度百分比（当前高度/总高度）
                    double d = (double) ScrollUnm / height;
                    double alpha = (d * 255);
                    llChange.setBackgroundColor(Color.argb((int) alpha, 255, 41, 76));
                } else { //滑出总高度 完全不透明
                    llChange.setBackgroundColor(Color.argb((int) 255, 255, 41, 76));
                }
                if (verticalOffset == 0) {
                    //展开
                    swipeRefresh.setEnabled(true);
//                    mImmersionBar.statusBarDarkFont(false,0.2f).init();
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
//                // 闭合
//                    mImmersionBar.statusBarDarkFont(true,0.2f).init();
                } else {
                    swipeRefresh.setEnabled(false);
                }
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
                start(TVShowsActivity.class);
            }
        });
    }


    class Myadapter extends FragmentPagerAdapter {

        public Myadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    private void setBannerOneDatas() {
//       Animation animation = AnimationUtils.loadAnimation(getActivity() , R.anim.banner_anim);
//       animation.setInterpolator(new OvershootInterpolator());
//        banner.setAnimation(animation);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(ResDatas.getBannerRes());
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                startActivity(new Intent(mContext, GirlWelfareActivity.class));
            }
        });
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
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
        LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(getActivity(), R.anim.menu_anim));
        menuRecyclerView.setLayoutAnimation(controller);
        menuRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 5));
        HomeMenuAdapter hap = new HomeMenuAdapter(R.layout.item_homemenu);
        menuRecyclerView.setAdapter(hap);
        hap.setOnItemClickListener((adapter, view, position) -> showVideoDialog());
        homeMenuBean b = new homeMenuBean();
        b.datas = new ArrayList<>();
        for (int i = 0; i < menu.size(); i++) {
            homeMenuBean.Data datas = new homeMenuBean.Data();
            datas.icon = ResDatas.icon_lol_imangs[i];
            datas.title = menu.get(i);
            b.datas.add(datas);
        }
        hap.setNewData(b.datas);

    }

    private void showVideoDialog() {
        String[] datas = {"抖音Style", "列表Style", "自动播放列表Style"};
        new SingleSelectDialog.Builder(mContext).setItems(datas, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    startActivity(new Intent(mContext, VideoPlayerDouActivity.class));
                } else if (i == 1) {
                    startActivity(new Intent(mContext, VideoPlayerListActivity.class));
                } else {
                    startActivity(new Intent(mContext, VideoPlayerListAutoActivity.class));
                }
            }
        }).show();
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
