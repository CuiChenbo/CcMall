package com.example.admin.ccb.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import com.example.admin.ccb.activity.GoodsInfoActivity;
import com.example.admin.ccb.activity.ShopHomeActivity;
import www.ccb.com.common.base.BaseFragment;
import www.ccb.com.common.utils.UiUtils;

import com.example.admin.ccb.bean.ShopPingCartBean;
import com.example.admin.ccb.utils.ResDatas;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.ImagePreviewActivity;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Ccb simple {@link Fragment} subclass.
 */
public class SpcFragment extends BaseFragment {


    private SpcAdapter spcAp;
    private ShopPingCartBean spcs;
    private CheckBox cbAll;
    private TextView jiesuan;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spc, container, false);
    }
    private RecyclerView rvShop;
    @Override
    public void initView(View view) {
      rvShop = view.findViewById(R.id.rvShop);
      cbAll = view.findViewById(R.id.spc_cb_all);
        jiesuan = view.findViewById(R.id.jiesuan);
      rvShop.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL, false));
        spcAp = new SpcAdapter(R.layout.item_spc_shop);
      rvShop.setAdapter(spcAp);
        isTitleBar(true,view);
    }

    @Override
    public void loadData() {
        Random random = new Random();
        spcs = new ShopPingCartBean();
        spcs.shopList = new ArrayList<>();
        for (int i = 0; i < ResDatas.getMenus().size(); i++) {   //添加店铺
            ShopPingCartBean.ShopBean shop = new ShopPingCartBean.ShopBean();
            shop.shopName = ResDatas.getMenus().get(i);
            int jj = random.nextInt(5)+1;
            shop.carList = new ArrayList<>();
            for (int j = 0; j < jj; j++) {  //添加商品
                ShopPingCartBean.ShopBean.CarListBean goods = new ShopPingCartBean.ShopBean.CarListBean();
                goods.title = ResDatas.getspcGoodsImages().get(random.nextInt(ResDatas.getspcGoodsImages().size()-1));
                goods.icon = ResDatas.getSpcImages().get(random.nextInt(ResDatas.getSpcImages().size()-1));
                shop.carList.add(goods);
            }
            spcs.shopList.add(shop);
        }
        spcAp.setNewData(spcs.shopList);
    }

    @Override
    public void initListener() {
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean is = cbAll.isChecked();
                for (int i = 0; i < spcs.shopList.size(); i++) {
                    spcs.shopList.get(i).isSelectShop = is;
                    for (int j = 0; j < spcs.shopList.get(i).carList.size(); j++) {
                        spcs.shopList.get(i).carList.get(j).isSelectGoods = is;
                    }
                }
                spcAp.notifyDataSetChanged();
            }
        });
        jiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wheel();
            }
        });
    }


    private void wheel() {
        OptionsPickerView pvOptions = new  OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int index, int options2, int options3, View v) {
                ImageInfo aliPay = new ImageInfo();
                aliPay.setSrcUrl(R.mipmap.ali_pay);
                ImageInfo wePay = new ImageInfo();
                wePay.setSrcUrl(R.mipmap.wecart_pay);
                 List<ImageInfo> pays = Arrays.asList(aliPay , wePay);
                Intent intent = new Intent(getActivity(), ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) pays);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        })
                .setSubmitText("就它了")//确定按钮文字
                .setCancelText("算了吧")//取消按钮文字
                .setTitleText("你要结账???")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(getResources().getColor(R.color.color_212121))//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.wecart_color))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.color_87c489))//取消按钮文字颜色
                .setTitleBgColor(0xFFffffff)//标题背景颜色 Night mode
                .setBgColor(0xFFffffff)//滚轮背景颜色 Night mode
                .setContentTextSize(20)//滚轮文字大小
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .build();

        pvOptions.setPicker(Arrays.asList("支付宝" , "微信"));//添加数据源
        pvOptions.show();
    }


    /**
     * 适配器
     */
    public class SpcAdapter extends BaseQuickAdapter<ShopPingCartBean.ShopBean,BaseViewHolder> {
        public SpcAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(final BaseViewHolder helper, ShopPingCartBean.ShopBean item) {
            helper.setText(R.id.tvShop,item.shopName)
                    .setOnClickListener(R.id.tvShop, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mContext.startActivity(new Intent(mContext, ShopHomeActivity.class));
                        }
                    });
            final CheckBox cbShop = helper.getView(R.id.spc_cb_shops);
            cbShop.setChecked(item.isSelectShop);
            cbShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spcs.shopList.get(helper.getAdapterPosition()).isSelectShop = cbShop.isChecked();
                    for (int i = 0; i < spcs.shopList.get(helper.getAdapterPosition()).carList.size(); i++) {  //改变这个店铺中所有商品的数据
                        spcs.shopList.get(helper.getAdapterPosition()).carList.get(i).isSelectGoods = cbShop.isChecked();
                    }
                     //遍历有所的店铺集合，看是否全部选中，若选中则改变全选标记
                    List<Boolean> shoplist = new ArrayList<>();
                    for (int i = 0; i < spcs.shopList.size(); i++) {
                        shoplist.add(spcs.shopList.get(i).isSelectShop);
                    }
                    if (shoplist.contains(false)){
                        cbAll.setChecked(false);
                    }else{
                        cbAll.setChecked(true);
                    }
                    notifyDataSetChanged();
                }
            });
            RecyclerView rvGoods = helper.getView(R.id.rvGoods);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rvGoods.getLayoutParams();
            layoutParams.height = UiUtils.dp2px(100 * item.carList.size());
            rvGoods.setLayoutParams(layoutParams);

            rvGoods.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
            rvGoods.setHasFixedSize(true); //禁止recyclerview滑动，避免和ScrollView冲突；
            rvGoods.setNestedScrollingEnabled(false); //禁止recyclerview滑动，避免和ScrollView冲突；
            GoodsAdapter goodsAp = new GoodsAdapter(R.layout.item_spc_goods, helper.getAdapterPosition());
            rvGoods.setAdapter(goodsAp);
            goodsAp.setNewData(item.carList);

        }
    }

    class GoodsAdapter extends BaseQuickAdapter<ShopPingCartBean.ShopBean.CarListBean,BaseViewHolder>{
        private int positionShop;
        public GoodsAdapter(int layoutResId,int positionShop) {
            super(layoutResId);
            this.positionShop = positionShop;
        }

        @Override
        protected void convert(final BaseViewHolder helper, ShopPingCartBean.ShopBean.CarListBean item) {
            helper.setText(R.id.spc_tv_shop_name_msg,item.title)
                   .setOnClickListener(R.id.rl, new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           mContext.startActivity(new Intent(mContext, GoodsInfoActivity.class));
                       }
                   });
            helper.setImageResource(R.id.spc_iv_page,item.icon);
            final CheckBox cbGoods = helper.getView(R.id.spc_cb_goods);
            cbGoods.setChecked(item.isSelectGoods);
            cbGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spcs.shopList.get(positionShop).carList.get(helper.getAdapterPosition()).isSelectGoods = cbGoods.isChecked();
                    //改变这个商品的选中状态后，遍历看是否全部选中，若全部选中则改变店铺的选中状态
                    List<Boolean> goodslist = new ArrayList<>();
                    for (int i = 0; i < spcs.shopList.get(positionShop).carList.size(); i++) {
                        goodslist.add(spcs.shopList.get(positionShop).carList.get(i).isSelectGoods);
                    }
                    if (goodslist.contains(false)){
                        spcs.shopList.get(positionShop).isSelectShop = false;
                    }else{
                        spcs.shopList.get(positionShop).isSelectShop = true;
                    }
                    //改变这个店铺的选中状态后，遍历看是否全部选中，若全部选中则改变全选的选中状态
                    List<Boolean> shoplist = new ArrayList<>();
                    for (int i = 0; i < spcs.shopList.size(); i++) {
                        shoplist.add(spcs.shopList.get(i).isSelectShop);
                    }
                    if (shoplist.contains(false)){
                        cbAll.setChecked(false);
                    }else{
                        cbAll.setChecked(true);
                    }
                    spcAp.notifyDataSetChanged();
                    notifyDataSetChanged();
                }
            });

           Button btnJian = helper.getView(R.id.spc_btn_comm_count_jian);
           Button btnJia = helper.getView(R.id.spc_btn_comm_count_jia);
           TextView count = helper.getView(R.id.spc_et_comm_count);
           count.setText(item.count+"");
           btnJia.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   spcs.shopList.get(positionShop).carList.get(helper.getAdapterPosition()).count =  spcs.shopList.get(positionShop).carList.get(helper.getAdapterPosition()).count + 1;
                notifyDataSetChanged();
               }
           });
            btnJian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (spcs.shopList.get(positionShop).carList.get(helper.getAdapterPosition()).count > 1){
                    spcs.shopList.get(positionShop).carList.get(helper.getAdapterPosition()).count =  spcs.shopList.get(positionShop).carList.get(helper.getAdapterPosition()).count - 1;}
                    notifyDataSetChanged();
                }
            });

        }
    }
}
