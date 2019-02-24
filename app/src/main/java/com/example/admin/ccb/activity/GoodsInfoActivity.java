package com.example.admin.ccb.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alipay.sdk.app.PayTask;
import com.example.admin.ccb.R;
import com.example.admin.ccb.base.BaseActivity;
import com.example.admin.ccb.base.DefaultBaseAdapter;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.example.admin.ccb.utils.PayResult;
import com.example.admin.ccb.utils.PhotoDgUtils;
import com.example.admin.ccb.utils.ResCcb;
import com.example.admin.ccb.view.AllListView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.List;
import java.util.Map;

import www.ccb.com.common.utils.ToastUtils;

public class GoodsInfoActivity extends BaseActivity{

    @Override
    public int getContentViewResource() {
        return R.layout.activity_goods_info;
    }

    @Override
    protected void initView() {
        UpTitle("详情");
//        ViewPager viewPager = findViewById(R.id.cvp);
        MZBannerView banner = findViewById(R.id.banner);
        AllListView allListView = findViewById(R.id.alv);
//        viewPager.setPageMargin(3);
//        viewPager.setOffscreenPageLimit(3);
//        MyVpAdater adater = new MyVpAdater(mContext, ResCcb.getGoodsimages2());
//        viewPager.setAdapter(adater);
//        viewPager.setCurrentItem(1);
//        viewPager.setPageTransformer(false,new ScaleTransformer());
        // 设置数据
        banner.setPages(ResCcb.getGoodsimages2(), new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        allListView.setAdapter(new DefaultBaseAdapter<Integer>(this,ResCcb.getDetailsImages()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Vh vh = null;
                if (convertView == null){
                    vh = new Vh();
                    convertView = View.inflate(mContext,R.layout.item_details,null);
                    vh.imageView = convertView.findViewById(R.id.iv);
                    convertView.setTag(vh);
                }else{
                    vh = (Vh) convertView.getTag();
                }
                vh.imageView.setImageResource(dataList.get(position));
                return convertView;
            }
            class Vh{
                public ImageView imageView;
            }
        });
    }

    @Override
    protected void initData() {

    }

    public class BannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_details,null);
            mImageView = (ImageView) view.findViewById(R.id.iv);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
            GlideImageUtils.Display(context,data,mImageView);
        }
    }

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @Override
    protected void initList() {
        findViewById(R.id.tvInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GoodsInfoActivity.this,GoodsInfoActivity.class));
            }
        });

        findViewById(R.id.tvAli).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String orderInfo = "服务器返回的OrderInfo";   // 订单信息
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(GoodsInfoActivity.this);
                        Map<String, String> result = alipay.payV2(orderInfo,true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
        });
        findViewById(R.id.tvWx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String PARTNER_ID ="xbM5MBCVOj2sEAs8KrMfwla4djpcQKuvG9";
                final String APP_ID = "wxd930ea5d5a258f4f";
                final String orderInfo = "kkdkkdk6kd4da54fa5fd45a6sd";   // 订单信息
                IWXAPI wxapi = WXAPIFactory.createWXAPI(mContext,null);
                if (wxapi.isWXAppInstalled()){
                    PayReq req = new PayReq();
                    req.appId = APP_ID;
                    req.transaction = String.valueOf(System.currentTimeMillis());
                    req.partnerId = PARTNER_ID;
                    req.prepayId = orderInfo;
                    req.nonceStr = orderInfo;
                    req.timeStamp = "ccb";
                    req.packageValue = "com.example.admin.ccb";
                    req.sign = orderInfo;
                    req.signType = orderInfo;
                    wxapi.sendReq(req);
                }else {
                    ToastUtils.showToast(mContext,"亲，您还安装微信");
                }

            }
        });
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult result = new PayResult((Map<String, String>) msg.obj);
            ToastUtils.showToast(mContext, result.toString());
        }
    };


//    public class MyCardHandler implements CardHandler<String> {
//
//        @Override
//        public View onBind(final Context context, final String data, final int position, int mode) {
//            View view = View.inflate(context, R.layout.item_goodsbanner, null);
//            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
//            GlideImageUtils.Display(mContext,data,imageView);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   PhotoDgUtils.show(mContext,data);
//                }
//            });
//            return view;
//        }
//
//    }

//    public class MyVpAdater extends PagerAdapter {
//        private List<String> list;
//        private Context context;
//
//        public MyVpAdater(Context context, List<String> list) {
//            this.context = context;
//            this.list = list;
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, final int position) {
//            ImageView iv = new ImageView(context);
//            GlideImageUtils.Display(mContext,list.get(position),iv);
//            iv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    PhotoDgUtils.show(mContext,list.get(position));
//                }
//            });
//            container.addView(iv);
//            return iv;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//    }
//    public class ScaleTransformer implements ViewPager.PageTransformer {
//        private static final float MIN_SCALE = 0.80f;
//        private static final float MIN_ALPHA = 0.95f;
//
//        @Override
//        public void transformPage(View page, float position) {
//            if (position < -1 || position > 1) {
//                page.setAlpha(MIN_ALPHA);
//                page.setScaleX(MIN_SCALE);
//                page.setScaleY(MIN_SCALE);
//            } else if (position <= 1) { // [-1,1]
//                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
//                if (position < 0) {
//                    float scaleX = 1 + 0.2f * position;
//                    page.setScaleX(scaleX);
//                    page.setScaleY(scaleX);
//                } else {
//                    float scaleX = 1 - 0.2f * position;
//                    page.setScaleX(scaleX);
//                    page.setScaleY(scaleX);
//                }
//                page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
//            }
//        }
//    }
}
