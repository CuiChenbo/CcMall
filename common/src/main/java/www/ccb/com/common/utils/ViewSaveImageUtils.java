package www.ccb.com.common.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.widget.ScrollView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * ChenboCui View转Bitmap 把图片保存到本地的工具类
 */
public class ViewSaveImageUtils {

    /**
     * 把一个View转为图片保存到本地
     * @param view
     * @param ener
     */
    public static void viewSaveToImage(View view, OnSaveListEner ener) {
        if (ener != null) ener.onStart();
        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);
        FileOutputStream fos;
        if (checkSDCardAvailable()) {
            try {
                // SD卡根目录
                File sdRoot = AndroidUtils.getDownloadDir();
                File file = new File(sdRoot, System.currentTimeMillis() + ".png");
                fos = new FileOutputStream(file);
                cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                if (ener != null) ener.onSucceed(file.getAbsolutePath());
            } catch (Exception e) {
                if (ener != null) ener.onFailure(e.getMessage());
                e.printStackTrace();
            }
        } else {
            if (ener != null) ener.onFailure("创建文件失败!");
        }
        view.destroyDrawingCache();
        if (ener != null) ener.onFinish();
    }

    /**
     * 把一个View转为图片保存到本地
     * @param view
     * @return
     */
    public static String viewSaveToImage(View view) {
        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);

        FileOutputStream fos;
        String imagePath = "";
        try {
            if (checkSDCardAvailable()) {
                // SD卡根目录
                File sdRoot = AndroidUtils.getDownloadDir();
                File file = new File(sdRoot, System.currentTimeMillis() + ".png");
                fos = new FileOutputStream(file);
                imagePath = file.getAbsolutePath();
            } else {
                throw new Exception("创建文件失败!");
            }
            cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        view.destroyDrawingCache();
        return imagePath;
    }

    /**
     * 把一个View转为Bitmap
     *
     * @param v
     * @return
     */
    public static Bitmap loadBitmapFromView(View v) {
        v.setDrawingCacheEnabled(true);
        v.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        v.setDrawingCacheBackgroundColor(Color.WHITE);

        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    /**
     * Bitmap保存为图片
     *
     * @param photoBitmap
     * @param path
     * @param photoName
     */
    public static void savePhotoToSDCard(Bitmap photoBitmap, String path, String photoName) {
        if (checkSDCardAvailable()) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File photoFile = new File(path, photoName + ".png");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
                    }
                } else {

                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * ScrollView保存为图片
     *
     * @param scrollView
     * @param path
     * @param photoName
     */
    public static void savePhotoToSDCard(final ScrollView scrollView, final String path, final String photoName, final OnSaveListEner ener) {
        //耗时操作，放线程处理
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ener != null) ener.onStart();
                if (checkSDCardAvailable()) {
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File photoFile = new File(path, photoName + ".png");
                    FileOutputStream fileOutputStream = null;
                    try {
                        Bitmap photoBitmap = getBitmapByView(scrollView);
                        fileOutputStream = new FileOutputStream(photoFile);
                        if (photoBitmap != null) {
                            if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                                fileOutputStream.flush();
                            }
                            if (ener != null) ener.onSucceed(photoFile.getAbsolutePath());
                        } else {
                            if (ener != null) ener.onFailure("Bitmap = NULL");
                        }
                    } catch (FileNotFoundException e) {
                        if (ener != null) ener.onFailure("FileNotFoundException");
                        photoFile.delete();
                        e.printStackTrace();
                    } catch (IOException e) {
                        if (ener != null) ener.onFailure("IOException");
                        photoFile.delete();
                        e.printStackTrace();
                    } finally {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (ener != null) ener.onFailure("文件储存异常");
                }
                if (ener != null) ener.onFinish();
            }
        }).start();
    }

    public static void savePhotoToSDCard(ScrollView scrollView, OnSaveListEner ener) {
        String path = AndroidUtils.getDownloadDir().getAbsolutePath();
        String photoname = "maxus" + System.currentTimeMillis();
        savePhotoToSDCard(scrollView, path, photoname, ener);
    }

    /**
     * 把一个View转为Bitmap
     *
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE); //白色背景
        scrollView.draw(canvas);
        return bitmap;
    }


    public static boolean checkSDCardAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }


    public interface OnSaveListEner {
        void onStart();

        void onSucceed(String filePath);

        void onFailure(String error);

        void onFinish();
    }

}
