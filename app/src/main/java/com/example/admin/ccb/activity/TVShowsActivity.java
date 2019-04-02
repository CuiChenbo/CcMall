package com.example.admin.ccb.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import com.example.admin.ccb.bean.DoubanBean;
import com.example.admin.ccb.fragment.DoubanInTheatersFragment;
import com.example.admin.ccb.fragment.DoubanTopFragment;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.example.admin.ccb.utils.ResCcb;
import com.example.admin.ccb.view.NotConflictViewPager;
import com.lzy.okgo.model.HttpParams;

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
import java.util.Random;

import www.ccb.com.common.base.BaseActivity;
import www.ccb.com.common.base.BaseFragment;
import www.ccb.com.common.utils.ToastUtils;
import www.ccb.com.common.utils.UrlFactory;
import www.ccb.com.common.widget.VpSwipeRefreshLayout;

public class TVShowsActivity extends BaseActivity {

    private BaseQuickAdapter<DoubanBean.SubjectsBean, BaseViewHolder> mAdapter;

    @Override
    public int getContentViewResource() {
        return R.layout.activity_tv;
    }

    private List<String> mDataList = Arrays.asList("豆瓣排行榜","正在热映");
    private MagicIndicator indicator;
    private NotConflictViewPager mPager;
    private AppBarLayout appBarLayout;
    private ImageView ivBack;
    private TextView tvName;
    private RecyclerView hRv;
    private SwipeRefreshLayout swipeRefresh;
    @Override
    protected void initView() {
        UpTitle("");
        appBarLayout = findViewById(R.id.appBarLayout);
        indicator = findViewById(R.id.Indicator);
        mPager = this.findViewById(R.id.vp);
        ivBack = findViewById(R.id.tvTitleBack);
        tvName = findViewById(R.id.tvTitleBar);
        hRv = findViewById(R.id.hRv);
        swipeRefresh = findViewById(R.id.swipe);

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

    private List<BaseFragment> mFragmentList;
    @Override
    protected void initData() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new DoubanTopFragment());
        mFragmentList.add(new DoubanInTheatersFragment());
        mPager.setAdapter(new Myadapter(getSupportFragmentManager()));

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        hRv.setLayoutManager(llm);
        mAdapter = new BaseQuickAdapter<DoubanBean.SubjectsBean, BaseViewHolder>(R.layout.item_tvplayer_horizontal) {
            @Override
            protected void convert(BaseViewHolder helper, DoubanBean.SubjectsBean item) {
                helper.setText(R.id.tv,item.getTitle());
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,BaseWebViewActivity.class);
                        intent.putExtra("url",item.getAlt());
                        startActivity(intent);
                    }
                });
                GlideImageUtils.display(mContext, item.getImages().getMedium(), (ImageView) helper.getView(R.id.iv));
            }
        };
        hRv.setAdapter(mAdapter);
        loadData();
    }

    private void loadData() {
        HttpParams params = new HttpParams();
        params.put("start",new Random().nextInt(200));
        params.put("count",16);
        okPostRequest("Top",UrlFactory.DoubanDataTopUrl,params,DoubanBean.class);
    }

    @Override
    protected void initList() {
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.main_green),getResources().getColor(R.color.colorOrange),getResources().getColor(R.color.mainColor));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
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

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    //展开
                    swipeRefresh.setEnabled(true);
                    tvName.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvName.setText("");
                    ivBack.setImageResource(R.mipmap.nav_back_white);
                    mImmersionBar.statusBarDarkFont(false,0.2f).init();
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
//                // 闭合
                    tvName.setText("CC影视");
                    tvName.setTextColor(getResources().getColor(R.color.emphasisTextview));
                    ivBack.setImageResource(R.mipmap.nav_back_black);
                    mImmersionBar.statusBarDarkFont(true,0.2f).init();
                } else {
                    swipeRefresh.setEnabled(false);
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


    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);
        if (!TextUtils.equals(whit,"Top"))return;
        try{
            DoubanBean datas = (DoubanBean) t;
            mAdapter.setNewData(datas.getSubjects());
        }catch (Exception e){
            e.fillInStackTrace();
        }
    }

    @Override
    protected void okResponseError(String whit, String body) {
        super.okResponseError(whit, body);
        ToastUtils.showCenterToast("哇偶，网炸了```");
    }

    @Override
    protected void okResponseFinish(String flag) {
        super.okResponseFinish(flag);
        swipeRefresh.setRefreshing(false);
    }
}
