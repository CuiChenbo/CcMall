package com.example.admin.ccb.activity;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.crazysunj.cardslideview.CardHandler;
import com.crazysunj.cardslideview.CardViewPager;
import com.example.admin.ccb.R;
import com.example.admin.ccb.utils.GlideImageUtils;
import com.example.admin.ccb.utils.ResCcb;

public class GoodsDatailsActivity extends BaseActivity {

    @Override
    public int getContentViewResource() {
        return R.layout.activity_goods_datails;
    }

    @Override
    protected void initView() {
        UpTitle("详情");
        CardViewPager viewPager = (CardViewPager) findViewById(R.id.cvp);
        viewPager.bind(getSupportFragmentManager(), new MyCardHandler(), ResCcb.getGoodsimages2());
        viewPager.setCardTransformer(180, 0.18f);
        viewPager.setCardPadding(60);
        viewPager.setCardMargin(20);
        viewPager.notifyUI(CardViewPager.MODE_CARD);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initList() {

    }

    public class MyCardHandler implements CardHandler<String> {

        @Override
        public View onBind(final Context context, final String data, final int position, int mode) {
            View view = View.inflate(context, R.layout.item_goodsbanner, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
            GlideImageUtils.Display(mContext,data,imageView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "data:" + data + "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }

    }
}
