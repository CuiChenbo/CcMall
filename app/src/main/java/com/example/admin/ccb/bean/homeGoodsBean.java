package com.example.admin.ccb.bean;

import java.util.List;

/**
 * @Author cuiChenBo
 * Created by zz on 2018/3/6 15:03.
 * 　　class explain:
 * 　　　　update:       upAuthor:      explain:
 */

public class homeGoodsBean {
    public List<Data> datas;
    public static class  Data{
        public int icon;
        public String title;
        public String content;
        public List<PicList> images;
        public static class  PicList{
            public String pic;
        }
    }
}
