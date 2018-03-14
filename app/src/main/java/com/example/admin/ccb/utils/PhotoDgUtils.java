package com.example.admin.ccb.utils;

import android.content.Context;

import java.util.List;

/**
 * @Author cuiChenBo
 * Created by zz on 2018/3/13 13:41.
 * 　　class explain:
 * 　　　　update:       upAuthor:      explain:
 */

public class PhotoDgUtils {
    public static void show(Context context, List<String> photoLists, int position){
        new PhotoShowDialog(context,photoLists,position).show();
    }
    public static void show(Context context,String photos){
        new PhotoShowDialog(context,photos).show();
    }
}
