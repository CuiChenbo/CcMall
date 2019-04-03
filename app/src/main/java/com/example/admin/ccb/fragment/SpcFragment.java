package com.example.admin.ccb.fragment;


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
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import com.example.admin.ccb.activity.GoodsInfoActivity;
import com.example.admin.ccb.activity.ShopHomeActivity;
import www.ccb.com.common.base.BaseFragment;
import com.example.admin.ccb.bean.ShopPingCartBean;
import com.example.admin.ccb.utils.ResCcb;

import java.util.ArrayList;
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
        for (int i = 0; i < ResCcb.getMenus().size(); i++) {   //添加店铺
            ShopPingCartBean.ShopBean shop = new ShopPingCartBean.ShopBean();
            shop.shopName = ResCcb.getMenus().get(i);
            int jj = random.nextInt(5)+1;
            shop.carList = new ArrayList<>();
            for (int j = 0; j < jj; j++) {  //添加商品
                ShopPingCartBean.ShopBean.CarListBean goods = new ShopPingCartBean.ShopBean.CarListBean();
                goods.title = ResCcb.getspcGoodsImages().get(random.nextInt(ResCcb.getspcGoodsImages().size()-1));
                goods.icon = ResCcb.getSpcImages().get(random.nextInt(ResCcb.getSpcImages().size()-1));
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
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = "";
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("请选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(0xFFffffff)//标题背景颜色 Night mode
                .setBgColor(0xFFffffff)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLinkage(true)//设置是否联动，默认true
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .build();

        pvOptions.setPicker(spcs.shopList);//添加数据源
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
