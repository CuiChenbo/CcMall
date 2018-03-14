package com.example.admin.ccb;

import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * @Author cuiChenBo
 * Created by zz on 2018/3/6 11:57.
 * 　　class explain:
 * 　　　　update:       upAuthor:      explain:
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ZXingLibrary.initDisplayOpinion(this);
        try {OkGo.getInstance().init(this);
        }catch (NoClassDefFoundError ncdfe){
        }
    }

    /**
     * 获取 上下文对象
     * @return
     */
    public static Context getInstance() {
        return instance;
    }
}
