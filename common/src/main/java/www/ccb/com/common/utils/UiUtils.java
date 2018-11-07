package www.ccb.com.common.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import www.ccb.com.common.BaseApplication;

public class UiUtils {

    /**
     * 复制内容
     * @param mContext
     * @param copyContent
     */
    public static void copyContent(Context mContext,String copyContent){
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里
        cm.setText(copyContent);
        ToastUtils.showToast(mContext, "已复制");
    }

    /**
     * 获得复制内容
     * @param mContext
     * @return
     */
    public static String getCopyContent(Context mContext){
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        // 获得复制内容
        String copyContent = cm.getText().toString();
        return copyContent;
    }

    public static int getScreenWidth(Context context) {
//		if (screenWidth != 0) {
//			return screenWidth;
//		}
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;//得到屏幕的宽度(像素)
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;//得到屏幕的高度(像素)
    }


    /**
     * dip转换px
     */
    public static int dp2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */

    public static int px2dp(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * dip转换px
     */
    public static int dp2px(Context content,int dip) {
        final float scale = content.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */

    public static int px2dp(Context content,int px) {
        final float scale = content.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


    /**
     * 根据资源id  获取 字符串数组
     *
     * @param id
     * @return
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * 获取Resources 对象
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 文件下载路径
     */
    public static String getDownloadPath() {
        return Environment.getExternalStorageDirectory().getPath()+"/download";
    }

    /**
     * 上下文对象
     *
     * @return
     */
    public static Context getContext() {
        return BaseApplication.get();
    }

    /**
     * 根据布局id  返回view 对象
     *
     * @param id
     * @return
     */
    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    /**
     * 根据 资源id 返回 Drawable 对象
     *
     * @param id
     * @return
     */
    public static Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    /**
     * id --->  px
     *
     * @param id
     * @return
     */
    public static int getDimens(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    /**
     * 通过id 获取字符串
     *
     * @param id
     * @return
     */
    public static String getString(int id) {
        return getResources().getString(id);
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
    /**
     * 返回当前程序版本名
     */
    public static int getAppVersionCode(Context context) {
        int VersionCode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            VersionCode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return VersionCode;
    }


    /**
     * 防止连续点击跳转两个页面 ,第一次点击返回true/
     * i 传点击间隔
     */
    private static long lastClickTime;

    public static boolean isFastDoubleClick(int i) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < i) {
            return false;
        }
        lastClickTime = time;
        return true;
    }

    public static Bitmap getBitmapFromUrl(String url, double width, double height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
//        Bitmap bitmap = BitmapFactory.decodeFile(url);


        BitmapFactory.Options
                opts = new BitmapFactory.Options();

        opts.inSampleSize= 4;

        Bitmap bitmap = BitmapFactory.decodeFile(url, opts);

        // 防止OOM发生
        options.inJustDecodeBounds = false;
        int mWidth = bitmap.getWidth();
        int mHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = 1;
        float scaleHeight = 1;
        //        try {
        //            ExifInterface exif = new ExifInterface(url);
        //            String model = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
        // 按照固定宽高进行缩放
        // 这里希望知道照片是横屏拍摄还是竖屏拍摄
        // 因为两种方式宽高不同，缩放效果就会不同
        // 这里用了比较笨的方式
        if (mWidth <= mHeight) {
            scaleWidth = (float) (width / mWidth);
            scaleHeight = (float) (height / mHeight);
        } else {
            scaleWidth = (float) (height / mWidth);
            scaleHeight = (float) (width / mHeight);
        }
        //        matrix.postRotate(90); /* 翻转90度 */
        // 按照固定大小对图片进行缩放
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight, matrix, true);
        // 用完了记得回收
        bitmap.recycle();
        return newBitmap;
    }

}
