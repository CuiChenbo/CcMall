package www.ccb.com.common.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 */
public final class AndroidUtils {
    /**
     * 用户图像存储目录
     */
    private static final String FACE_CACHE_DIR = "face";
    /**
     * http缓存目录
     */
    private static final String HTTP_CACHE_DIR = "http";
    /**
     * web缓存目录
     */
    private static final String WEB_CACHE_DIR = "web";

    /**
     * 创建（获取）应用的web缓存目录../cache/web
     *
     * @param context
     * @return
     */
    public static File getWebCacheDir(Context context) {
        File androidCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        }
        if (MEDIA_MOUNTED.equals(externalStorageState)) {
            androidCacheDir = context.getApplicationContext().getExternalCacheDir();
        }
        if (androidCacheDir == null) {
            androidCacheDir = context.getApplicationContext().getCacheDir();
        }

        File webDir = new File(androidCacheDir, WEB_CACHE_DIR);
        if (!webDir.exists()) {
            if (!webDir.mkdir()) {
                webDir = androidCacheDir;
            }
        }

        return webDir;
    }

    /**
     * 存放用户头像文件夹 ../face
     */
    public static File getFaceCacheDir(Context context) {
        File androidCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        }
        if (MEDIA_MOUNTED.equals(externalStorageState)) {
            androidCacheDir = context.getApplicationContext().getExternalCacheDir();
        }
        if (androidCacheDir == null) {
            androidCacheDir = context.getApplicationContext().getCacheDir();
        }

        File faceDir = new File(androidCacheDir, FACE_CACHE_DIR);
        if (!faceDir.exists()) {
            if (!faceDir.mkdir()) {
                faceDir = androidCacheDir;
            }
        }

        return faceDir;
    }

    public static File getHttpCacheDir(Context context) {
        File androidCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        }
        if (MEDIA_MOUNTED.equals(externalStorageState)) {
            androidCacheDir = context.getApplicationContext().getExternalCacheDir();
        }
        if (androidCacheDir == null) {
            androidCacheDir = context.getApplicationContext().getCacheDir();
        }

        File httpDir = new File(androidCacheDir, HTTP_CACHE_DIR);
        if (!httpDir.exists()) {
            if (!httpDir.mkdir()) {
                httpDir = androidCacheDir;
            }
        }

        return httpDir;
    }

    /**
     * 判断程序是否在前台运行
     *
     * @param context
     * @return true在前台，false在后台
     */
    public static boolean isAppForeground(Context context) {
        boolean isForeground = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null)
            return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                //前台程序
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String pkgName : processInfo.pkgList) {
                        if (pkgName.equals(context.getPackageName())) {
                            isForeground = true;
                        }
                    }
                }
            }
        } else {
            //@deprecated As of {@link android.os.Build.VERSION_CODES#LOLLIPOP}, 从Android5.0开始不能使用getRunningTask函数
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isForeground = true;
            }
        }

        return isForeground;
    }

    /**
     * 判断手机是否ROOT
     */
    public static boolean isRoot() {
        Process process = null;
        try {
            //   /system/xbin/which 或者  /system/bin/which
            process = Runtime.getRuntime().exec(new String[]{"which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }

    /**
     * 获取手机brand
     */
    public static String getBrand(){
        return Build.BRAND;
    }

    /**
     * 获取手机型号
     */
    public static String getModel(){
        return Build.MODEL;
    }
    /**
     * 获取手机系统版本号
     */
    public static String getRelease (){
        return Build.VERSION.SDK_INT + "";
    }

    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断是否有内存卡
     *
     * @return
     */
    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡的根目录
     *
     * @return
     */
    public static String getSDRoot() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static File getDownloadDir() {
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!downloadDir.exists()) {
            downloadDir.mkdir();
        }
        return downloadDir;
    }

    public static String getDeviceId(Context context) {
        String imei = getPhoneIMEI(context);
        if (!TextUtils.isEmpty(imei))
            return imei;
        return getAndroidID(context);
    }

    /**
     * 获取设备的ANDROID_ID
     *
     * @param context
     * @return
     */
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取设备号
     *
     * @param context
     * @return
     */
    public static String getPhoneIMEI(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取ApiKey
     *
     * @param context
     * @param metaKey
     * @return
     */
    public static String getMetaValue(Context context, String metaKey) {
        String apiKey = "NameNotFoundException";
        if (context == null || metaKey == null) {
            return apiKey;
        }
        try {
            Bundle metaData = null;
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {
            // ignore
        }

        return apiKey;
    }

    public static boolean isFlyme() {
        String str = Build.BRAND;
        return str != null && str.toLowerCase(Locale.ENGLISH).contains("meizu");
    }

    public static boolean isKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isLOLLIPOP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference    an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *                     string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @TargetApi(19)
    public static String getImageAbsolutePath(Context context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }


    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
