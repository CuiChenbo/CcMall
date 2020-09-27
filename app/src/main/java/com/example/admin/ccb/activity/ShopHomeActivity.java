package com.example.admin.ccb.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.ccb.R;
import www.ccb.com.common.base.BaseActivity;
import com.example.admin.ccb.fragment.ShopFragment;
import com.example.admin.ccb.view.NotConflictViewPager;

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

public class ShopHomeActivity extends BaseActivity {

    private AppBarLayout appBarLayout;

    @Override
    public int getContentViewResource() {
        return R.layout.activity_shop_home;
    }

    private String titles[] = {"Grid","Linear"};
    private List<String> mDataList = Arrays.asList(titles);
    private List<Fragment> mFragmentList;

private MagicIndicator indicator;
private NotConflictViewPager mPager;
private ImageView ivBack,ivScan;
private TextView tvName;
    @Override
    protected void initView() {
        UpTitle("CCB家店铺");
        appBarLayout = findViewById(R.id.appBarLayout);
       indicator = findViewById(R.id.Indicator);
        mPager = this.findViewById(R.id.vp);
        ivBack = findViewById(R.id.tvTitleBack);
        ivScan = findViewById(R.id.ivScan);
        tvName = findViewById(R.id.tvTitleBar);

        CommonNavigator commonNavigator = new CommonNavigator(this);
//        commonNavigator.setSkimOver(true);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

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
        ViewPagerHelper.bind(indicator,mPager);
    }

    @Override
    protected void initData() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(ShopFragment.getInstance("grid"));
        mFragmentList.add(ShopFragment.getInstance("line"));
        mPager.setAdapter(new Myadapter(getSupportFragmentManager()));
    }

    @Override
    protected void initList() {
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
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    //展开
                    ivScan.setImageResource(R.mipmap.home_scan);
                    tvName.setTextColor(getResources().getColor(R.color.colorWhite));
                    ivBack.setImageResource(R.mipmap.nav_back_white);
                    mImmersionBar.statusBarDarkFont(false,0.2f).init();
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
//                // 闭合
                    ivScan.setImageResource(R.mipmap.home_scan_black);
                    tvName.setTextColor(getResources().getColor(R.color.emphasisTextview));
                    ivBack.setImageResource(R.mipmap.nav_back_black);
                    mImmersionBar.statusBarDarkFont(true,0.2f).init();
                } else {
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    class Myadapter extends FragmentPagerAdapter{

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
}
