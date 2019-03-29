package com.example.admin.ccb.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.admin.ccb.R;
import com.example.admin.ccb.fragment.DoubanInTheatersFragment;
import com.example.admin.ccb.fragment.DoubanTopFragment;
import com.example.admin.ccb.fragment.ShopFragment1;
import com.example.admin.ccb.fragment.ShopFragment2;
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

import www.ccb.com.common.base.BaseActivity;
import www.ccb.com.common.base.BaseHeadLayoutFragment;
import www.ccb.com.common.widget.header_layout.HeaderViewPager;

public class SetActivity extends BaseActivity {

    @Override
    public int getContentViewResource() {
        return R.layout.activity_set;
    }

    private List<String> mDataList = Arrays.asList("豆瓣排行榜","正在热映");
    private HeaderViewPager headerLayout;
    private MagicIndicator indicator;
    private NotConflictViewPager mPager;
    @Override
    protected void initView() {
        UpTitle("豆瓣影视");
        headerLayout = findViewById(R.id.headerLayout);
        indicator = findViewById(R.id.Indicator);
        mPager = this.findViewById(R.id.vp);

        CommonNavigator commonNavigator = new CommonNavigator(this);
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

    private List<BaseHeadLayoutFragment> mFragmentList;
    @Override
    protected void initData() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new DoubanTopFragment());
        mFragmentList.add(new DoubanInTheatersFragment());
        mPager.setAdapter(new Myadapter(getSupportFragmentManager()));
    }

    @Override
    protected void initList() {
        headerLayout.setCurrentScrollableContainer(mFragmentList.get(0));
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                headerLayout.setCurrentScrollableContainer(mFragmentList.get(position));
            }
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
}
