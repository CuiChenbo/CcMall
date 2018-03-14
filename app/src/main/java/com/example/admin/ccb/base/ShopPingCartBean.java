package com.example.admin.ccb.base;

import java.util.List;


public class ShopPingCartBean {
        public List<ShopBean> shopList;

                public static class ShopBean {
                    public boolean isSelectShop;
                    public String shopName;
                    public List<CarListBean> carList;

                                public static class CarListBean {
                                    public String title;
                                    public Integer icon;
                                    public boolean isSelectGoods;
                                }

                }

}
