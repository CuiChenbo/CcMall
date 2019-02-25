package com.example.admin.ccb;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.widget.ImageView;

import com.example.admin.ccb.utils.GlideImageUtils;
import com.lzy.ninegrid.NineGridView;
import com.lzy.okgo.OkGo;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import www.ccb.com.common.BaseApplication;

/**
 * @Author cuiChenBo
 * Created by zz on 2018/3/6 11:57.
 * 　　class explain:
 * 　　　　update:       upAuthor:      explain:
 */

public class MyApplication extends BaseApplication {
    private static MyApplication instance;
    private IWXAPI wxapi;
    public static final String APP_ID = "wxd930ea5d5a258f4f";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ZXingLibrary.initDisplayOpinion(this);
        wxapi = WXAPIFactory.createWXAPI(this, APP_ID, false);
        wxapi.registerApp(APP_ID);
        try {
            OkGo.getInstance().init(this);
        } catch (NoClassDefFoundError ncdfe) {
        }
        NineGridView.setImageLoader(new GlideImageLoader());

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /** Glide 加载 */
    private class GlideImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            GlideImageUtils.Display(context,url,imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

    /**
     * 获取 上下文对象
     *
     * @return
     */
    public static Context getInstance() {
        return instance;
    }
}
